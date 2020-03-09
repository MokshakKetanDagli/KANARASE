package com.example.afinal;

public class Users {
    String FULL_NAME,DOB,EMAIL_ID;

    public Users() {
    }

    public Users(String FULL_NAME, String DOB, String EMAIL_ID) {
        this.FULL_NAME = FULL_NAME;
        this.DOB = DOB;
        this.EMAIL_ID = EMAIL_ID;
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

    public String getEMAIL_ID() {
        return EMAIL_ID;
    }

    public void setEMAIL_ID(String EMAIL_ID) {
        this.EMAIL_ID = EMAIL_ID;
    }
}
