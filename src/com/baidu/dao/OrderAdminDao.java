package com.baidu.dao;

import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;

import java.util.List;

public interface OrderAdminDao {
//分页查询，获取订单数
   Integer getCount();
//分页订单列表查询
    List<Orders> findPageList(Integer i,Integer j);
//订单详情
    List<OrderDetail> findOByOrderId(Long id);
// 根据订单编号 查询 food 获取名字 价格
    List<Food> findByOrderId(long id);
}
