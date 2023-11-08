package com.example.investmentmanager.models;

import java.util.Date;

public class Historic
{
    private char type;
    private String stockCode;
    private String boughtDate;
    private String soldDate;
    private int amount;
    private double price;
    private double otherCosts;
    private int userID;

    public Historic(char type, String stockCode, String boughtDate, String soldDate, int amount, double price, double otherCosts, int userID)
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

    public void setType(char type) {this.type = type;}
    public void setStockCode(String stockCode){
        this.stockCode = stockCode;
    }
    public void setBoughtDate(String boughtDate) { this.boughtDate = boughtDate;}
    public  void setSoldDate(String soldDate) {this.soldDate = soldDate;}
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

    public char getType() { return this.type;}
    public String getStockCode(){
        return this.stockCode;
    }
    public String getBoughtDate() {return  this.boughtDate;}
    public String getSoldDate() {return this.soldDate;}
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
