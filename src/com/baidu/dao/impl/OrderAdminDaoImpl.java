package com.baidu.dao.impl;

import com.baidu.dao.OrderAdminDao;
import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderAdminDaoImpl implements OrderAdminDao {
    @Override
    public Integer getCount() {
        String sql="select count(*) from orders";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        Integer count=0;
        try {
            count = ((Long)runner.query(sql,new ScalarHandler<>())).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<Orders> findPageList(Integer i, Integer j) {
        String sql="select * from orders limit ?,?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Orders> list = null;
        try {
            list = runner.query(sql,new BeanListHandler<>(Orders.class),i,j);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderDetail> findOByOrderId(Long id) {
        String sql="SELECT * FROM orderdetail WHERE orderId =?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<OrderDetail> list = null;
        try {
            list = runner.query(sql,new BeanListHandler<>(OrderDetail.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Food> findByOrderId(long id) {
        String sql="SELECT * FROM food a JOIN orderdetail b ON a.id=b.food_id WHERE b.orderId=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Food> list = null;
        try {
            list = runner.query(sql,id,new BeanListHandler<>(Food.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
