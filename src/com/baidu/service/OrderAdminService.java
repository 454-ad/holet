package com.baidu.service;

import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;

import java.util.List;

public interface OrderAdminService {
    public Integer getCount();

    //分页订单列表查询
    public List<Orders> findPageOList(Integer i, Integer j);

    public List<OrderDetail> findOByOrderId(long id);

    public List<Food> findByOrderId(long id);


    //根据ID
    public Orders findById(Long id);
}
