package com.example.investmentmanager.models;

import java.util.Date;

public class Historic
{
    private String stockCode;
    private Date boughtDate;
    private Date soldDate;
    private int amount;
    private double price;
    private double otherCosts;
    private int userID;

    public Historic(String stockCode, Date boughtDate, Date soldDate, int amount, double price, double otherCosts, int userID)
    {
        this.stockCode = stockCode;
        this.boughtDate = boughtDate;
        this.soldDate = soldDate;
        this.amount = amount;
        this.price = price;
        this.otherCosts = otherCosts;
        this.userID = userID;
    }

    public void setStockCode(String stockCode){
        this.stockCode = stockCode;
    }
    public void setBoughtDate(Date boughtDate) { this.boughtDate = boughtDate;}
    public  void setSoldDate(Date soldDate) {this.soldDate = soldDate;}
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

    public String getStockCode(){
        return this.stockCode;
    }
    public Date getBoughtDate() {return  this.boughtDate;}
    public Date getSoldDate() {return this.soldDate;}
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
