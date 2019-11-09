package com.ciel.springcloudasso.interceptor.restapi;

import lombok.Data;

@Data
public final class CommonResult<T> {

    private int status = 1;

    private String errorCode = "";

    private String errorMsg = "";

    private T resultBody;

    public CommonResult() {
    }

    public CommonResult(T resultBody) {
        this.resultBody = resultBody;
    }

    public static <T> CommonResult valueOf(T body) {
        return new CommonResult(body);
    }



}