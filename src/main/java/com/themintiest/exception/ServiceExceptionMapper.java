package com.themintiest.exception;

import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import org.apache.commons.text.StringSubstitutor;
import com.themintiest.security.SecurityIdentityHolder;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<ServiceException> {

    @Override
    public Response toResponse(ServiceException e) {
        ResourceBundle resourceBundle;
        if (SecurityIdentityHolder.getIdentity() instanceof QuarkusSecurityIdentity) {
            QuarkusSecurityIdentity securityIdentity = (QuarkusSecurityIdentity) SecurityIdentityHolder.getIdentity();
            resourceBundle = ResourceBundle.getBundle("i18n.exception", (Locale) securityIdentity.getAttribute("locale"));
        } else {
            resourceBundle = ResourceBundle.getBundle("i18n.exception", new Locale("vn"));
        }
        return this.buildResponse(resourceBundle, e.getErrorCode(), e.getOrdinal(), e.getData(), e.getStatus());
    }

    private Response buildResponse(ResourceBundle resourceBundle, ServiceErrorCode error,
                                   String ordinal, Map<String, Object> data, Response.Status status) {
        Response.ResponseBuilder response = Response.status(status);
        String errorCode = "E" + error.getCode() + ordinal;
        String message = resourceBundle.getString(errorCode);
        StringSubstitutor stringSubstitutor = new StringSubstitutor(data);
        response.entity(ExceptionResponse.builder()
                .code(errorCode)
                .message(stringSubstitutor.replace(message))
                .data(data)
                .build());
        return response.build();
    }
}
