package com.example.smartcityc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public List<Integer> keyList = new ArrayList<>();
    HashMap<Integer, Food> map = new HashMap<>();

    public HashMap<Integer, Food> getFoodData() {
        return this.map;
    }

    public static class Food{
        int count;
        Double price;
        String url;
        String name;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public Food(int count, Double price,String url,String name) {
            this.count = count;
            this.price = price;
            this.url = url;
            this.name = name;
        }
    }
}
