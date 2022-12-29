package com.example.smartcityc.Bean;

import java.util.List;

public class CityDataBean {

    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String city_name;
        private String city_sort;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_sort() {
            return city_sort;
        }

        public void setCity_sort(String city_sort) {
            this.city_sort = city_sort;
        }
    }
}
