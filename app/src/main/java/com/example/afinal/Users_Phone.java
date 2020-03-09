package com.example.afinal;

public class Users_Phone {
    String FULL_NAME,DOB,PHONE;

    public Users_Phone() {
    }

    public Users_Phone(String FULL_NAME, String DOB, String PHONE) {
        this.FULL_NAME = FULL_NAME;
        this.DOB = DOB;
        this.PHONE = PHONE;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public void setFULL_NAME(String FULL_NAME) {
        this.FULL_NAME = FULL_NAME;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}
