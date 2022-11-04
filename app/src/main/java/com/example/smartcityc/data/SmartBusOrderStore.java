package com.example.smartcityc.data;

import java.util.HashMap;
import java.util.Map;

public class SmartBusOrderStore {
    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    Map<String, String> map = new HashMap<>();

}
