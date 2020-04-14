package com.chongba.entity;

import com.chongba.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 接口返回对象实体
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/14 16:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3628649205978144511L;

    /**
     * 错误码
     */
    private Integer code = 500;

    /**
     * 错误信息
     */
    private String msg = null;

    /**
     * 返回结果实体
     */
    private T data = null;

    public static <T extends Serializable> Result<T> error(String msg) {
        return new Result<T>( StatusCode.ERROR, msg, null );
    }

    public static <T extends Serializable> Result<T> error(int code, String msg) {
        return new Result<T>( code, msg, null );
    }

    public static <T extends Serializable> Result<T> success(T data) {
        return new Result<T>( StatusCode.OK, "", data );
    }
}