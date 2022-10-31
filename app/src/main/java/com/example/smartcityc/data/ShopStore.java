package com.example.smartcityc.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopStore {
    public Map<Integer, ShopDetails> getMap() {
        return map;
    }

    public void setMap(Map<Integer, ShopDetails> map) {
        this.map = map;
    }

    public List<String> getSellerIds() {
        return sellerIds;
    }

    public void setSellerIds(List<String> sellerIds) {
        this.sellerIds = sellerIds;
    }

    public  Map<Integer, ShopDetails> map = new HashMap<>();
    public  List<String> sellerIds = new ArrayList<>();

    public static class ShopDetails {
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getMouthSells() {
            return mouthSells;
        }

        public void setMouthSells(String mouthSells) {
            this.mouthSells = mouthSells;
        }

        private String url;
        private String name;
        private String score;
        private String mouthSells;

    }
}
