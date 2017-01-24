package com.lh.cachelibrary.cache;

/**
 * Created by home on 2017/1/10.
 * 缓存模式
 */

public enum CacheMode {
    NONE,
    DISK,
    MEMORY,
    BOTH;

    public boolean canCacheDisk() {
        return this == DISK || this == BOTH;
    }

    public boolean canCacheMemory() {
        return this == MEMORY || this == BOTH;
    }
}
