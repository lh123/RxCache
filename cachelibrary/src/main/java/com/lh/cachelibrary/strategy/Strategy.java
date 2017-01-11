package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.cache.CacheMode;

import io.reactivex.Observable;

/**
 * Created by home on 2017/1/10.
 * 策略接口
 */

public interface Strategy {
    <T> Observable<T> execute(Observable<T> remote,Observable<T> cache);

    CacheMode getCacheMode();
}
