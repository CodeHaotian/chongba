package com.chongba.schedule.cache;

import com.chongba.cache.CacheService;
import com.chongba.schedule.ScheduleApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}