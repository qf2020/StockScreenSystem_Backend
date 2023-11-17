package com.cjk.bakend.demo.mapper;

import com.cjk.bakend.demo.pojo.FilterStock;

public interface FilterStockMapper {
    int deleteByPrimaryKey(Long filterStockId);

    int insert(FilterStock record);

    int insertSelective(FilterStock record);

    FilterStock selectByPrimaryKey(Long filterStockId);

    int updateByPrimaryKeySelective(FilterStock record);

    int updateByPrimaryKey(FilterStock record);
}