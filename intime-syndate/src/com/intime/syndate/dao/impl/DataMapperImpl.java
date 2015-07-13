package com.intime.syndate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.intime.syndate.CustomSqlSessionTemplate;
import com.intime.syndate.beans.User;
import com.intime.syndate.dao.DataMapper;
/**
 * 数据Mapper
 * @author 陈浩
 * @date 2015-7-13
 */
@Repository("dataMapperImpl")
public class DataMapperImpl implements DataMapper{
    @Autowired
    private CustomSqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<User> masterSelectDatas() {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.selectList("masterSelectDatas");
    }

    @Override
    public void slave1InsertDatas(List<User> u) {
        // TODO Auto-generated method stub
        sqlSessionTemplate.slave1InsertDatas("slave1InsertDatas", u);
    }

    @Override
    public List<User> masterUpdateDatas() {
        // TODO Auto-generated method stub
        return null;
    }

}
