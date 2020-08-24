package com.baidu.servlet;

import com.baidu.pojo.DinnerTable;
import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;
import com.baidu.pojo.Orders;
import com.baidu.service.DinnerTableService;
import com.baidu.service.FoodService;
import com.baidu.service.FoodTypeService;
import com.baidu.service.OrderService;
import com.baidu.service.impl.DinnerTableServiceImpl;
import com.baidu.service.impl.FoodServiceImpl;
import com.baidu.service.impl.FoodTypeServiceImpl;
import com.baidu.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

public class OrderServlet extends HttpServlet {
    private DinnerTable dinnerTable=new DinnerTable();
    private Orders orders=new Orders();
    private FoodService foodService=new FoodServiceImpl();
    private FoodTypeService foodTypeService=new FoodTypeServiceImpl();
    private OrderService orderService=new OrderServiceImpl();
    private DinnerTableService dinnerTableService=new DinnerTableServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        if ("putInCar".equals(method)){
            putInCar(req,resp);
        }else if ("removeOrder".equals(method)){
            removeOrder(req,resp);
        }else if ("alterSorder".equals(method)){
            alterSorder(req,resp);
        }else if ("takeOrder".equals(method)){
            takeOrder(req,resp);
        }else if ("callPay".equals(method)){
            callPay(req,resp);
        }else if ("sayOrder".equals(method)){
            sayOrder(req,resp);
        }
    }

    //清单
    private void sayOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<FoodType> foodTypes = foodTypeService.findList();
        req.setAttribute("foodtypes",foodTypes);
        req.getRequestDispatcher("/app/detail/clientCart.jsp").forward(req, resp);
    }

    //结账
    private void callPay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        orders = (Orders) session.getAttribute("orders");
        Object table_id = session.getAttribute("table_id");

        dinnerTable = dinnerTableService.findById(Integer.parseInt((String) table_id));
        dinnerTable.setTableStatus(0);
        dinnerTable.setOrderDate(null);

        dinnerTableService.toUp(dinnerTable);

        orders.setOrderStatus(0);
        orderService.updateStatus(orders);
        session.invalidate();       //销毁session
        req.getRequestDispatcher("/app/detail/jiezhang.jsp").forward(req, resp);
    }

    //下单
    private void takeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService.takeOrder(req);

        List<FoodType> foodTypes = foodTypeService.findList();
        req.setAttribute("foodtypes",foodTypes);
        req.getRequestDispatcher("/app/detail/clientOrderList.jsp").forward(req, resp);
    }

    //修改菜品数量
    private void alterSorder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fid = req.getParameter("fid");
        String snumberS = req.getParameter("snumber");
        Food food = foodService.findById(Integer.parseInt(fid));

        HttpSession session = req.getSession();
        LinkedHashMap<Food, Integer> map =(LinkedHashMap<Food, Integer>) session.getAttribute("cart");

//        map.put(food,Integer.parseInt(snumberS));
        for(Food food1 : map.keySet()) {
            if (food1.getFoodName().equals(food.getFoodName())) {
                map.put(food1,Integer.parseInt(snumberS));
                break;
            }
        }
        List<FoodType> foodTypes = foodTypeService.findList();
        req.setAttribute("foodtypes",foodTypes);
        req.getRequestDispatcher("app/detail/clientCart.jsp").forward(req,resp);

    }


    //删除餐车里的菜品
    private void removeOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String foodId = req.getParameter("fid");
        Food food = foodService.findById(Integer.parseInt(foodId));

        HttpSession session = req.getSession();
        LinkedHashMap<Food,Integer> map=(LinkedHashMap<Food, Integer>) session.getAttribute("cart");

//         map.remove(food);
        for(Food food1 : map.keySet()) {
            if (food1.getFoodName().equals(food.getFoodName())) {
                map.remove(food1);
                break;
            }
        }


//        System.out.println(map);
        List<FoodType> foodTypes = foodTypeService.findList();
        req.setAttribute("foodtypes",foodTypes);

        req.getRequestDispatcher("app/detail/clientCart.jsp").forward(req, resp);
    }

    //放入餐车
    private void putInCar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String foodId = req.getParameter("food");
        Food food = foodService.findById(Integer.parseInt(foodId));

        HttpSession session = req.getSession();
        Object c = session.getAttribute("cart");

        if(null == c) {
            //创建
            LinkedHashMap<Food, Integer> map = new LinkedHashMap<Food,Integer>();
            map.put(food, 1);
            session.setAttribute("cart", map);
        }else {
            LinkedHashMap<Food, Integer> map=(LinkedHashMap<Food, Integer>) c;

            boolean b=true;
            for(Food food1 : map.keySet()) {
                if (food1.getFoodName().equals(food.getFoodName())) {
                    Integer num = map.get(food1);
                    num++;
                    map.put(food1, num);
                    b = false;
                    break;
                }
            }
            if (b){
                //无此菜品
                map.put(food,1);
            }
        }
        //菜系列表
        List<FoodType> foodTypes = foodTypeService.findList();
        req.setAttribute("foodtypes",foodTypes);
        req.getRequestDispatcher("/index?method=getMenu").forward(req, resp);
    }
}
