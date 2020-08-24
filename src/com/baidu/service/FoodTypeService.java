package com.baidu.service;

import com.baidu.pojo.FoodType;

import java.util.List;

public interface FoodTypeService {
    //    显示
    List<FoodType> findList();
    //    查询
    List<FoodType> findName(String name);
    FoodType findById(Integer id);
    //    更新
    void toUp(FoodType foodType);
    //    添加
    void toAdd(FoodType foodType);
    //    删除
    Boolean toDel(Integer id);

}
