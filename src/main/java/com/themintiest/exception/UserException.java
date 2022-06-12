package com.themintiest.exception;

import javax.ws.rs.core.Response;
import java.util.HashMap;

public class UserException extends ServiceException {
    private static final String USER_NOT_FOUND = "001";
    private static final String EMAIL_HAS_BEEN_IN_USED = "002";

    private UserException(String ordinal) {
        super(new HashMap<>(), ServiceErrorCode.USER, ordinal, Response.Status.BAD_REQUEST);
    }

    public static UserException userNotFound() {
        return new UserException(USER_NOT_FOUND);
    }

    public static UserException emailHasBeenInUsed() {
        return new UserException(EMAIL_HAS_BEEN_IN_USED);
    }
}
