package com.baidu.servlet;

import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;
import com.baidu.service.FoodService;
import com.baidu.service.FoodTypeService;
import com.baidu.service.impl.FoodServiceImpl;
import com.baidu.service.impl.FoodTypeServiceImpl;
import com.baidu.utils.PageUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

//

public class FoodServlet extends HttpServlet {
    private Food food=new Food();
    private FoodService foodService=new FoodServiceImpl();
    private FoodTypeService foodTypeService=new FoodTypeServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if ("list".equals(method)){
            list(req,resp);
        }else if ("search".equals(method)){
            search(req,resp);
        }else if ("update".equals(method)){
            update(req, resp);
        }else if ("add".equals(method)){
            add(req,resp);
        }else if ("show".equals(method)){
            findById(req,resp);
        }else if ("toSaveFood".equals(method)){
            toSaveFood(req,resp);
        }else if ("delete".equals(method)){
            delete(req,resp);
        }
    }

    //删除
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        foodService.toDel(Integer.parseInt(id));
        resp.sendRedirect(req.getContextPath() + "/food?method=list");

    }

    //添加准备
    private void toSaveFood(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.查询菜系列表 用于下拉列表显示
        List<FoodType> foodtypes = foodTypeService.findList();
        req.setAttribute("foodtypes", foodtypes);
        //2.跳转页面
        req.getRequestDispatcher("/sys/food/saveFood.jsp").forward(req, resp);

    }


    //添加
    private void add(HttpServletRequest req, HttpServletResponse resp) {

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);//文件上传工厂
            upload.setFileSizeMax(10*1024*1024);//单个文件大小限制
            upload.setSizeMax(50*1024*1024); //总文件大小限制
            upload.setHeaderEncoding("UTF-8"); //对中文文件编码处理

            //判断是文件的上传类型

            if(upload.isMultipartContent(req)){
                Food food = new Food();

                //将当前request中的内容进行解析
                List<FileItem> list = upload.parseRequest(req);

                for(FileItem fileItem:list){
                    //判断当前fileItem是否是普通表单字段
                    if(fileItem.isFormField()){
                        String fieldName = fileItem.getFieldName();
                        String value = fileItem.getString();
                        value=new String(value.getBytes("iso8859-1"),"UTF-8");
                        System.out.println(fieldName+"---------"+value);
                        BeanUtils.setProperty(food,fieldName,value);
                    }else {
                        //文件上传
                        String fieldName = fileItem.getFieldName();
                        String name = fileItem.getName();

                        System.out.println(name);

                        //获取当前部署服务器的路径
                        String realPath = req.getServletContext().getRealPath("/upload");
                        System.out.println(realPath);

                        File file = new File(realPath);
                        if(! file.exists()){
                            file.mkdir();
                        }

                        File file2 = new File(file ,name);
                        if(!file2.isDirectory()){
                            fileItem.write(file2);
                        }
                        BeanUtils.setProperty(food,fieldName,"upload/"+name);
                        fileItem.delete();
                    }
                }
                //保存到数据库
                foodService.toAdd(food);
                list(req,resp);
            }else {

            }
        } catch (Exception e) {
        }

    }

    //更新准备
    private void findById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FoodType type = new FoodType();
        int id = Integer.parseInt(req.getParameter("id"));
        //用户于下拉列表
        List<FoodType> foodtypes = foodTypeService.findList();
        req.setAttribute("foodtypes", foodtypes);

        food = foodService.findById(id);
        type = foodService.findByFood(food.getFoodType_id());
        req.setAttribute("type",type);
        req.setAttribute("food",food);
        req.getRequestDispatcher("/sys/food/updateFood.jsp").forward(req,resp);
    }


    //更新
    private void update(HttpServletRequest req, HttpServletResponse resp) {

        Boolean b=true;
        try {
            FileItemFactory factory = new DiskFileItemFactory();//获取每一个传入值
            ServletFileUpload upload = new ServletFileUpload(factory);//文件上传工厂
            upload.setFileSizeMax(10*1024*1024);//单个文件大小限制
            upload.setSizeMax(50*1024*1024); //总文件大小限制
            upload.setHeaderEncoding("UTF-8"); //对中文文件编码处理
              
            //判断是文件的上传类型
            if(upload.isMultipartContent(req)){
                Food food = new Food();

                //将当前request中的内容进行解析
                List<FileItem> list = upload.parseRequest(req);

                for(FileItem fileItem:list){
                    //判断当前fileItem是否是普通表单字段
                    if(fileItem.isFormField()){
                        String fieldName = fileItem.getFieldName();
                        String value = fileItem.getString();
                        value=new String(value.getBytes("iso8859-1"),"UTF-8");
                        BeanUtils.setProperty(food,fieldName,value);
                    }else {
                        //文件上传
                        String fieldName = fileItem.getFieldName();
                        String name = fileItem.getName();
                        if (name==null || name.equals("")){
                            b=false;
                        }
                        //获取当前部署服务器的路径
                        String realPath = req.getServletContext().getRealPath("/upload");
                        System.out.println(realPath);

                        File file = new File(realPath);
                        if(! file.exists()){
                            file.mkdir();
                        }

                        File file2 = new File(file ,name);
                        if(!file2.isDirectory()){
                            fileItem.write(file2);
                        }
                        BeanUtils.setProperty(food,fieldName,"upload/"+name);
                        fileItem.delete();
                    }
                }
                //保存到数据库
                if (b) {
                    foodService.toUp(food);
                }else {
                    foodService.upNoImg(food);
                }
                list(req,resp);
            }else {

            }
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    //模糊查询
    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword = req.getParameter("keyword");
        List<Food> list = foodService.findName(keyword);
        req.setAttribute("list",list);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/sys/food/foodList.jsp").forward(req, resp);
    }

    //分页查询列表
    private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        Integer pageSize=5;
        Integer count=foodService.count();
        PageUtils pageUtils=new PageUtils(page,pageSize,count);
        List<Food> list = foodService.findList((pageUtils.getCurrentPage() - 1) * pageSize, pageSize);
        List<FoodType> types = foodTypeService.findList();

        req.setAttribute("list",list);
        req.setAttribute("types",types);
        req.setAttribute("pageUtils",pageUtils);
        req.getRequestDispatcher("/sys/food/foodList.jsp").forward(req, resp);

    }
}
