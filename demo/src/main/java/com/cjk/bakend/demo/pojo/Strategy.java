package com.cjk.bakend.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class Strategy implements Serializable {
    private Long strategyId;

    private Long filterStockId;

    private Integer userId;

    private Date strategyDate;

    private Double openPrice;

    private Double closePrice;

    private Double highPrice;

    private Double lowPrice;

    private static final long serialVersionUID = 1L;

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Long getFilterStockId() {
        return filterStockId;
    }

    public void setFilterStockId(Long filterStockId) {
        this.filterStockId = filterStockId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStrategyDate() {
        return strategyDate;
    }

    public void setStrategyDate(Date strategyDate) {
        this.strategyDate = strategyDate;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }
}