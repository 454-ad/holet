package com.baidu.text;

import com.baidu.pojo.Food;

import java.util.LinkedHashMap;

public class MapText {
    static Food food = new Food();

    public static void main(String[] args) {

        System.out.println(Math.round(Math.random() * 10000));
//        LinkedHashMap<Food, Integer> map = new LinkedHashMap<>();
//
//
//        food.setFoodName("123");
//        map.put(food,1);
//
//
//        Food food1 = new Food();
//        food1.setFoodName("123");
////        map.put(food,1);
//
//
////        if (map.containsKey(food)){
////            System.out.println("111111");
////        }
//
//        for(Food food2 : map.keySet()){
//            if (food2.getFoodName().equals(food1.getFoodName())){
//                Integer num = map.get(food2);
//                num++;
//                map.put(food2,num);
//            }
//
//        }
//
//        for(Food food2 : map.keySet()){
//            System.out.println(food2.getFoodName());
//            System.out.println(map.get(food2));
//        }
    }
}
