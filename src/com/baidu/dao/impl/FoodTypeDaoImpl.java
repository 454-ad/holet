package com.baidu.dao.impl;

import com.baidu.dao.FoodTypeDao;
import com.baidu.pojo.FoodType;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class FoodTypeDaoImpl implements FoodTypeDao {
    @Override
    public List<FoodType> findList() {
        List<FoodType> list=null;
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            list = runner.query("select * from foodtype", new BeanListHandler<>(FoodType.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<FoodType> findName(String name) {
        String sql="select * from foodtype where typeName like ?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<FoodType> list=null;
        try {
            list=runner.query(sql,new BeanListHandler<>(FoodType.class),"%"+name+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public FoodType findById(Integer id) {
        String sql="select * from foodtype where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        FoodType foodType=new FoodType();
        try {
            foodType=runner.query(sql,new BeanHandler<>(FoodType.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodType;
    }

    @Override
    public void toUp(FoodType foodType) {
        String sql="update foodtype set typeName=? where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql, foodType.getTypeName(),foodType.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toAdd(FoodType foodType) {
        String sql="insert into foodtype set typeName=? ";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,foodType.getTypeName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean toDel(Integer id) {
        String sql="delete from foodtype where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            return 1== runner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
