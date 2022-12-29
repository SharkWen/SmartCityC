package com.example.smartcityc.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YouthBean {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String msg;
   private int code;

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    @SerializedName("data")
   private List<YouthBean.Data> dataList;
   public class Data {
       public int getId() {
           return id;
       }

       public void setId(int id) {
           this.id = id;
       }

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getImgUrl() {
           return imgUrl;
       }

       public void setImgUrl(String imgUrl) {
           this.imgUrl = imgUrl;
       }

       int id;
       String name;
       String imgUrl;
   }
}
