package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.CacheProxy;
import com.lh.cachelibrary.cache.CacheMode;

import java.lang.reflect.Type;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 优先Remote
 */

class PriorityRemoteStrategy extends BaseStrategy {

    @Override
    public <T> Observable<T> execute(final CacheProxy cacheProxy, Observable<T> origin, final String key, Type type) {
        Observable<T> remote = loadRemote(origin, cacheProxy, key, CacheMode.BOTH);
        Observable<T> cache = loadCache(cacheProxy, key, type);
        return Observable.concatArrayDelayError(remote, cache).firstElement().toObservable();
    }
}
