package com.themintiest.exception;

import javax.ws.rs.core.Response;
import java.util.HashMap;

public class RoleException extends ServiceException {
    private static final String ROLE_NOT_FOUND = "001";

    private RoleException(String ordinal) {
        super(new HashMap<>(), ServiceErrorCode.ROLE, ordinal, Response.Status.BAD_REQUEST);
    }

    public static RoleException roleNotFound() {
        return new RoleException(ROLE_NOT_FOUND);
    }
}
