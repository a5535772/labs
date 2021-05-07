package com.example.demo.bean;


public class Cat {

    private int sex;

    private String name;

    private String drlType;

    public Cat(int sex, String name, String drlType) {
        this.sex = sex;
        this.name = name;
        this.drlType = drlType;
    }

    public Cat() {}

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrlType() {
        return drlType;
    }

    public void setDrlType(String drlType) {
        this.drlType = drlType;
    }


}
