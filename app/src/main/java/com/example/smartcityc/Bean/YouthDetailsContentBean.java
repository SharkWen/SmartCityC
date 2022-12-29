package com.example.smartcityc.Bean;

import java.util.List;

public class YouthDetailsContentBean {

    private String msg;
    private Integer code;
    private List<YouthDetailsContentBean.DataDTO> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<YouthDetailsContentBean.DataDTO> getData() {
        return data;
    }

    public void setData(List<YouthDetailsContentBean.DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        private String createTime;
        private Integer id;
        private Integer areaId;
        private String title;
        private String author;
        private String content;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
