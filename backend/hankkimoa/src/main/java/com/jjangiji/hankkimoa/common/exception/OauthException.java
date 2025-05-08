package com.jjangiji.hankkimoa.common.exception;

public class OauthException extends RuntimeException {

    private OauthExceptionResponse response;

    public OauthException(OauthExceptionResponse response) {
        this.response = response;
    }

    public OauthExceptionResponse getResponse() {
        return response;
    }
}
