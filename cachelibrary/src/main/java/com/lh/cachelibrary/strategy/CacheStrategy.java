package com.lh.cachelibrary.strategy;

/**
 * Created by home on 2017/1/10.
 * 缓存策略
 */

public class CacheStrategy {

    private static Strategy PRIORITY_REMOTE;
    private static Strategy PRIORITY_CACHE;
    private static Strategy ONLY_REMOTE;
    private static Strategy ONLY_CACHE;

    public static Strategy priorityRemote() {
        if (PRIORITY_REMOTE == null) {
            PRIORITY_REMOTE = new PriorityRemoteStrategy();
        }
        return PRIORITY_REMOTE;
    }

    public static Strategy priorityCache() {
        if (PRIORITY_CACHE == null) {
            PRIORITY_CACHE = new PriorityCacheStrategy();
        }
        return PRIORITY_CACHE;
    }

    public static Strategy onlyRemote() {
        if (ONLY_REMOTE == null) {
            ONLY_REMOTE = new OnlyRemoteStrategy();
        }
        return ONLY_REMOTE;
    }

    public static Strategy onlyCache() {
        if (ONLY_CACHE == null) {
            ONLY_CACHE = new OnlyCacheStrategy();
        }
        return ONLY_CACHE;
    }
}
