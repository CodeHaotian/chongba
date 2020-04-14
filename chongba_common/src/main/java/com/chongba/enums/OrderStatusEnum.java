package com.chongba.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 订单状态枚举
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 15:34
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OrderStatusEnum {

    CREATE( 0, "创建" ), WARTING( 1, "处理中" ), SUCCESS( 2, "成功" ), FAIL( 3, "失败" ), UNAFFIRM( 9, "未确认" );

    private Integer code;

    private String desc;
}