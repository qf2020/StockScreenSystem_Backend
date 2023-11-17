package com.cjk.bakend.demo.mapper;

import com.cjk.bakend.demo.pojo.Stock;

public interface StockMapper {
    int deleteByPrimaryKey(Long stockId);

    int insert(Stock record);

    int insertSelective(Stock record);

    Stock selectByPrimaryKey(Long stockId);

    int updateByPrimaryKeySelective(Stock record);

    int updateByPrimaryKey(Stock record);
}