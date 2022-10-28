package com.example.smartcityc.Bean;

import java.util.List;

public class OrderSureBean {

    private String addressDetail;
    private String label;
    private String name;
    private String phone;
    private Float amount;
    private List<OrderItemListDTO> orderItemList;
    private Integer sellerId;

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public List<OrderItemListDTO> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemListDTO> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public static class OrderItemListDTO {
        private Integer productId;
        private Integer quantity;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
