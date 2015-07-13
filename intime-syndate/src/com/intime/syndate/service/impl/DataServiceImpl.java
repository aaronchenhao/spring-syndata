package com.intime.syndate.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intime.syndate.beans.User;
import com.intime.syndate.dao.DataMapper;
import com.intime.syndate.service.DataService;

/**
 * DataServiceImpl
 * 
 * @author 陈浩
 * @date 2015-7-13
 *
 */
@Service("dataServiceImpl")
public class DataServiceImpl implements DataService{

    @Autowired
    private DataMapper dataMapperImpl;
    
    @Override
    public void doSyndata() {
        // TODO Auto-generated method stub
        List<User> listAll = new ArrayList<User>();
        List<User> listSlave1 = new ArrayList<User>();
        
        //获取主数据库所有数据
        listAll = dataMapperImpl.masterSelectDatas();
        
        //分类数据
        for(User u : listAll) {
            if("1".equals(u.getUserName())) {
                listSlave1.add(u);
            }
        }
        
        //插入从数据
        dataMapperImpl.slave1InsertDatas(listSlave1);
        
        
        
    }

}
