package com.baidu.servlet;

import com.baidu.pojo.DinnerTable;
import com.baidu.pojo.Food;
import com.baidu.pojo.OrderDetail;
import com.baidu.pojo.Orders;
import com.baidu.service.DinnerTableService;
import com.baidu.service.FoodService;
import com.baidu.service.OrderAdminService;
import com.baidu.service.OrderService;
import com.baidu.service.impl.DinnerTableServiceImpl;
import com.baidu.service.impl.FoodServiceImpl;
import com.baidu.service.impl.OrderAdminServiceImpl;
import com.baidu.service.impl.OrderServiceImpl;
import com.baidu.utils.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OrderAdminServlet extends HttpServlet {
    private Orders orders = new Orders();
    private DinnerTable dinnerTable = new DinnerTable();
    private FoodService foodService=new FoodServiceImpl();
    private DinnerTableService dinnerTableService=new DinnerTableServiceImpl();
    private OrderAdminService orderAdminService=new OrderAdminServiceImpl();
    private OrderService orderService=new OrderServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("list".equals(method)){
            list(req,resp);
        }else if("pay".equals(method)){
            pay(req,resp);
        }
        else if("getOrderDetail".equals(method)){
            getOrderDetail(req,resp);
        }
    }

    //详细
    private void getOrderDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long orderId = Long.valueOf(req.getParameter("orderId"));

        List<OrderDetail> orderDetail = orderAdminService.findOByOrderId(orderId);
        List<Food> list = foodService.findAll();
        req.setAttribute("orderDetail",orderDetail);
        req.setAttribute("food",list);
        req.getRequestDispatcher("/sys/order/orderDetail.jsp").forward(req, resp);
    }

    private void pay(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String table_id =  req.getParameter("tableId");
        long orderId = Long.valueOf(req.getParameter("orderId"));

        //修改orders表中OrderStatus 的状态
        orders = orderAdminService.findById(orderId);
        orders.setOrderStatus(0);

        dinnerTable = dinnerTableService.findById(Integer.parseInt(table_id) );
        //修改table表中餐桌状态
        dinnerTable.setTableStatus(0);
        dinnerTable.setOrderDate(null);

        dinnerTableService.toUp(dinnerTable);
        orderService.updateStatus(orders);

        resp.sendRedirect(req.getContextPath()+"/orderAdmin?method=list");
    }

    //分页
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        Integer pageSize=2;
        Integer count=orderAdminService.getCount();

        PageUtils pageUtils=new PageUtils(page,pageSize,count);
        List<Orders> list = orderAdminService.findPageOList((pageUtils.getCurrentPage() - 1) * pageSize, pageSize);
        List<DinnerTable> table= dinnerTableService.findList();

        req.setAttribute("list",list);
        req.setAttribute("table",table);
        req.setAttribute("pageUtils",pageUtils);
        req.getRequestDispatcher("/sys/order/orderList.jsp").forward(req, resp);

    }
}
