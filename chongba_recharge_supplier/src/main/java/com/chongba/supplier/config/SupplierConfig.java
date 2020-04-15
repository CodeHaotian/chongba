package com.chongba.supplier.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 读取配置文件
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/15 16:27
 */
@Data
@Component
@ConfigurationProperties(prefix = "supplier")
public class SupplierConfig {
    /**
     * 加载供应商api地址
     */
    private Map<String, String> apis;
}