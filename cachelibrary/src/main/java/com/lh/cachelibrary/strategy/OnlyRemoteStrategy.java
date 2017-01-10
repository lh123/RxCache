package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.CacheProxy;
import com.lh.cachelibrary.cache.CacheMode;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 只从Remote获取
 */

class OnlyRemoteStrategy extends BaseStrategy {
    @Override
    public <T> Observable<T> execute(CacheProxy cacheProxy, Observable<T> origin, String key, Type type) {
        return loadRemote(origin, cacheProxy, key, CacheMode.BOTH);
    }
}
