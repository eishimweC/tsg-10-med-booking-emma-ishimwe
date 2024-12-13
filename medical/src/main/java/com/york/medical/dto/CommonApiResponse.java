package com.york.medical.dto;

import lombok.Builder;

@Builder
public class CommonApiResponse {
    private String responseMessage;

    private boolean isSuccess;

    public CommonApiResponse() {
    }

    public CommonApiResponse(String responseMessage, boolean isSuccess) {
        this.responseMessage = responseMessage;
        this.isSuccess = isSuccess;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public boolean isSuccess() {
        return isSuccess;
    }
    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}

