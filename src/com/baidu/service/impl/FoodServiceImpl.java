package com.baidu.service.impl;

import com.baidu.dao.FoodDao;
import com.baidu.dao.impl.FoodDaoImpl;
import com.baidu.pojo.Food;
import com.baidu.pojo.FoodType;
import com.baidu.service.FoodService;

import java.util.List;

public class FoodServiceImpl implements FoodService {
    private FoodDao food=new FoodDaoImpl();
    @Override
    public List<Food> findAll() {
        return food.findAll();
    }

    @Override
    public List<Food> findList(Integer i, Integer j) {
        return food.findList(i,j);
    }

    @Override
    public Integer count() {
        return food.count();
    }

    @Override
    public List<Food> findName(String name) {
        return food.findName(name);
    }

    @Override
    public Food findById(Integer id) {
        return food.findById(id);
    }

    @Override
    public FoodType findByFood(Integer id) {
        return food.findByFood(id);
    }

    @Override
    public List<Food> findTypeById(Integer foodType_id, Integer i, Integer j) {
        return food.findTypeById(foodType_id, i, j);
    }

    @Override
    public Integer countType(Integer foodType_id) {
        return food.countType(foodType_id);
    }

    @Override
    public void toAdd(Food food) {
        this.food.toAdd(food);
    }

    @Override
    public void toUp(Food food) {
        this.food.toUp(food);
    }

    @Override
    public void upNoImg(Food food) {
        this.food.upNoImg(food);
    }

    @Override
    public Boolean toDel(Integer id) {
        return food.toDel(id);
    }
}
