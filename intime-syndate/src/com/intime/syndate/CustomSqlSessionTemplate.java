package com.intime.syndate;

import static java.lang.reflect.Proxy.newProxyInstance;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 动态代理SqlSessionTemplate
 * 在执行SqlSessionTemplate操作数据库方法之前，根据方法名
 * 动态判断是发往数据库
 * @author 陈浩
 * @date 2015-7-13
 */
public class CustomSqlSessionTemplate  implements  CustomSqlSession{
    
    private static final String SLAVE1 = "slave1";
    
   // private static final String SLAVE2 = "slave2";//TODO
    
    private static final String MASTER = "master";
    

    private SqlSessionTemplate sqlSessionTemplate;

    private final CustomSqlSession sqlSessionProxy;
    
    

    public CustomSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.sqlSessionProxy = (CustomSqlSession) newProxyInstance(
                CustomSqlSession.class.getClassLoader(),
                new Class[] { CustomSqlSession.class }, new SqlSessionInterceptor());
    }

    /**
     * 方法拦截
     * 拦截CustomSqlSessionTemplate的方法，
     * 根据方法名动态获取数据库链接
     * @author 陈浩
     * @date 2015-7-13
     */
    private class SqlSessionInterceptor implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            try {
                boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
                if(synchronizationActive){
                    return method.invoke(sqlSessionTemplate, args);
                }else{
                    String methodName = (String)args[0];
                    if(methodName.startsWith(SLAVE1)){
                        DataSourceHandler.setSlave1();
                    }else if(methodName.startsWith(MASTER)){
                        DataSourceHandler.setMaster();
                    }
                    Object result = method.invoke(sqlSessionTemplate, args);    
                    //清理工作
                    DataSourceHandler.clearDataSource();
                    return result;
                }               
            } catch (Exception e) {
                throw e;
            }
        }
    }

    public <T> T selectOne(String statement) {
        return sqlSessionProxy.selectOne(statement);
    }

    public <T> T selectOne(String statement, Object parameter) {
        return sqlSessionProxy.selectOne(statement, parameter);
    }

    public <E> List<E> selectList(String statement) {
        return sqlSessionProxy.selectList(statement);
    }

    public <E> List<E> selectList(String statement, Object parameter) {
        return sqlSessionProxy.selectList(statement, parameter);
    }

    public <E> List<E> selectList(String statement, Object parameter,
            RowBounds rowBounds) {
        return sqlSessionProxy.selectList(statement, parameter, rowBounds);
    }

    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return sqlSessionProxy.selectMap(statement, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter,
            String mapKey) {
        return sqlSessionProxy.selectMap(statement, parameter, mapKey);
    }

    public <K, V> Map<K, V> selectMap(String statement, Object parameter,
            String mapKey, RowBounds rowBounds) {
        return sqlSessionProxy.selectMap(statement, parameter, mapKey, rowBounds);
    }

    public void select(String statement, Object parameter, ResultHandler handler) {
        sqlSessionProxy.select(statement, parameter, handler);      
    }

    public void select(String statement, ResultHandler handler) {
        sqlSessionProxy.select(statement, handler);
    }

    public void select(String statement, Object parameter, RowBounds rowBounds,
            ResultHandler handler) {
        sqlSessionProxy.select(statement, parameter, rowBounds, handler);
    }

    public int insert(String statement) {
        return sqlSessionProxy.insert(statement);
    }

    public int insert(String statement, Object parameter) {
        return sqlSessionProxy.insert(statement, parameter);
    }

    public int update(String statement) {
        return sqlSessionProxy.update(statement);
    }

    public int update(String statement, Object parameter) {
        return sqlSessionProxy.update(statement, parameter);
    }

    public int delete(String statement) {
        return sqlSessionProxy.delete(statement);
    }

    public int delete(String statement, Object parameter) {
        return sqlSessionProxy.delete(statement, parameter);
    }

    public void commit() {
        sqlSessionProxy.commit();
    }

    public void commit(boolean force) {
        sqlSessionProxy.commit(force);
    }

    public void rollback() {
        sqlSessionProxy.rollback();
    }

    public void rollback(boolean force) {
        sqlSessionProxy.rollback(force);
    }

    public List<BatchResult> flushStatements() {
        return sqlSessionProxy.flushStatements();
    }

    public void close() {
        sqlSessionProxy.close();
    }

    public void clearCache() {
        sqlSessionProxy.clearCache();
    }

    public Configuration getConfiguration() {
        return sqlSessionProxy.getConfiguration();
    }

    public <T> T getMapper(Class<T> type) {
        return sqlSessionProxy.getMapper(type);
    }

    public Connection getConnection() {
        return sqlSessionProxy.getConnection();
    }
    
    
    
    //todo
    public int slave1InsertDatas(String statement, Object parameter) {
        return sqlSessionProxy.insert(statement, parameter);
    }

}
