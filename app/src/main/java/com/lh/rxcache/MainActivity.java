package com.lh.rxcache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.lh.cachelibrary.RxCache;
import com.lh.cachelibrary.convert.GsonDiskConverter;
import com.lh.cachelibrary.strategy.CacheStrategy;
import com.lh.cachelibrary.utils.TypeBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<CacheBean> cacheBeens = new ArrayList<>();
        cacheBeens.add(new CacheBean("hello cache!"));
        RxCache rxCache = new RxCache.Builder()
                .setAppVersion(1)
                .setValueCount(1)
                .setDiskCachePath(getExternalCacheDir().getAbsolutePath())
                .setDiskConverter(new GsonDiskConverter())
                .build();
        Type type = TypeBuilder.newBuilder(List.class)
                .addParamType(CacheBean.class)
                .build();
        Observable
                .create(new ObservableOnSubscribe<List<CacheBean>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<CacheBean>> e) throws Exception {
                        e.onNext(cacheBeens);
                        e.onComplete();
                    }
                })
                .compose(rxCache.<List<CacheBean>>transformer("cache", CacheStrategy.priorityRemote(),type ))
                .subscribe(new Consumer<List<CacheBean>>() {
                    @Override
                    public void accept(List<CacheBean> cacheBean) throws Exception {
                        Toast.makeText(getApplicationContext(),cacheBean.get(0).getData(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
