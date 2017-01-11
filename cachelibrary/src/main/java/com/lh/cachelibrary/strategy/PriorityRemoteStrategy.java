package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.cache.CacheMode;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 优先Remote
 */

class PriorityRemoteStrategy implements Strategy {

    @Override
    public <T> Observable<T> execute(Observable<T> remote, Observable<T> cache) {
        return Observable.concatArrayDelayError(remote,cache).firstElement().toObservable();
    }

    @Override
    public CacheMode getCacheMode() {
        return CacheMode.BOTH;
    }
}
