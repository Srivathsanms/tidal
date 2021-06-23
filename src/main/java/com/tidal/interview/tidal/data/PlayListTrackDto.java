package com.tidal.interview.tidal.data;

import lombok.Data;

import java.util.Date;

@Data
public class PlayListTrackDto {
    private int id;
    private String playlistId;
    private int trackIndex;
    private Date dateAdded;
    private int trackId;
}
