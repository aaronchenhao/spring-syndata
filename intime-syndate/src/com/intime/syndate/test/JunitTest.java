package com.intime.syndate.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.intime.syndate.beans.User;
import com.intime.syndate.dao.DataMapper;

public class JunitTest {
    DataMapper dataMapper;
    @Before
    public void before(){        
        String[] xmls = new String[]{ "classpath:applicationContext.xml","classpath:dataSource.xml" };
        //String[] xmls = new String[]{ "classpath:applicationContext.xml","classpath:dataSource.xml"};
        ApplicationContext context = new ClassPathXmlApplicationContext(xmls);
        dataMapper = (DataMapper) context.getBean("dataMapperImpl");
    }
    
    @Test
    public void testUserMapper() throws Throwable{
        List<User> list = new ArrayList<User>();
        for(int i=0; i<2 ; i++) {
            User user = new User();
            user.setUserName("ttt"+i);
            list.add(user);
        }
        User user = new User();
        //user.setUserId(3726434L);
        user.setUserName("ttt");
        dataMapper.slave1InsertDatas(list);
    }

    @Test
    public void testSelectByUserNameAndPwd() throws Throwable{
        List list = dataMapper.masterSelectDatas();
        list.size();
        
       
    }
}
