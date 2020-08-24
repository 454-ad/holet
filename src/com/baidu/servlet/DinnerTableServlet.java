package com.baidu.servlet;

import com.baidu.pojo.DinnerTable;
import com.baidu.service.DinnerTableService;
import com.baidu.service.impl.DinnerTableServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;




public class DinnerTableServlet extends HttpServlet {
    private DinnerTable dinnerTable=new DinnerTable();
    private DinnerTableService dinnerTableService=new DinnerTableServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        if ("list".equals(method)){
            list(req,resp);
        }else if ("search".equals(method)){
            search(req,resp);
        }else if ("add".equals(method)){
            add(req,resp);
        }else if ("up1".equals(method)){
            up(req,resp);
        }else if ("del".equals(method)){
            del(req,resp);
        }
    }

    //删除
    private void del(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        dinnerTableService.toDel(Integer.parseInt(id));
        resp.sendRedirect(req.getContextPath()+"/table?method=list");
    }

    //修改
    private void up(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        //按id 查询 获取餐桌状态
        dinnerTable = dinnerTableService.findById(id);

        if(dinnerTable.getTableStatus()==1){
            dinnerTable.setTableStatus(0);
            dinnerTable.setOrderDate(null);
        }else {
            dinnerTable.setTableStatus(1);
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            dinnerTable.setOrderDate(new Date());
        }

        dinnerTableService.toUp(dinnerTable);

        resp.sendRedirect(req.getContextPath()+"/table?method=list");   //重定向

    }

    //添加餐桌
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        if(tableName !=null && tableName.trim().length()>0) {
            dinnerTable.setTableName(tableName);
            dinnerTableService.toAdd(dinnerTable);

            resp.sendRedirect(req.getContextPath()+"/table?method=list");
        }

    }
    //模糊查询
    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String keyword = req.getParameter("keyword");

        List<DinnerTable> list = dinnerTableService.findName(keyword);
        req.setAttribute("list",list);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("sys/board/boardList.jsp").forward(req,resp);
    }

    //餐桌列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<DinnerTable> list = dinnerTableService.findList();

        req.setAttribute("list",list);
        req.getRequestDispatcher("sys/board/boardList.jsp").forward(req,resp);
    }
}
