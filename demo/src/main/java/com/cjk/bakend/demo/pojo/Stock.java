package com.cjk.bakend.demo.pojo;

import java.io.Serializable;

public class Stock implements Serializable {
    private Long stockId;

    private String stockCode;

    private static final long serialVersionUID = 1L;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }
}