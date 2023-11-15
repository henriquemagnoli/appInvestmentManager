package com.example.investmentmanager.models;

import java.util.Date;

public class Stocks {

    private int id;
    private String stockType;
    private String stockCode;
    private String amount;
    private String price;
    private String otherCosts;
    private String userID;

    // Constructor
    public Stocks(int id, String stockType, String stockCode, String amount, String price, String otherCosts, String userID) {
        this.id = id;
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.amount = amount;
        this.price = price;
        this.otherCosts = otherCosts;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockType() {return stockType;}

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOtherCosts() {
        return otherCosts;
    }

    public void setOtherCosts(String otherCosts) {
        this.otherCosts = otherCosts;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}