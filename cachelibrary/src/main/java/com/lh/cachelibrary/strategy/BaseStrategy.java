package com.lh.cachelibrary.strategy;

import com.lh.cachelibrary.CacheProxy;
import com.lh.cachelibrary.cache.CacheMode;
import com.lh.cachelibrary.utils.LogUtils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by home on 2017/1/10.
 * BaseStrategy
 */

abstract class BaseStrategy implements Strategy {
    <T> Observable<T> loadCache(final CacheProxy cacheProxy, final String key, final Type type) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                T result;
                try {
                    LogUtils.getInstance().i("load cache(key:" + key + ")");
                    result = (T) cacheProxy.load(key,type);
                } catch (ClassCastException e) {
                    emitter.onError(e);
                    return;
                }
                if (result != null) {
                    emitter.onNext(result);
                }
                emitter.onComplete();
            }
        });
    }

    <T> Observable<T> loadRemote(Observable<T> remote, final CacheProxy cacheProxy, final String key, final CacheMode mode) {
        return remote.map(new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {
                LogUtils.getInstance().i("load remote\nsave cache(key:" + key + " mode:" + mode + ")");
                cacheProxy.save(key, t, mode);
                return t;
            }
        });
    }
}
