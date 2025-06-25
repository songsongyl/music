package com.music.demo.login.util;

import cn.hutool.bloomfilter.BitMapBloomFilter;

/**
 * 使用单例模式创建一个布隆过滤器工具类
 */
public class BloomFilterUtil {

    private static final BloomFilterUtil instance = new BloomFilterUtil();
    private static final BitMapBloomFilter filter = new BitMapBloomFilter(10);

    public static BloomFilterUtil getInstance() {
        return instance;
    }

    public void add(String str) {
        filter.add(str);
    }

    public boolean contains(String str) {
        return filter.contains(str);
    }
}
