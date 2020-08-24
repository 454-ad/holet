package com.baidu.dao;

import com.baidu.pojo.Orders;

import java.util.List;

public interface OrderDao {
    //添加
    void add(Orders orders);
    //查询列表
    List<Orders> findList();
    //修改
    void update(Orders orders);
}
