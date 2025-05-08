package com.jjangiji.hankkimoa.common.handler;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jjangiji.hankkimoa.common.exception.ExceptionCode;
import com.jjangiji.hankkimoa.common.exception.HankkiMoaException;
import com.jjangiji.hankkimoa.common.exception.OauthException;
import com.jjangiji.hankkimoa.common.exception.OauthExceptionResponse;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

@Component
public class OauthClientExceptionHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) {
        try {
            return response.getStatusCode().is4xxClientError();
        } catch (IOException exception) {
            throw new HankkiMoaException(ExceptionCode.OAUTH_TOKEN_INTERNAL_EXCEPTION);
        }
    }

    @Override
    public void handleError(ClientHttpResponse response) {
        throw new OauthException(getResponseBody(response));
    }

    private OauthExceptionResponse getResponseBody(ClientHttpResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.getBody(), OauthExceptionResponse.class);
        } catch (IOException exception) {
            throw new HankkiMoaException(ExceptionCode.OAUTH_TOKEN_INTERNAL_EXCEPTION);
        }
    }
}
