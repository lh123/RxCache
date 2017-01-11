package com.lh.cachelibrary.cache;

import com.lh.cachelibrary.convert.DiskConverter;
import com.lh.cachelibrary.utils.LogUtils;

import java.io.File;
import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 缓存的代理类
 */

public class CacheProxy implements ICache {

    private DiskCache mDiskCache;
    private MemoryCache mMemoryCache;

    public CacheProxy(DiskConverter diskConverter, File directory, int appVersion, int valueCount, int memorySize, long diskSize) {
        mDiskCache = new DiskCache(diskConverter, directory, appVersion, valueCount, diskSize);
        mMemoryCache = new MemoryCache(memorySize);
    }

    @Override
    public Object load(String key, Type type) {
        Object result = null;
        if (mMemoryCache != null) {
            result = mMemoryCache.load(key, type);
            if (result != null) {
                LogUtils.getInstance().i("load from memory");
                return result;
            }
        }
        if (mDiskCache != null) {
            LogUtils.getInstance().i("load from disk");
            result = mDiskCache.load(key, type);
        }
        return result;
    }

    @Override
    public boolean save(String key, Object data) {
        return save(key, data, CacheMode.BOTH);
    }

    public boolean save(String key, Object data, CacheMode mode) {
        boolean haveCached = false;
        if (mode.canCacheDisk() && mDiskCache != null) {
            mDiskCache.save(key, data);
            haveCached = true;
        }
        if (mode.canCacheMemory() && mMemoryCache != null) {
            mMemoryCache.save(key, data);
            haveCached = true;
        }
        return haveCached;
    }

    @Override
    public boolean containKey(String key) {
        if (mMemoryCache != null) {
            if (mMemoryCache.containKey(key)) {
                return true;
            }
        }
        return mDiskCache != null && mDiskCache.containKey(key);
    }

    @Override
    public boolean remove(String key) {
        boolean haveRemove = false;
        if (mMemoryCache != null) {
            haveRemove = mMemoryCache.remove(key);
        }
        if (mDiskCache != null) {
            haveRemove = mDiskCache.remove(key);
        }
        return haveRemove;
    }

    @Override
    public void clear() {
        if (mMemoryCache != null) {
            mMemoryCache.clear();
        }
        if (mDiskCache != null) {
            mDiskCache.clear();
        }
    }
}
