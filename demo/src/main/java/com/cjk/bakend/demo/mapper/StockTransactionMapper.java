package com.cjk.bakend.demo.mapper;

import com.cjk.bakend.demo.pojo.StockTransaction;

public interface StockTransactionMapper {
    int deleteByPrimaryKey(Long stockTransactionId);

    int insert(StockTransaction record);

    int insertSelective(StockTransaction record);

    StockTransaction selectByPrimaryKey(Long stockTransactionId);

    int updateByPrimaryKeySelective(StockTransaction record);

    int updateByPrimaryKey(StockTransaction record);
}