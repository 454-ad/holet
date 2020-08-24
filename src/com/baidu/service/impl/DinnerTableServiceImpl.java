package com.baidu.service.impl;

import com.baidu.dao.DinnerTableDao;
import com.baidu.dao.impl.DinnerTableDaoImpl;
import com.baidu.pojo.DinnerTable;
import com.baidu.service.DinnerTableService;

import java.util.List;

public class DinnerTableServiceImpl implements DinnerTableService {
    private DinnerTableDao dinnerTableDao=new DinnerTableDaoImpl();
    @Override
    public List<DinnerTable> findList() {
        return this.dinnerTableDao.findList();
    }

    @Override
    public List<DinnerTable> findName(String name) {
        return this.dinnerTableDao.findName(name);
    }

    @Override
    public DinnerTable findById(Integer id) {
        return this.dinnerTableDao.findById(id);
    }

    @Override
    public Boolean toDel(Integer id) {
        return this.dinnerTableDao.toDel(id);
    }

    @Override
    public void toAdd(DinnerTable dinnerTable) {
        this.dinnerTableDao.toAdd(dinnerTable);
    }

    @Override
    public Boolean toUp(DinnerTable dinnerTable) {
        return this.dinnerTableDao.toUp(dinnerTable);
    }
}
