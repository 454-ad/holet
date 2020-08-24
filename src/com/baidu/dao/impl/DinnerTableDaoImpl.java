package com.baidu.dao.impl;

import com.baidu.dao.DinnerTableDao;
import com.baidu.pojo.DinnerTable;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class DinnerTableDaoImpl implements DinnerTableDao {
    @Override
    public List<DinnerTable> findList() {
        String sql="select * from dinnertable";
        QueryRunner qr = JDBCUtils.getQueryRunner();
        List<DinnerTable> list=null;
        try {
            list = qr.query(sql, new BeanListHandler<>(DinnerTable.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<DinnerTable> findName(String name) {
        String sql="select * from dinnertable WHERE tableName like ?";
        QueryRunner qr = JDBCUtils.getQueryRunner();
        List<DinnerTable> list=null;
        try {
            list = qr.query(sql, new BeanListHandler<>(DinnerTable.class), "%"+name+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DinnerTable findById(Integer id) {
        String sql="select * from dinnertable where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        DinnerTable dinnerTable=new DinnerTable();
        try {
            dinnerTable=runner.query(sql,new BeanHandler<>(DinnerTable.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dinnerTable;
    }

    @Override
    public Boolean toDel(Integer id) {
        String sql="delete from dinnertable where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            return 1==runner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void toAdd(DinnerTable dinnerTable) {
        String sql="insert into dinnertable set tableName=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,dinnerTable.getTableName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean toUp(DinnerTable dinnerTable) {
        String sql="update dinnertable set tableStatus =?,orderDate =? where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            return 1== runner.update(sql, dinnerTable.getTableStatus(), dinnerTable.getOrderDate(),dinnerTable.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
