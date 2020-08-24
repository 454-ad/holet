package com.baidu.servlet;

import com.baidu.pojo.DinnerTable;
import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;
import com.baidu.service.DinnerTableService;
import com.baidu.service.FoodService;
import com.baidu.service.FoodTypeService;
import com.baidu.service.impl.DinnerTableServiceImpl;
import com.baidu.service.impl.FoodServiceImpl;
import com.baidu.service.impl.FoodTypeServiceImpl;
import com.baidu.utils.PageUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class IndexServlet extends HttpServlet {
    private DinnerTable dinnerTable=new DinnerTable();
    private DinnerTableService dinnerTableService=new DinnerTableServiceImpl();
    private FoodService foodService=new FoodServiceImpl();
    private FoodTypeService foodTypeService=new FoodTypeServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");
        if ("toApp".equals(method)){
            list(req,resp);
        }else if ("getMenu".equals(method)){
            getMenu(req,resp);
        }else if ("getTypeFood".equals(method)){
            getTypeFood(req,resp);
        }else if ("getFoodDetail".equals(method)){
            getFoodDetail(req,resp);
        }else if ("searchFood".equals(method)){
            search(req,resp);
        }
    }

    //模糊搜索
    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");


        List<Food> list = foodService.findName(keyword);

        List<FoodType> foodTypes = foodTypeService.findList();

        req.setAttribute("list",list);
        req.setAttribute("foodtypes",foodTypes);
        req.getRequestDispatcher("/app/detail/caidan.jsp").forward(req, resp);

    }


    //跳转到菜品详细
    private void getFoodDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String foodId = req.getParameter("food");

        Food food = foodService.findById(Integer.parseInt(foodId));

        List<FoodType> foodtypes = foodTypeService.findList();

        req.setAttribute("food",food);
        req.setAttribute("foodtypes",foodtypes);
        req.getRequestDispatcher("app/detail/caixiangxi.jsp").forward(req,resp);
    }

    //菜系
    private void getTypeFood(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("type","type");
        Integer foodtype = Integer.parseInt(req.getParameter("foodtype"));

        Integer pageSize=6;
        Integer count=foodService.countType( foodtype);
        String page = req.getParameter("page");
        PageUtils pageUtils=new PageUtils(page,pageSize,count);

        List<Food> list = foodService.findTypeById(foodtype, (pageUtils.getCurrentPage() - 1) * pageSize, pageSize);

        List<FoodType> foodTypes = foodTypeService.findList();

        req.setAttribute("list",list);
        req.setAttribute("foodtypes",foodTypes);
        req.setAttribute("pageUtils",pageUtils);

        req.getRequestDispatcher("/app/detail/caidan.jsp").forward(req, resp);
    }

    //菜品分页
    private void getMenu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Object tableid = session.getAttribute("table_id");
        if (Objects.isNull(tableid)){
            String table_id = req.getParameter("table_id");
            session.setAttribute("table_id",table_id);

            dinnerTableService.findById(Integer.parseInt(table_id));
        }
        dinnerTable.setTableStatus(1);
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dinnerTable.setOrderDate(new Date());
        dinnerTableService.toUp(dinnerTable);

        Integer pageSize=6;
        Integer count=foodService.count();
        String page = req.getParameter("page");
        PageUtils pageUtils=new PageUtils(page,pageSize,count);
        List<Food> list = foodService.findList((pageUtils.getCurrentPage() - 1) * pageSize, pageSize);

        List<FoodType> foodtypes = foodTypeService.findList();
        req.setAttribute("list",list);
        req.setAttribute("foodtypes",foodtypes);
        req.setAttribute("pageUtils",pageUtils);
        req.getRequestDispatcher("app/detail/caidan.jsp").forward(req,resp);
    }


    //餐桌列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DinnerTable> list = dinnerTableService.findList();
        req.setAttribute("listDinnerTable",list);
        req.getRequestDispatcher("app/index.jsp").forward(req,resp);
    }
}
