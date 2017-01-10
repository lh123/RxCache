package com.lh.cachelibrary.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by home on 2017/1/10.
 * 关闭Stream的工具类
 */

public class Utils {
    public static void closeStream(Closeable... closeables) {
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
