package com.baidu.service.impl;

import com.baidu.dao.FoodTypeDao;
import com.baidu.dao.impl.FoodTypeDaoImpl;
import com.baidu.pojo.FoodType;
import com.baidu.service.FoodTypeService;

import java.util.List;

public class FoodTypeServiceImpl implements FoodTypeService {
    private FoodTypeDao foodType=new FoodTypeDaoImpl();
    @Override
    public List<FoodType> findList() {
        return foodType.findList();
    }

    @Override
    public List<FoodType> findName(String name) {
        return foodType.findName(name);
    }

    @Override
    public FoodType findById(Integer id) {
        return foodType.findById(id);
    }

    @Override
    public void toUp(FoodType foodType) {
        this.foodType.toUp(foodType);
    }

    @Override
    public void toAdd(FoodType foodType) {
        this.foodType.toAdd(foodType);
    }

    @Override
    public Boolean toDel(Integer id) {
        return this.foodType.toDel(id);
    }
}
