package com.tidal.interview.tidal.exception;

import lombok.Getter;

@Getter
public enum CustomErrors {

    PLAYLIST_NOT_FOUND("Playlist does not exists"),
    NO_MORE_ADDITION_OF_TRACKS_ALLOWED("Playlist cannot have more than 500 tracks"),
    GENERIC_ERROR("Some problem has occurred");

    String errorMessage;

    CustomErrors(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
