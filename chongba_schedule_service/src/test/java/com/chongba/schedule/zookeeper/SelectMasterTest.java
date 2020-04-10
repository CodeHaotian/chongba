package com.chongba.schedule.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Haotian
 * @Date: 2020/4/10 17:49
 * @Description: zookeeper 选主测试
 */
public class SelectMasterTest {
    private static final String SELECT_MASTER_ZOOKEEPER = "192.168.200.129:2181";
    //可以放很多节点
    Map<String, Boolean> masterMap = new HashMap<String, Boolean>();

    /**
     * 选主
     *
     * @param leaderPath zookeeper目录节点
     */
    public void selectMaster(String leaderPath) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString( SELECT_MASTER_ZOOKEEPER )
                .sessionTimeoutMs( 5000 )  //超时时间
                .retryPolicy( new ExponentialBackoffRetry( 1000, 3 ) ) //连接不上重试三次
                .build();
        client.start();

        //争抢注册节点
        @SuppressWarnings("resource")
        LeaderSelector selector = new LeaderSelector( client, leaderPath,
                new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework client) throws Exception {
                        //如果争抢到当前注册节点
                        masterMap.put( leaderPath, true );
                        while (true) {
                            //抢占当前节点
                            TimeUnit.SECONDS.sleep( 3 );
                        }
                    }
                } );
        masterMap.put( leaderPath, false );
        selector.autoRequeue();
        selector.start();
    }

    /**
     * 判断当前目录是否抢占到
     *
     * @param leaderPath zookeeper目录节点
     * @return true:主节点 false:从节点
     */
    public boolean checkMaster(String leaderPath) {
        Boolean isMaster = masterMap.get( leaderPath );
        return isMaster == null ? false : isMaster;
    }

    public static void main(String[] args) throws InterruptedException {
        String leaderPath = "/master";
        SelectMasterTest selectMaster = new SelectMasterTest();
        selectMaster.selectMaster( leaderPath );

        TimeUnit.SECONDS.sleep( 1 );

        while (true) {
            if (selectMaster.checkMaster( leaderPath )) {
                System.out.println( " 当前为 主节点 " );
            } else {
                System.out.println( " 当前为 从节点 " );
            }
            TimeUnit.SECONDS.sleep( 6 );
        }
    }
}