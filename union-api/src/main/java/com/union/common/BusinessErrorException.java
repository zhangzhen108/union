package com.union.common;

import lombok.Data;

@Data
public class BusinessErrorException extends RuntimeException{

    private final String errorCode;

    private final String errorMessage;

    public BusinessErrorException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public BusinessErrorException(ErrorEnum errorEnum) {
        this(errorEnum.getCode(), errorEnum.getMsg());
    }

}
