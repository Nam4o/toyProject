package com.nam4o.myweb.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponseBody<T> {
    @Getter
    @Setter
    public static class DataHeader {
        // 0 : success, 1: error
        private int successCode;

        private String resultCode = "";

        private Object resultMessage = "";

    }

    private DataHeader dataHeader = new DataHeader();
    private T dataBody;

    public static <T> BaseResponseBody<T> of(int successCode, T dataBody) {
        BaseResponseBody<T> body = new BaseResponseBody<>();
        body.getDataHeader().setSuccessCode(successCode);
        body.setDataBody(dataBody);

        return body;
    }

    public static BaseResponseBody<Object> error(String resultCode, Object resultMessage) {
        BaseResponseBody<Object> body = new BaseResponseBody<>();
        body.getDataHeader().setSuccessCode(1);
        body.getDataHeader().setResultCode(resultCode);
        body.getDataHeader().setResultMessage(resultMessage);

        return body;
    }
}
