package com.baidu.service;

import com.baidu.pojo.Orders;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    //修改
    void updateStatus(Orders orders);
    //结账
    void takeOrder(HttpServletRequest req);
}
