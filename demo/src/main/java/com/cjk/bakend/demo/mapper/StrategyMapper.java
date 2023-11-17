package com.cjk.bakend.demo.mapper;

import com.cjk.bakend.demo.pojo.Strategy;

public interface StrategyMapper {
    int deleteByPrimaryKey(Long strategyId);

    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy selectByPrimaryKey(Long strategyId);

    int updateByPrimaryKeySelective(Strategy record);

    int updateByPrimaryKey(Strategy record);
}