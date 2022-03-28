package com.min.project_map.data.vo;

public class AqiVo {
    private String status;
    private String message;
    private AqiDataVo data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AqiDataVo getData() {
        return data;
    }

    public void setData(AqiDataVo data) {
        this.data = data;
    }
}
