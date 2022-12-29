package com.example.smartcityc.Bean;

import java.util.List;

public class YouthListBean {

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
        private Object searchValue;
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private ParamsDTO params;
        private Integer id;
        private String name;
        private String coverImgUrl;
        private String imageUrls;
        private Integer bedsCountBoy;
        private Integer bedsCountGirl;
        private String address;
        private String phone;
        private String workTime;
        private String introduce;
        private String internalFacilities;
        private String surroundingFacilities;
        private String specialService;
        private Object imageUrlList;

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

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public String getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(String imageUrls) {
            this.imageUrls = imageUrls;
        }

        public Integer getBedsCountBoy() {
            return bedsCountBoy;
        }

        public void setBedsCountBoy(Integer bedsCountBoy) {
            this.bedsCountBoy = bedsCountBoy;
        }

        public Integer getBedsCountGirl() {
            return bedsCountGirl;
        }

        public void setBedsCountGirl(Integer bedsCountGirl) {
            this.bedsCountGirl = bedsCountGirl;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getInternalFacilities() {
            return internalFacilities;
        }

        public void setInternalFacilities(String internalFacilities) {
            this.internalFacilities = internalFacilities;
        }

        public String getSurroundingFacilities() {
            return surroundingFacilities;
        }

        public void setSurroundingFacilities(String surroundingFacilities) {
            this.surroundingFacilities = surroundingFacilities;
        }

        public String getSpecialService() {
            return specialService;
        }

        public void setSpecialService(String specialService) {
            this.specialService = specialService;
        }

        public Object getImageUrlList() {
            return imageUrlList;
        }

        public void setImageUrlList(Object imageUrlList) {
            this.imageUrlList = imageUrlList;
        }

        public static class ParamsDTO {
        }
    }
}
