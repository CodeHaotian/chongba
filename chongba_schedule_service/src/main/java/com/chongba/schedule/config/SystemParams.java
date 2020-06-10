package com.chongba.schedule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/6/10 15:53
 **/
@Data
@ConfigurationProperties(prefix = "chongba")
@Component
public class SystemParams {
    /**
     * 数据预加载时间/单位分钟
     */
    private int preLoad;

    /**
     * zookeeper 地址
     */
    private String selectMasterZookeeper;
}