package com.baidu.service.impl;

import com.baidu.dao.OrderAdminDao;
import com.baidu.dao.OrderDetailDao;
import com.baidu.dao.impl.OrderAdminDaoImpl;
import com.baidu.dao.impl.OrderDetailDaoImpl;
import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;
import com.baidu.service.OrderAdminService;

import java.util.List;

public class OrderAdminServiceImpl implements OrderAdminService {
    private OrderAdminDao orderAdminDao=new OrderAdminDaoImpl();
    private OrderDetailDao orderDetailDao=new OrderDetailDaoImpl();

    @Override
    public Integer getCount() {
        return orderAdminDao.getCount();
    }

    @Override
    public List<Orders> findPageOList(Integer i, Integer j) {
        return orderAdminDao.findPageList(i, j);
    }

    @Override
    public List<OrderDetail> findOByOrderId(long id) {
        return orderAdminDao.findOByOrderId(id);
    }

    @Override
    public List<Food> findByOrderId(long id) {
        return orderAdminDao.findByOrderId(id);
    }

    @Override
    public Orders findById(Long id) {
        return orderDetailDao.findByOId(id);
    }
}
