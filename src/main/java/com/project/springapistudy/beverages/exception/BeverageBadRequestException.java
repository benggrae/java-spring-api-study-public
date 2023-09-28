package com.project.springapistudy.beverages.exception;

import com.project.springapistudy.beverages.constants.BeverageMessages;

public class BeverageBadRequestException extends BeverageException {

    public BeverageBadRequestException(BeverageMessages messages) {
        super(messages);
    }

    public BeverageBadRequestException(long code, String message) {
        super(code, message);
    }
}
