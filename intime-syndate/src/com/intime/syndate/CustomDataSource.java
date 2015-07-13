package com.intime.syndate;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 动态数据源
 * @author 陈浩
 * @date 2015-7-13
 */
public class CustomDataSource extends AbstractRoutingDataSource{
    
    private final Logger log = Logger.getLogger(this.getClass());
    
    private AtomicInteger counter = new AtomicInteger();
    
    /**
     * master库 dataSource
     */
    private DataSource master;
    
    /**
     * slaves1
     */
    private DataSource slave1;
    

    @Override
    protected Object determineCurrentLookupKey() {
        //do nothing
        return null;
    }
    
    @Override
    public void afterPropertiesSet(){
        //do nothing
    }

    /**
     * 根据标识
     * 获取数据源
     * 
     */
    @Override
    protected DataSource determineTargetDataSource() {
        DataSource returnDataSource = null;
        if(DataSourceHandler.isMaster()){
            returnDataSource = master;
        }else if(DataSourceHandler.isSlave1()){
            returnDataSource = slave1;
        }else{
            returnDataSource = master;
        }
        if(returnDataSource instanceof ComboPooledDataSource){
            ComboPooledDataSource source = (ComboPooledDataSource)returnDataSource;
            String jdbcUrl = source.getJdbcUrl();
            log.debug("jdbcUrl:" + jdbcUrl);
        }       
        return returnDataSource;
    }

    public DataSource getMaster() {
        return master;
    }

    public void setMaster(DataSource master) {
        this.master = master;
    }

    public DataSource getSlave1() {
        return slave1;
    }

    public void setSlave1(DataSource slave1) {
        this.slave1 = slave1;
    }


    
    
}
