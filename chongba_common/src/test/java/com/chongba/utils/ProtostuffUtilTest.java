package com.chongba.utils;

import com.chongba.recharge.RechargeRequest;

/**
 * 序列化时间测试
 *
 * @author Haotian
 * @date 2020/4/18 21:55
 */
public class ProtostuffUtilTest {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            RechargeRequest rechargeRequest = new RechargeRequest();
            JdkSerializeUtil.serialize( rechargeRequest );
        }
        System.out.println( " jdk 花费 " + (System.currentTimeMillis() - start) );

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            RechargeRequest rechargeRequest = new RechargeRequest();
            ProtostuffUtil.serialize( rechargeRequest );
        }
        System.out.println( " protostuff 花费 " + (System.currentTimeMillis() - start) );
    }
}