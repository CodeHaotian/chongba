package com.chongba.entity;

/**
 * @Author: Haotian
 * @Date: 2020/3/19 20:50
 * @Description: 任务常量类
 **/
public class Constants {
    /**
     * 初始化状态
     */
    public static final int SCHEDULED = 0;

    /**
     * 已执行状态
     */
    public static final int EXECUTED = 1;

    /**
     * 已取消状态
     */
    public static final int CANCELLED = 2;

    /**
     * 任务缓存key
     */
    public static final String DB_CACHE = "db_cache";

    /**
     * 消费队列key前缀
     */
    public static String TOPIC = "topic_";

    /**
     * 待消费队列key前缀
     */
    public static String FUTURE = "future_";

    /**
     * zookeeper 抢占目录
     */
    public static final String SCHEDULE_LEADER_PATH = "/chongba/schedule_master";
}