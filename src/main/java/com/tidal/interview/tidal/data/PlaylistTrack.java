package com.tidal.interview.tidal.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PlaylistTrack implements Comparable<PlaylistTrack> {

/*
    PLAYLIST_ID VARCHAR(50) NOT NULL,
    TRACK_ID INT(8) NOT NULL,
    TRACK_INDEX INT(3) NOT NULL,
    DATE_ADDED DATE NOT NULL*/

    @Id
    private String playlistID;

    private int trackIndex;
    private Date dateAdded;
    private int trackId;

    @Override
    public int compareTo(PlaylistTrack o) {
        return 0;
    }

    public String getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public int getTrackIndex() {
        return trackIndex;
    }

    public void setTrackIndex(int trackIndex) {
        this.trackIndex = trackIndex;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }
}