package com.intime.syndate.service;

public interface DataService {
    /**
     * 同步数据方法
     * 规则：master＋操作名
     */
    public void doSyndata();
}
