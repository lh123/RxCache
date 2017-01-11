package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.cache.CacheMode;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 只从Remote获取
 */

class OnlyRemoteStrategy implements Strategy {

    @Override
    public <T> Observable<T> execute(Observable<T> remote, Observable<T> cache) {
        return remote;
    }

    @Override
    public CacheMode getCacheMode() {
        return CacheMode.BOTH;
    }
}
