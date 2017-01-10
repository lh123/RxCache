package com.lh.cachelibrary.convert;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.lh.cachelibrary.utils.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

/**
 * Created by home on 2017/1/10.
 * 基于Gson的序列化（需要Type参数）
 */

public class GsonDiskConverter implements DiskConverter {
    private Gson gson;

    public GsonDiskConverter() {
        gson = new Gson();
    }

    @Override
    public <T> T load(InputStream stream, Type type) {
        if (type == null){
            throw new IllegalArgumentException("type can not be null");
        }
        InputStreamReader inr = new InputStreamReader(stream);
        T result = gson.fromJson(inr,type);
        Utils.closeStream(inr);
        return result;
    }

    @Override
    public <T> boolean save(OutputStream os, T data) {
        OutputStreamWriter osw = new OutputStreamWriter(os);
        boolean success = false;
        try {
            gson.toJson(data,osw);
            success = true;
        } catch (JsonIOException e){
            e.printStackTrace();
        }
        Utils.closeStream(osw);
        return success;
    }
}
