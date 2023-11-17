package com.cjk.bakend.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class FilterStock implements Serializable {
    private Long filterStockId;

    private Long stockId;

    private Integer filterStockCategory;

    private Integer filterStockType;

    private Date filterStockStartDate;

    private Date filterStockEndDate;

    private static final long serialVersionUID = 1L;

    public Long getFilterStockId() {
        return filterStockId;
    }

    public void setFilterStockId(Long filterStockId) {
        this.filterStockId = filterStockId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Integer getFilterStockCategory() {
        return filterStockCategory;
    }

    public void setFilterStockCategory(Integer filterStockCategory) {
        this.filterStockCategory = filterStockCategory;
    }

    public Integer getFilterStockType() {
        return filterStockType;
    }

    public void setFilterStockType(Integer filterStockType) {
        this.filterStockType = filterStockType;
    }

    public Date getFilterStockStartDate() {
        return filterStockStartDate;
    }

    public void setFilterStockStartDate(Date filterStockStartDate) {
        this.filterStockStartDate = filterStockStartDate;
    }

    public Date getFilterStockEndDate() {
        return filterStockEndDate;
    }

    public void setFilterStockEndDate(Date filterStockEndDate) {
        this.filterStockEndDate = filterStockEndDate;
    }
}