package com.example.slf.getgps;

/**
 * Created by slf on 2016/10/5.
 */

public class Mac {
    String name,address;
    int level;

    public Mac(String name, String address,int level) {
        this.name = name;
        this.address = address;
        this.level=level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
