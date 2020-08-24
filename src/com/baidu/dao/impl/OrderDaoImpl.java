package com.baidu.dao.impl;

import com.baidu.dao.OrderDao;
import com.baidu.pojo.Orders;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void add(Orders orders) {
        String sql="insert into orders(id,table_id,orderDate,totalPrice,orderStatus) values(?,?,?,?,?)";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,orders.getId(), orders.getTable_id(), orders.getOrderDate(), orders.getTotalPrice(), orders.getOrderStatus()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Orders> findList() {
        List<Orders> list=null;
        String sql ="SELECT * from orders";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            list = runner.query(sql, new BeanListHandler<>(Orders.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Orders orders) {

    }
}
