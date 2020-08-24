package com.baidu.dao.impl;

import com.baidu.dao.FoodDao;
import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;
import com.baidu.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class FoodDaoImpl implements FoodDao {
    @Override
    //查询food表所有
    public List<Food> findAll() {
        String sql="select * from food";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Food> list=null;
        try {
            list=runner.query(sql,new BeanListHandler<>(Food.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    //分页查询
    public List<Food> findList(Integer i, Integer j) {
        String sql="select * from food LIMIT ?,? ";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Food> list=null;
        try {
            list=runner.query(sql,new BeanListHandler<>(Food.class),i,j);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    //查询food表总数
    public Integer count() {
        String sql="select count(*) from food";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        Integer count=null;
        try {
            count = ((Long)runner.query(sql, new ScalarHandler<>())).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    //模糊查询字段
    public List<Food> findName(String name) {
        String sql="select * from food where foodName like ?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Food> list=null;
        try {
            list = runner.query(sql, new BeanListHandler<>(Food.class), "%" + name + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    //根据id查询food
    public Food findById(Integer id) {
        String sql="select * from food where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        Food food=new Food();
        try {
            food= runner.query(sql, new BeanHandler<>(Food.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return food;
    }

    @Override
    //查询菜品所属菜系
    public FoodType findByFood(Integer id) {
        String sql="SELECT a.id,a.typeName FROM foodtype a JOIN food b ON a.id=b.foodType_id WHERE b.foodType_id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        FoodType foodType = new FoodType();
        try {
            foodType = runner.query(sql,new BeanHandler<>(FoodType.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodType;
    }

    @Override
    //根据菜系ID查询菜品
    public List<Food> findTypeById(Integer foodType_id, Integer i, Integer j) {
        String sql="select * from food where foodType_id=? LIMIT ?,? ";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        List<Food> list=null;
        try {
            list=runner.query(sql,new BeanListHandler<>(Food.class),foodType_id,i,j);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    //每个菜系菜品数量
    public Integer countType(Integer foodType_id) {
        String sql="select count(*) from food where foodType_id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        Integer count=null;
        try {
            count = ((Long)runner.query(sql, new ScalarHandler<>(),foodType_id)).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    //添加数据
    public void toAdd(Food food) {
        String sql =" INSERT food(foodName,foodType_id,price,mprice,remark,img) VALUES(?,?,?,?,?,?)";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql, food.getFoodName(),food.getFoodType_id(),
                    food.getPrice(),food.getMprice(),food.getRemark(),food.getImg());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    //修改有图
    public void toUp(Food food) {
        String sql ="update food set foodName=?,foodType_id=?,price=?,mprice=?,remark=?,img=? where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),food.getRemark(),food.getImg(),food.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //修改无图
    public void upNoImg(Food food) {
        String sql ="update food set foodName=?,foodType_id=?,price=?,mprice=?,remark=? where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            runner.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),food.getRemark(),food.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //删除
    public Boolean toDel(Integer id) {
        String sql="delete from food where id=?";
        QueryRunner runner = JDBCUtils.getQueryRunner();
        try {
            return 1== runner.update(sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
