package com.lh.cachelibrary.cache;

import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 抽象缓存接口
 */

@SuppressWarnings("WeakerAccess")
public interface ICache {
    Object load(String key, Type type);

    boolean save(String key, Object data);

    boolean containKey(String key);

    boolean remove(String key);

    void clear();
}
