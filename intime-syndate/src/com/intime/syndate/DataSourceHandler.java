package com.intime.syndate;

import javax.sql.DataSource;

/**
 * DataSourceHolder
 * 
 * @author 陈浩
 * @date 2015-7-13
 */
public class DataSourceHandler {

    private static final String MASTER = "master";

    private static final String SLAVE1 = "slave1";

    /**
     * dataSource master or slave
     */
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    /**
     * master local
     */
    private static final ThreadLocal<DataSource> masterLocal = new ThreadLocal<DataSource>();

    /**
     * master local
     */
    private static final ThreadLocal<DataSource> slaveLocal = new ThreadLocal<DataSource>();

    /**
     * 设置数据源
     * 
     * @param dataSourceKey
     * @author 陈浩
     * @date 2015-6-17
     */
    private static void setDataSource(String dataSourceKey) {
        dataSources.set(dataSourceKey);
    }

    /**
     * 获取数据源
     * 
     * @return
     * @author 陈浩
     * @date 2015-6-17
     */
    private static String getDataSource() {
        return (String) dataSources.get();
    }

    /**
     * 标志为master
     */
    public static void setMaster() {
        setDataSource(MASTER);
    }

    /**
     * 标志为slave
     */
    public static void setSlave1() {
        setDataSource(SLAVE1);
    }
    
    /**
     * 将master放入threadlocal
     * @param master
     */
    public static void setMaster(DataSource master) {
        masterLocal.set(master);
    }
    
    /**
     * 将slave放入threadlocal
     * @param master
     */
    public static void setSlave(DataSource slave) {
        slaveLocal.set(slave);
    }

    
    public static boolean isMaster() {
        return getDataSource() == MASTER;
    }

    public static boolean isSlave1() {
        return getDataSource() == SLAVE1;
    }

    /**
     * 清除thread local中的数据源
     * 
     * @author 陈浩
     * @date 2015-6-17
     */
    public static void clearDataSource() {
        dataSources.remove();
        masterLocal.remove();
        slaveLocal.remove();
    }
}
