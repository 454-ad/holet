package com.baidu.dao.impl;

import com.baidu.dao.OrderDetailDao;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class OrderDetailDaoImpl implements OrderDetailDao {
    @Override
    public void toAdd(OrderDetail orderDetail) {
        String sql="insert into orderdetail(orderId,food_id,foodCount) values(?,?,?)";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql, orderDetail.getOrderId(), orderDetail.getFood_id(), orderDetail.getFoodCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStatus(Orders orders) {
        String sql = "update orders set table_id =?,orderDate =?,totalPrice =?,orderStatus =? where id =?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,orders.getTable_id(),orders.getOrderDate(),orders.getTotalPrice(),orders.getOrderStatus(),orders.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Orders findByOId(Long id) {
        String sql="select * from orders where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        Orders orders=new Orders();
        try {
            orders=runner.query(sql,new BeanHandler<>(Orders.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
