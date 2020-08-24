package com.baidu.dao;

import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;

import java.util.List;

public interface FoodDao {
//    显示
    List<Food> findAll();   //全部
    List<Food> findList(Integer i,Integer j);  //分页
    Integer count();  //数量
//    查询
    List<Food> findName(String name);
    Food findById(Integer id);
    FoodType findByFood(Integer id);  //查询修改菜名的所属菜系
    List<Food> findTypeById(Integer foodType_id,Integer i,Integer j);//根据菜系ID查询菜品
    Integer countType(Integer foodType_id);     //每个菜系菜品数量
//    添加
    void toAdd(Food food);
//    修改
    void toUp(Food food);
    void upNoImg(Food food);
//    删除
    Boolean toDel(Integer id);
}
