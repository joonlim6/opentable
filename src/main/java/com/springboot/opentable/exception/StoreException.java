package com.springboot.opentable.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public StoreException(ErrorCode errorcode) {
        this.errorCode = errorcode;
        this.errorMessage = errorcode.getDescription();
    }
}

