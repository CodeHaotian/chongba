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
     * zookeeper 数据恢复功能抢占目录
     */
    public static final String SCHEDULE_LEADER_PATH = "/chongba/schedule_master";

    /**
     * zookeeper job定制刷新服务抢占目录
     */
    public static final String JOB_LEADER_PATH = "/chongba/job_master";

    /**
     * 全局共享时间key，用于存放数据恢复未来五分钟时间节点
     */
    public static final String NEXT_SCHEDULE_TIME = "nextScheduleTime";

    public static final String jisuapi = "jisuapi";
    public static final String juheapi = "juheapi";

    public static final String jisu_order = "order_jisu";

    /**
     * 供应商排除key
     */
    public static final String exclude_supplier = "exclude_supplier";
    /**
     * 订单检查集合key
     */
    public static final String order_checked = "order_checked";
}