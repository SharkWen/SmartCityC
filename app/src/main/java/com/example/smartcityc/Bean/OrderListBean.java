package com.example.smartcityc.Bean;

import java.util.List;

public class OrderListBean {

    private Integer total;
    private List<RowsDTO> rows;
    private Integer code;
    private String msg;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class RowsDTO {
        private SellerInfoDTO sellerInfo;
        private OrderInfoDTO orderInfo;

        public SellerInfoDTO getSellerInfo() {
            return sellerInfo;
        }

        public void setSellerInfo(SellerInfoDTO sellerInfo) {
            this.sellerInfo = sellerInfo;
        }

        public OrderInfoDTO getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoDTO orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class SellerInfoDTO {
            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private String updateTime;
            private Object remark;
            private ParamsDTO params;
            private Integer id;
            private String name;
            private String address;
            private String introduction;
            private Integer themeId;
            private Double score;
            private Integer saleQuantity;
            private Integer deliveryTime;
            private String imgUrl;
            private Double avgCost;
            private Object other;
            private String recommend;
            private Double distance;
            private Integer saleNum3hour;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public ParamsDTO getParams() {
                return params;
            }

            public void setParams(ParamsDTO params) {
                this.params = params;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
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

            public String getIntroduction() {
                return introduction;
            }

            public void setIntroduction(String introduction) {
                this.introduction = introduction;
            }

            public Integer getThemeId() {
                return themeId;
            }

            public void setThemeId(Integer themeId) {
                this.themeId = themeId;
            }

            public Double getScore() {
                return score;
            }

            public void setScore(Double score) {
                this.score = score;
            }

            public Integer getSaleQuantity() {
                return saleQuantity;
            }

            public void setSaleQuantity(Integer saleQuantity) {
                this.saleQuantity = saleQuantity;
            }

            public Integer getDeliveryTime() {
                return deliveryTime;
            }

            public void setDeliveryTime(Integer deliveryTime) {
                this.deliveryTime = deliveryTime;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public Double getAvgCost() {
                return avgCost;
            }

            public void setAvgCost(Double avgCost) {
                this.avgCost = avgCost;
            }

            public Object getOther() {
                return other;
            }

            public void setOther(Object other) {
                this.other = other;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public Double getDistance() {
                return distance;
            }

            public void setDistance(Double distance) {
                this.distance = distance;
            }

            public Integer getSaleNum3hour() {
                return saleNum3hour;
            }

            public void setSaleNum3hour(Integer saleNum3hour) {
                this.saleNum3hour = saleNum3hour;
            }

            public static class ParamsDTO {
            }
        }

        public static class OrderInfoDTO {
            private Object searchValue;
            private Object createBy;
            private String createTime;
            private Object updateBy;
            private String updateTime;
            private Object remark;
            private ParamsDTO params;
            private Integer id;
            private String orderNo;
            private Integer userId;
            private Integer sellerId;
            private Double amount;
            private Object postage;
            private String status;
            private String paymentType;
            private String payTime;
            private Object sendTime;
            private String receiverName;
            private String receiverPhone;
            private String receiverAddress;
            private String receiverLabel;
            private Object houseNumber;
            private List<OrderItemListDTO> orderItemList;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public ParamsDTO getParams() {
                return params;
            }

            public void setParams(ParamsDTO params) {
                this.params = params;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
            }

            public Integer getUserId() {
                return userId;
            }

            public void setUserId(Integer userId) {
                this.userId = userId;
            }

            public Integer getSellerId() {
                return sellerId;
            }

            public void setSellerId(Integer sellerId) {
                this.sellerId = sellerId;
            }

            public Double getAmount() {
                return amount;
            }

            public void setAmount(Double amount) {
                this.amount = amount;
            }

            public Object getPostage() {
                return postage;
            }

            public void setPostage(Object postage) {
                this.postage = postage;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public String getPayTime() {
                return payTime;
            }

            public void setPayTime(String payTime) {
                this.payTime = payTime;
            }

            public Object getSendTime() {
                return sendTime;
            }

            public void setSendTime(Object sendTime) {
                this.sendTime = sendTime;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }

            public String getReceiverPhone() {
                return receiverPhone;
            }

            public void setReceiverPhone(String receiverPhone) {
                this.receiverPhone = receiverPhone;
            }

            public String getReceiverAddress() {
                return receiverAddress;
            }

            public void setReceiverAddress(String receiverAddress) {
                this.receiverAddress = receiverAddress;
            }

            public String getReceiverLabel() {
                return receiverLabel;
            }

            public void setReceiverLabel(String receiverLabel) {
                this.receiverLabel = receiverLabel;
            }

            public Object getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(Object houseNumber) {
                this.houseNumber = houseNumber;
            }

            public List<OrderItemListDTO> getOrderItemList() {
                return orderItemList;
            }

            public void setOrderItemList(List<OrderItemListDTO> orderItemList) {
                this.orderItemList = orderItemList;
            }

            public static class ParamsDTO {
            }

            public static class OrderItemListDTO {
                private Object searchValue;
                private Object createBy;
                private String createTime;
                private Object updateBy;
                private Object updateTime;
                private Object remark;
                private ParamsDTO params;
                private Integer id;
                private Integer userId;
                private String orderNo;
                private Integer productId;
                private String productName;
                private String productImage;
                private Double productPrice;
                private Integer quantity;
                private Double totalPrice;

                public Object getSearchValue() {
                    return searchValue;
                }

                public void setSearchValue(Object searchValue) {
                    this.searchValue = searchValue;
                }

                public Object getCreateBy() {
                    return createBy;
                }

                public void setCreateBy(Object createBy) {
                    this.createBy = createBy;
                }

                public String getCreateTime() {
                    return createTime;
                }

                public void setCreateTime(String createTime) {
                    this.createTime = createTime;
                }

                public Object getUpdateBy() {
                    return updateBy;
                }

                public void setUpdateBy(Object updateBy) {
                    this.updateBy = updateBy;
                }

                public Object getUpdateTime() {
                    return updateTime;
                }

                public void setUpdateTime(Object updateTime) {
                    this.updateTime = updateTime;
                }

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public ParamsDTO getParams() {
                    return params;
                }

                public void setParams(ParamsDTO params) {
                    this.params = params;
                }

                public Integer getId() {
                    return id;
                }

                public void setId(Integer id) {
                    this.id = id;
                }

                public Integer getUserId() {
                    return userId;
                }

                public void setUserId(Integer userId) {
                    this.userId = userId;
                }

                public String getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(String orderNo) {
                    this.orderNo = orderNo;
                }

                public Integer getProductId() {
                    return productId;
                }

                public void setProductId(Integer productId) {
                    this.productId = productId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public Double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(Double productPrice) {
                    this.productPrice = productPrice;
                }

                public Integer getQuantity() {
                    return quantity;
                }

                public void setQuantity(Integer quantity) {
                    this.quantity = quantity;
                }

                public Double getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(Double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
