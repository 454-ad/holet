package com.baidu.dao;

import com.baidu.pojo.DinnerTable;

import java.util.List;

public interface DinnerTableDao {
//    显示
    List<DinnerTable> findList();
//    查询
    List<DinnerTable> findName(String name);

//    删除
    Boolean toDel(Integer id);
//    添加
    void toAdd(DinnerTable dinnerTable);
//    预定
    Boolean toUp(DinnerTable dinnerTable);
    DinnerTable findById(Integer id);
}
