package com.example.investmentmanager.models;

import java.util.Date;

public class Stocks
{
    private String stockType;
    private String stockCode;
    private int amount;
    private double price;
    private double otherCosts;
    private int userID;

    // Constructor
    public Stocks(String stockType, String stockCode, int amount, double price, double otherCosts, int userID)
    {
        this.stockType = stockType;
        this.stockCode = stockCode;
        this.amount = amount;
        this.price = price;
        this.otherCosts = otherCosts;
        this.userID = userID;
    }

    // SETS
    public void setStockType(String stockType){
        this.stockType = stockType;
    }

    public void setStockCode(String stockCode){
        this.stockCode = stockCode;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public void setOtherCosts(double otherCosts){
        this.otherCosts = otherCosts;
    }

    public  void setUserID (int userID){
        this.userID = userID;
    }

    // GETS
    public String getStockType(){
        return this.stockType;
    }

    public String getStockCode(){
        return this.stockCode;
    }

    public int getAmount(){
        return this.amount;
    }

    public double getPrice(){
        return this.price;
    }

    public double getOtherCosts(){
        return this.otherCosts;
    }

    public int getUserID(){
        return this.userID;
    }
}
