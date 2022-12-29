package com.example.smartcityc.Bean;

import java.util.List;

public class ViolationListBean {

    private Integer code;
    private String msg;
    private List<RowsDTO> rows;
    private Integer total;

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

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class RowsDTO {
        private Integer id;
        private String licencePlate;
        private String disposeState;
        private String badTime;
        private String money;
        private String deductMarks;
        private String illegalSites;
        private String noticeNo;
        private String engineNumber;
        private String trafficOffence;
        private String catType;
        private String performOffice;
        private String performDate;
        private String imgUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLicencePlate() {
            return licencePlate;
        }

        public void setLicencePlate(String licencePlate) {
            this.licencePlate = licencePlate;
        }

        public String getDisposeState() {
            return disposeState;
        }

        public void setDisposeState(String disposeState) {
            this.disposeState = disposeState;
        }

        public String getBadTime() {
            return badTime;
        }

        public void setBadTime(String badTime) {
            this.badTime = badTime;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getDeductMarks() {
            return deductMarks;
        }

        public void setDeductMarks(String deductMarks) {
            this.deductMarks = deductMarks;
        }

        public String getIllegalSites() {
            return illegalSites;
        }

        public void setIllegalSites(String illegalSites) {
            this.illegalSites = illegalSites;
        }

        public String getNoticeNo() {
            return noticeNo;
        }

        public void setNoticeNo(String noticeNo) {
            this.noticeNo = noticeNo;
        }

        public String getEngineNumber() {
            return engineNumber;
        }

        public void setEngineNumber(String engineNumber) {
            this.engineNumber = engineNumber;
        }

        public String getTrafficOffence() {
            return trafficOffence;
        }

        public void setTrafficOffence(String trafficOffence) {
            this.trafficOffence = trafficOffence;
        }

        public String getCatType() {
            return catType;
        }

        public void setCatType(String catType) {
            this.catType = catType;
        }

        public String getPerformOffice() {
            return performOffice;
        }

        public void setPerformOffice(String performOffice) {
            this.performOffice = performOffice;
        }

        public String getPerformDate() {
            return performDate;
        }

        public void setPerformDate(String performDate) {
            this.performDate = performDate;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
