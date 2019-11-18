package com.wangdao.mall.exception;

public class NoSuchGoodsException extends Exception {
    public NoSuchGoodsException() {
    }

    public NoSuchGoodsException(String message) {
        super(message);
    }
}
