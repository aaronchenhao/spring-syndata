package com.intime.syndate;

import org.apache.ibatis.session.SqlSession;

public interface CustomSqlSession extends SqlSession{
    public int slave1InsertDatas(String statement, Object parameter);
}
