package com.lh.cachelibrary.cache;

/**
 * Created by home on 2017/1/10.
 */

public enum CacheMode {
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
