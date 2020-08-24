package com.baidu.dao;

import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;

public interface OrderDetailDao {
//    添加
    void toAdd(OrderDetail orderDetail);
//    修改预定
    void updateStatus(Orders orders);
//    查询订单--id
    Orders findByOId(Long id);
}
