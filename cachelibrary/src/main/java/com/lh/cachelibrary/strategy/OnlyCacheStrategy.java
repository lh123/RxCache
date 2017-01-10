package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.CacheProxy;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 只从Cache获取
 */

public class OnlyCacheStrategy extends BaseStrategy {
    @Override
    public <T> Observable<T> execute(CacheProxy cacheProxy, Observable<T> origin, String key, Type type) {
        return loadCache(cacheProxy, key, type);
    }
}
