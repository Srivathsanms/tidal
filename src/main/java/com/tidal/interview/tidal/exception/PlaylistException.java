package com.tidal.interview.tidal.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaylistException extends RuntimeException {

    private String messageCode;

    private CustomErrors customErrors;

    public CustomErrors getCustomErrors() {
        return customErrors;
    }

    public void setCustomErrors(CustomErrors customErrors) {
        this.customErrors = customErrors;
    }


    public PlaylistException(CustomErrors customErrors) {
        this.customErrors = customErrors;
        this.messageCode = customErrors.getErrorMessage();
    }


    public String getMessageCode() {
        return messageCode;
    }
    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
