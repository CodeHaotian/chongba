package com.chongba.schedule.config;

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
     * 数据预加载时间/单位分钟
     */
    private int preLoad;
}