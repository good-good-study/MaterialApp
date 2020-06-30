package com.sxt.mvvm.model;

import com.sxt.mvvm.model.location.LocationInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RespLocation {
    private final int code;
    private String error;
    private String requestNo;
    private String userName;
    private int version;
    private String imgUrl;
    private List<LocationInfo> locationInfoList;

    public RespLocation(int code) {
        this.code = code;
        this.requestNo = "";
    }

    public RespLocation(int code, @NotNull String requestNo) {
        this.code = code;
        this.requestNo = requestNo;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setLocationInfoList(List<LocationInfo> locationInfoList) {
        this.locationInfoList = locationInfoList;
    }

    public List<LocationInfo> getLocationInfoList() {
        return locationInfoList;
    }
}