package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.cache.CacheMode;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/24.
 * 只从网络获取(不缓存)
 */

public class NoneCacheStrategy implements Strategy {

    @Override
    public <T> Observable<T> execute(Observable<T> remote, Observable<T> cache) {
        return remote;
    }

    @Override
    public CacheMode getCacheMode() {
        return CacheMode.NONE;
    }
}
