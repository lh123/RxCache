package com.lh.cachelibrary.cache;

import android.util.LruCache;

import com.lh.cachelibrary.utils.Utils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashSet;

/**
 * Created by home on 2017/1/10.
 * 内存缓存
 */

public class MemoryCache<T> implements ICache<T> {

    private LruCache<String, T> mCache;
    private HashSet<String> mKeySet;

    public MemoryCache(int maxSize) {
        mCache = new LruCache<String, T>(maxSize) {
            @Override
            protected int sizeOf(String key, T value) {
                if (value instanceof Serializable) {
                    SizeCounter sizeCounter = new SizeCounter();
                    ObjectOutputStream os = null;
                    try {
                        os = new ObjectOutputStream(sizeCounter);
                        os.writeObject(value);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        Utils.closeStream(os, sizeCounter);
                    }
                    return sizeCounter.count;
                }
                return super.sizeOf(key, value);
            }
        };
        mKeySet = new HashSet<>();
    }

    @Override
    public T load(String key, Type type) {
        return mCache.get(key);
    }

    @Override
    public boolean save(String key, T data) {
        mCache.put(key, data);
        mKeySet.add(key);
        return true;
    }

    @Override
    public boolean containKey(String key) {
        return mKeySet.contains(key);
    }

    @Override
    public boolean remove(String key) {
        mKeySet.remove(key);
        mCache.remove(key);
        return true;
    }

    @Override
    public void clear() {
        mCache.evictAll();
        mKeySet.clear();
    }

    private static class SizeCounter extends OutputStream {

        private int count;

        @Override
        public void write(int b) throws IOException {
            count++;
        }
    }
}
