package com.baidu.servlet;

import com.baidu.pojo.FoodType;
import com.baidu.service.FoodTypeService;
import com.baidu.service.impl.FoodTypeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FoodTypeServlet extends HttpServlet {
    private FoodType foodType=new FoodType();
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
        if ("show".equals(method)){
            findbyId(req,resp);
        }else if ("list".equals(method)){
            list(req,resp);
        }else if ("search".equals(method)){
            search(req, resp);
        }else if ("add".equals(method)){
            add(req,resp);
        }else if ("update".equals(method)){
            update(req, resp);
        }else if ("delete".equals(method)){
            delete(req,resp);
        }
    }


    //删除
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        foodTypeService.toDel(Integer.parseInt(id));

        resp.sendRedirect(req.getContextPath() + "/foodType?method=list");
    }

    //按ID
    private void findbyId(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        foodType = foodTypeService.findById(id);
        req.setAttribute("type",foodType);
        req.getRequestDispatcher("/sys/foodtype/updateCuisine.jsp").forward(req, resp);
    }
    //菜系列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FoodType> list = foodTypeService.findList();
        req.setAttribute("list",list);
        req.getRequestDispatcher("sys/foodtype/cuisineList.jsp").forward(req,resp);
    }

    //添加
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String typeName = req.getParameter("name");

        if(typeName!=null&&typeName.trim().length()>0) {
            FoodType foodType = new FoodType();
            foodType.setTypeName(typeName);
            foodTypeService.toAdd(foodType);

            resp.sendRedirect(req.getContextPath() + "/foodType?method=list");
        }
    }
    //修改
    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String keyword = req.getParameter("typeName");
        Integer id = Integer.parseInt(req.getParameter("id"));
        foodType.setTypeName(keyword);

        foodTypeService.toUp(foodType);

        resp.sendRedirect(req.getContextPath()+"/foodType?method=list");
    }

    //模糊查询
    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<FoodType> list = foodTypeService.findName(keyword);
        req.setAttribute("list", list);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/sys/foodtype/cuisineList.jsp").forward(req, resp);

    }
}
