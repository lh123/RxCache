package com.lh.cachelibrary.cache;

import com.jakewharton.disklrucache.DiskLruCache;
import com.lh.cachelibrary.convert.DiskConverter;
import com.lh.cachelibrary.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 磁盘缓存类
 */

public class DiskCache<T> implements ICache<T> {

    private DiskLruCache mCache;
    private DiskConverter mConvert;

    public DiskCache(DiskConverter diskConverter, File directory, int appVersion, int valueCount, long maxSize) {
        mConvert = diskConverter;
        try {
            mCache = DiskLruCache.open(directory, appVersion, valueCount, maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T load(String key, Type type) {
        if (mCache == null) {
            return null;
        }
        T result = null;
        InputStream inputStream = null;
        try {
            DiskLruCache.Snapshot snapshot = mCache.get(key);
            if (snapshot == null) {
                return null;
            }
            inputStream = snapshot.getInputStream(0);
            result = mConvert.load(inputStream, type);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.closeStream(inputStream);
        }
        return result;
    }

    @Override
    public boolean save(String key, T data) {
        if (mCache == null) {
            return false;
        }
        boolean success = false;
        OutputStream outputStream = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = mCache.edit(key);
            if (editor != null) {
                outputStream = editor.newOutputStream(0);
                success = mConvert.save(outputStream, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (editor != null) {
                try {
                    editor.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Utils.closeStream(outputStream);
        }
        return success;
    }

    @Override
    public boolean containKey(String key) {
        if (mCache == null) {
            return false;
        }
        try {
            return mCache.get(key) != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(String key) {
        if (mCache == null) {
            return false;
        }
        try {
            return mCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void clear() {
        if (mCache == null) {
            return;
        }
        try {
            mCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
