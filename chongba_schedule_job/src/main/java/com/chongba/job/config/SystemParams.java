package com.chongba.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Haotian
 * @Date: 2020/4/7 10:13
 * @Description: 系统配置类
 */
@Data
@ConfigurationProperties(prefix = "chongba")
@Component
public class SystemParams {
    /**
     * zookeeper 地址
     */
    private String selectMasterZookeeper;
}