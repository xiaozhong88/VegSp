package com.atinytot.vegsp_v_1.model.repository;

public class Result<T> {

    private T data;
    private Exception error;

    public Result(T data) {
        this.data = data;
        this.error = null;
    }

    public Result(Exception error) {
        this.data = null;
        this.error = error;
    }

    public boolean isSuccess() {
        return error == null;
    }

    public T getData() {
        return data;
    }

    public Exception getError() {
        return error;
    }

    public static class Success<T> extends Result<T> {

        public Success(T data) {
            super(data);
        }
    }

    public static class Failure<T> extends Result<T> {

        public Failure(Exception error) {
            super(error);
        }
    }
}
