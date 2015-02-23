package com.richard;

import java.util.Map;

/**
 * User: rnkoaa
 * Created: 2/22/15 10:13 PM
 */
public class RestResponse<T> {

    CTMResponse response;
    T dataResponse;
    Map<Integer, String> errors;

    public RestResponse(CTMResponse response, T dataResponse, Map<Integer, String> errors) {
        this.response = response;
        this.dataResponse = dataResponse;
        this.errors = errors;
    }

    public RestResponse() {
    }

    public CTMResponse getResponse() {
        return response;
    }

    public void setResponse(CTMResponse response) {
        this.response = response;
    }

    public T getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(T dataResponse) {
        this.dataResponse = dataResponse;
    }

    public Map<Integer, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<Integer, String> errors) {
        this.errors = errors;
    }

    public static class CTMResponse {
        boolean success;
        String message;
        int errorCode;
        String errorData;

        public CTMResponse() {

        }

        public CTMResponse(boolean success, String message, int errorCode, String errorData) {
            this.success = success;
            this.message = message;
            this.errorCode = errorCode;
            this.errorData = errorData;
        }

        public boolean isSuccessful() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorData() {
            return errorData;
        }
    }
}
