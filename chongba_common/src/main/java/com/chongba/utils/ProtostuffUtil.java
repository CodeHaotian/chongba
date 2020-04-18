package com.chongba.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * protostuff 序列化
 *
 * @author Haotian
 * @version 1.0.0
 * @date 2020/4/18 21:45
 **/
public class ProtostuffUtil {

    /**
     * 序列化
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T t) {
        Schema schema = RuntimeSchema.getSchema( t.getClass() );
        return ProtostuffIOUtil.toByteArray( t, schema,
                LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE ) );

    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T deserialize(byte[] bytes, Class<T> c) {
        T t = null;
        try {
            t = c.newInstance();
            Schema schema = RuntimeSchema.getSchema( t.getClass() );
            ProtostuffIOUtil.mergeFrom( bytes, t, schema );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}