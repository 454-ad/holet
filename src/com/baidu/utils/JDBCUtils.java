package com.baidu.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class JDBCUtils {
    private static ComboPooledDataSource dataSource=null;
    private static ThreadLocal<Connection> tl=new ThreadLocal<>();


    static {
        dataSource=new ComboPooledDataSource();
    }

    private static DataSource getDataSource(){
        return dataSource;
    }

    public static Connection getConnection(){

        try {
            Connection connection=tl.get();

            if (Objects.isNull(connection)){
                connection=dataSource.getConnection();
                tl.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static QueryRunner getQueryRunner(){
        return new QueryRunner(dataSource);
    }

    public static void close(Connection connection, ResultSet rs, PreparedStatement preparedStatement){

        if (!Objects.isNull(rs)){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(!Objects.isNull(preparedStatement)){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!Objects.isNull(connection)){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
