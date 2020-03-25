package com.chongba.schedule.cache;

import com.alibaba.fastjson.JSON;
import com.chongba.cache.CacheService;
import com.chongba.entity.Constants;
import com.chongba.entity.Task;
import com.chongba.schedule.ScheduleApplication;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/3/20 20:54
 * @Description: redis 工具类测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ScheduleApplication.class)
public class CacheServiceTest {
    @Autowired
    private CacheService cacheService;

    @Test
    public void test01() {
        cacheService.set( "name", "艾莉丝" );
        String name = cacheService.get( "name" );
        log.info( "name={}", name );
    }

    @Test
    public void test02() {
        cacheService.delete( "name" );
    }

    @Test
    public void test03() {
        cacheService.expire( "name", 5, TimeUnit.SECONDS );
    }

    @Test
    public void test04() {
        cacheService.setEx( "age", "20", 20, TimeUnit.SECONDS );
    }

    @Test
    public void test05() {
        //储存list类型数据
        cacheService.lLeftPush( "list", "1" );
        cacheService.lLeftPush( "list", "2" );
        cacheService.lLeftPush( "list", "3" );
        cacheService.lRightPush( "list", "1" );
        cacheService.lRightPush( "list", "2" );
        cacheService.lRightPush( "list", "3" );
        //弹出左边数据
        String list = cacheService.lLeftPop( "list" );
        log.info( "list={}", list );
    }

    @Test
    public void test06() {
        //存储hash类型
        cacheService.hPut( "myHash", "name", "艾莉丝" );
        cacheService.hPut( "myHash", "age", "18" );
        cacheService.hPut( "myHash", "cup", "D" );
        Map<String, String> map = new HashMap<>();
        map.put( "name", "椎名真白" );
        map.put( "age", "18" );
        map.put( "cup", "C" );
        cacheService.hPutAll( "myHash", map );
    }

    @Test
    public void test07() {
        //保存任务
        for (int i = 1; i <= 20; i++) {
            Task task = Task.builder()
                    .taskType( i )
                    .executeTime( System.currentTimeMillis() )
                    .priority( i )
                    .parameters( "cacheService".getBytes() ).build();
            cacheService.zAdd( "task", JSON.toJSONString( task ), task.getExecuteTime() );
        }
        //设置任务过期时间
        cacheService.expire( "task", 10, TimeUnit.SECONDS );
        //取出10个任务打印
        cacheService.zRange( "task", 0, 10 ).forEach( System.out::println );
        System.out.println( "------------孤独的分割线-----------------" );
        //取出全部数据
        //cacheService.zRangeAll( "task" ).forEach( System.out::println );
        //倒序排列
        cacheService.zReverseRangeByScore( "task", 0, System.currentTimeMillis() ).forEach( System.out::println );
    }

    @Test
    public void testPiple() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            // stringRedisTemplate.opsForValue().increment(key, increment);
            cacheService.incrBy( "pipelined", 1 );
        }
        log.info( "执行10000次自增操作共耗时:{}毫秒", (System.currentTimeMillis() - start) );
        start = System.currentTimeMillis();
        //使用管道技术
        List<Object> objectList = cacheService.getStringRedisTemplate().executePipelined( new RedisCallback<Object>() {
            @Nullable
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    redisConnection.incrBy( "pipelined".getBytes(), 1 );
                }
                return null;
            }
        } );
        log.info( "使用管道技术执行10000次自增操作共耗时:{}毫秒", (System.currentTimeMillis() - start) );
    }

    @Test
    public void refreshPiplineTest() {
        List<String> list = new ArrayList<String>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            Task task = Task.builder()
                    .taskType( i )
                    .executeTime( System.currentTimeMillis() )
                    .priority( i )
                    .parameters( "refreshPiplineTest".getBytes() ).build();
            list.add( JSON.toJSONString( task ) );
            cacheService.lLeftPush( "demo", JSON.toJSONString( task ) );
        }
        log.info( "未使用管道耗时{}毫秒", (System.currentTimeMillis() - start) );
        start = System.currentTimeMillis();
        String key = "demo";
        cacheService.refreshWithPipeline( Constants.FUTURE + key, Constants.TOPIC + key, list );
        log.info( "使用管道耗时{}毫秒", (System.currentTimeMillis() - start) );
    }
}