package com.cjk.bakend.demo.pojo;

import java.io.Serializable;
import java.util.Date;

public class StockTransaction implements Serializable {
    private Long stockTransactionId;

    private Integer userId;

    private Long stockId;

    private Integer trasactionType;

    private Double profit;

    private Date transactionDate;

    private static final long serialVersionUID = 1L;

    public Long getStockTransactionId() {
        return stockTransactionId;
    }

    public void setStockTransactionId(Long stockTransactionId) {
        this.stockTransactionId = stockTransactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public Integer getTrasactionType() {
        return trasactionType;
    }

    public void setTrasactionType(Integer trasactionType) {
        this.trasactionType = trasactionType;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}