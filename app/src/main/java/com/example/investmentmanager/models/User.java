package com.example.investmentmanager.models;

public class User
{
    private String fullName;
    private String cellphone;
    private String email;
    private String cpf;
    private String password;
    private String birthDate;

    public User(String fullName, String cellphone, String email, String cpf, String password, String birthDate)
    {
        this.fullName = fullName;
        this.cellphone = cellphone;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.birthDate = birthDate;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setCellphone(String cellphone){
        this.cellphone = cellphone;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getCellphone(){
        return this.cellphone;
    }

    public String getEmail(){
        return this.email;
    }

    public String getCpf(){
        return this.cpf;
    }

    public String getPassword(){
        return this.password;
    }

    public String getBirthDate(){
        return this.birthDate;
    }


}
