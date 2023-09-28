package com.project.springapistudy.beverages.exception;

import com.project.springapistudy.beverages.constants.BeverageMessages;

public class BeverageNotFountException extends BeverageException {

    public BeverageNotFountException(BeverageMessages messages) {
        super(messages);
    }

    public BeverageNotFountException(long code, String message) {
        super(code, message);
    }
}
