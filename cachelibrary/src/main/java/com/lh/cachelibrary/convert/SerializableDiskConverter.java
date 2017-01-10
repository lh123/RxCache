package com.lh.cachelibrary.convert;

import com.lh.cachelibrary.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 可序化对象的转换器
 */

public class SerializableDiskConverter implements DiskConverter {
    @Override
    public <T> T load(InputStream stream, Type type) {
        T data = null;
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(stream);
            data = (T) oin.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            Utils.closeStream(oin);
        }
        return data;
    }

    @Override
    public <T> boolean save(OutputStream os, T data) {
        boolean success = false;
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(data);
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Utils.closeStream(oos);
        }
        return success;
    }
}
