package com.tidal.interview.tidal.data;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="PLAYLIST_AND_TRACK")
public class PlaylistTrack implements Comparable<PlaylistTrack> {

/*
    PLAYLIST_ID VARCHAR(50) NOT NULL,
    TRACK_ID INT(8) NOT NULL,
    TRACK_INDEX INT(3) NOT NULL,
    DATE_ADDED DATE NOT NULL*/

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int Id;
    private String playlistID;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @MapsId("PLAYLISTID")
    @JoinColumn(name = "PLAYLISTID", updatable = false, insertable = false, nullable = false, referencedColumnName = "ID")
    private Playlist playlist;
    private int trackIndex;
    private Date dateAdded;
    private int trackId;

    @Transient
    Track track;
    public PlaylistTrack(){
    this.dateAdded = new Date();
    }
    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

 /*   @Override
    public boolean equals(Object o) {
        PlaylistTrack o1 = (PlaylistTrack) o;
        if(this.trackId==o1.trackId) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result =  0;
        result = 31 * result + trackIndex;
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        result = 31 * result + trackId;
        return result;
    }*/

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