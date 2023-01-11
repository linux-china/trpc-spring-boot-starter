package org.mvnsearch.spring.boot.trpc;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrpcResponse<T> {
    private Result<T> result;
    private Error error;

    public TrpcResponse() {
    }

    public TrpcResponse(Result<T> result) {
        this.result = result;
    }

    public TrpcResponse(Error error) {
        this.error = error;
    }

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static <T> TrpcResponse<T> of(T data) {
        return new TrpcResponse<>(new Result<T>(data));
    }

    public static TrpcResponse<?> error(String code, String message, Object data) {
        return new TrpcResponse<>(new Error(code, message, data));
    }

    public static class Result<T> {
        private T data;

        public Result() {
        }

        public Result(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

    }

    public static class Error {
        private String code;
        private String message;
        private Object data;

        public Error() {
        }

        public Error(String code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
