package com.themintiest.exception;

import javax.ws.rs.core.Response;
import java.util.HashMap;

public class AuthException extends ServiceException {
    private static final String MATCHING_PROFILE_NOT_FOUND = "001";

    private AuthException(String ordinal) {
        super(new HashMap<>(), ServiceErrorCode.AUTHENTICATION, ordinal, Response.Status.BAD_REQUEST);
    }

    public static AuthException matchingProfileNotFound() {
        return new AuthException(MATCHING_PROFILE_NOT_FOUND);
    }
}

