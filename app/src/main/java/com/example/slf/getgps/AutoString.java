package com.example.slf.getgps;


public class AutoString {

    private String result="";

    public AutoString(String name, String value) {
        result=result+name+"="+value;
    }

    public void addToResult(String name,String value){
        result=result+"&"+name+"="+value;
    }

    public String getResult() {
        return result;
    }
}
