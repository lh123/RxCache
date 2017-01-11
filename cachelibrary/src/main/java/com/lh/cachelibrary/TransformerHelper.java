package com.lh.cachelibrary;

import com.lh.cachelibrary.cache.CacheMode;
import com.lh.cachelibrary.cache.CacheProxy;
import com.lh.cachelibrary.strategy.Strategy;
import com.lh.cachelibrary.utils.LogUtils;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by home on 2017/1/10.
 * TransformerHelper
 */

class TransformerHelper {

    private CacheProxy mCache;

    TransformerHelper(CacheProxy cache) {
        this.mCache = cache;
    }

    private <T> Observable<T> loadCache(final String key, final Type type) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                T result;
                try {
                    LogUtils.getInstance().i("loAad cache(key:" + key + ")");
                    result = (T) mCache.load(key,type);
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

    private <T> Observable<T> loadRemote(Observable<T> remote, final String key, final CacheMode mode) {
        return remote.map(new Function<T, T>() {
            @Override
            public T apply(T t) throws Exception {
                LogUtils.getInstance().i("load remote\nsave cache(key:" + key + " mode:" + mode + ")");
                mCache.save(key, t, mode);
                return t;
            }
        });
    }

    <T> ObservableTransformer<T,T> handlerStrategy(final String key, final Strategy strategy, Type type){
        final Observable<T> cache = loadCache(key,type);
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return strategy.execute(loadRemote(upstream,key,strategy.getCacheMode()),cache);
            }
        };
    }
}
