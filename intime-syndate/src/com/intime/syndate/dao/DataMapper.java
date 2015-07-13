package com.intime.syndate.dao;

import java.util.List;

import com.intime.syndate.beans.User;

public interface DataMapper {
    /**
     * 主数据库获取数据
     * 规则：master＋操作名
     * 
     */
    public List<User> masterSelectDatas();
    
    /**
     * 主数据库更新数据
     * 规则：master＋操作名
     * 
     */
    public List<User> masterUpdateDatas();
    
    /**
     * 从数据库slave1插入数据
     * 规则：slave1＋操作名
     * 
     */
    public void slave1InsertDatas(List<User> u);

}
