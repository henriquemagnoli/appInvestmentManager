package com.example.investmentmanager.models;

import java.util.Date;

public class Historic
{
    private String type;
    private String stockCode;
    private String boughtDate;
    private String soldDate;
    private String amount;
    private String price;
    private String otherCosts;
    private String userID;

    public Historic(String type, String stockCode, String boughtDate, String soldDate, String amount, String price, String otherCosts, String userID)
    {
        this.type = type;
        this.stockCode = stockCode;
        this.boughtDate = boughtDate;
        this.soldDate = soldDate;
        this.amount = amount;
        this.price = price;
        this.otherCosts = otherCosts;
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(String soldDate) {
        this.soldDate = soldDate;
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
