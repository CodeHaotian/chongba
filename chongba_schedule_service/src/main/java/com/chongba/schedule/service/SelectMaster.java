package com.chongba.schedule.service;

import com.chongba.schedule.config.SystemParams;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper 选主
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/10 17:49
 */
@Component
public class SelectMaster {
    private final SystemParams systemParams;
    /**
     * 可以放很多节点
     */
    Map<String, Boolean> masterMap = new HashMap<>();

    public SelectMaster(SystemParams systemParams) {
        this.systemParams = systemParams;
    }

    /**
     * 选主
     *
     * @param leaderPath zookeeper目录节点
     */
    public void selectMaster(String leaderPath) {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString( systemParams.getSelectMasterZookeeper() )
                //超时时间
                .sessionTimeoutMs( 5000 )
                //连接不上重试三次
                .retryPolicy( new ExponentialBackoffRetry( 1000, 3 ) )
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
}