package com.lh.cachelibrary.convert;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 转换器接口
 */

public interface DiskConverter {
    <T> T load(InputStream stream, Type type);

    <T> boolean save(OutputStream os, T data);
}
