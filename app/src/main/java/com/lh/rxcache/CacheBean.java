package com.lh.rxcache;

import java.io.Serializable;

/**
 * Created by home on 2017/1/10.
 */

public class CacheBean implements Serializable {
    private String data;

    public CacheBean(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
