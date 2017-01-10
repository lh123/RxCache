package com.lh.cachelibrary;

import android.text.TextUtils;

import com.lh.cachelibrary.convert.DiskConverter;
import com.lh.cachelibrary.strategy.Strategy;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Created by home on 2017/1/10.
 * RxCache主类
 */

public class RxCache {
    private CacheProxy<Serializable> mCache;

    private RxCache(DiskConverter diskConverter, File directory, int appVersion, int valueCount, int memorySize, long diskSize) {
        mCache = new CacheProxy<>(diskConverter, directory, appVersion, valueCount, memorySize, diskSize);
    }

    public <T> ObservableTransformer<T, T> transformer(String key, Strategy strategy) {
        return transformer(key, strategy, null);
    }

    public <T> ObservableTransformer<T, T> transformer(final String key, final Strategy strategy, final Type type) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return strategy.execute(mCache, upstream, key, type);
            }
        };
    }

    public static class Builder {

        private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
        private static final int MAX_MEMORY_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory());
        private static final int DEFAULT_MEMORY_CACHE_SIZE = MAX_MEMORY_CACHE_SIZE / 8;//运行内存的8分之1

        private int appVersion;
        private int valueCount;
        private long diskSize;
        private int memorySize;
        private String diskCachePath;
        private DiskConverter diskConverter;

        public Builder() {
            appVersion = 1;
            valueCount = 1;
            diskSize = MAX_DISK_CACHE_SIZE;
            memorySize = DEFAULT_MEMORY_CACHE_SIZE;
        }

        public Builder setAppVersion(int appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        public Builder setValueCount(int valueCount) {
            this.valueCount = valueCount;
            return this;
        }

        public Builder setDiskSize(long diskSize) {
            this.diskSize = diskSize;
            return this;
        }

        public Builder setMemorySize(int memorySize) {
            this.memorySize = memorySize;
            return this;
        }

        public Builder setDiskCachePath(String diskCachePath) {
            this.diskCachePath = diskCachePath;
            return this;
        }

        public Builder setDiskConverter(DiskConverter diskConverter) {
            this.diskConverter = diskConverter;
            return this;
        }

        public RxCache build() {
            if (TextUtils.isEmpty(diskCachePath)) {
                throw new IllegalArgumentException("diskCachePath can not be null");
            }
            if (diskConverter == null) {
                throw new IllegalArgumentException("diskConverter can not be null");
            }
            File file = new File(diskCachePath);
            if (!file.exists()) {
                file.mkdirs();
            }

            if (appVersion <= 0) {
                appVersion = 1;
            }

            if (valueCount <= 0) {
                valueCount = 1;
            }

            if (diskSize > MAX_DISK_CACHE_SIZE) {
                diskSize = MAX_DISK_CACHE_SIZE;
            } else if (diskSize <= 0) {
                diskSize = MAX_DISK_CACHE_SIZE / 10;
            }

            if (memorySize > MAX_MEMORY_CACHE_SIZE) {
                memorySize = MAX_MEMORY_CACHE_SIZE;
            } else if (memorySize <= 0) {
                memorySize = DEFAULT_MEMORY_CACHE_SIZE;
            }
            return new RxCache(diskConverter, file, appVersion, valueCount, memorySize, diskSize);
        }
    }
}
