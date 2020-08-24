package com.baidu.service.impl;

import com.baidu.dao.DinnerTableDao;
import com.baidu.dao.OrderDao;
import com.baidu.dao.OrderDetailDao;
import com.baidu.dao.impl.DinnerTableDaoImpl;
import com.baidu.dao.impl.OrderDaoImpl;
import com.baidu.dao.impl.OrderDetailDaoImpl;
import com.baidu.pojo.DinnerTable;
import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;
import com.baidu.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao=new OrderDaoImpl();
    private DinnerTableDao dinnerTableDao=new DinnerTableDaoImpl();
    private OrderDetailDao orderDetailDao=new OrderDetailDaoImpl();
    @Override
    public void updateStatus(Orders orders) {
        orderDetailDao.updateStatus(orders);
    }

    @Override
    public void takeOrder(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        //table_id
        Integer table_id=Integer.parseInt((String) httpSession.getAttribute("table_id"));

        //cart
        LinkedHashMap<Food, Integer> map =(LinkedHashMap<Food, Integer>) httpSession.getAttribute("cart");

        Long id=Math.round(Math.random() * 100000);

        double totalPrice=0.0;
        Set<Map.Entry<Food, Integer>> set = map.entrySet();
        for (Map.Entry<Food, Integer> entry : set) {

            Food food = entry.getKey();
            Integer num = entry.getValue();
            totalPrice+=(food.getPrice()*num);

            //插入到订单详细表
            OrderDetail detail = new OrderDetail();
            detail.setFood_id(food.getId());
            detail.setFoodCount(num);
            detail.setOrderId(id);

            orderDetailDao.toAdd(detail);

        }

        //下单时执行
        //查询餐桌表中餐桌状态  修改订单表中餐桌的状态
        DinnerTable dinnerTable = new DinnerTable();
        dinnerTable = dinnerTableDao.findById(table_id);
        int a = dinnerTable.getTableStatus();


        //更改餐桌状态和下单时间
        Orders orders = new Orders();
        orders.setId(id);
        orders.setOrderDate(new Date());
        //修改订单表中餐桌的状态
        orders.setOrderStatus(a);
        orders.setTable_id(table_id);
        orders.setTotalPrice(totalPrice);
        orderDao.add(orders);

        //用于获取 orders 表中 雪花算法产生的id
        httpSession.setAttribute("orders",orders);

    }
}
