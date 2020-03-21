package com.chongba.schedule.cache;

import com.alibaba.fastjson.JSON;
import com.chongba.cache.CacheService;
import com.chongba.entity.Task;
import com.chongba.schedule.ScheduleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
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
}