package com.tidal.interview.tidal.data;

import sun.reflect.generics.tree.Tree;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;


/**
 * A very simplified version of TrackPlaylist
 */
@Entity
@Table(name = "PLAYLIST")
public class Playlist {
/*
    UUID VARCHAR(50) NOT NULL, // Done
    Playlist_Name VARCHAR(50) NOT NULL,// Done
    Number_of_Tracks INT(8) NOT NULL,//Done
    Last_Updated_date DATE NOT NULL,//Done
    Registered_date DATE NOT NULL,
    Deleted NUMERIC (1) DEFAULT 0*/
    @Id
    private String uuid;
    private String playlistName;
    private Set<PlaylistTrack> playlistTracks = new TreeSet<PlaylistTrack>();
    private Date registeredDate;
    private Date lastUpdatedDate;
    //Why are we using UUID it can be an normal ID

    private int nrOfTracks;
    private boolean deleted;
    private Float duration;

    public Playlist() {
        this.uuid = UUID.randomUUID().toString();
        Date d = new Date();
        this.registeredDate = d;
        this.lastUpdatedDate = d;
        this.playlistTracks = new HashSet<PlaylistTrack>();
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Set<PlaylistTrack> getPlaylistTracks() {
        return playlistTracks;
    }

    public void setPlaylistTracks(Set<PlaylistTrack> playlistTracks) {
        this.playlistTracks = playlistTracks;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getNrOfTracks() {
        return nrOfTracks;
    }

    public void setNrOfTracks(int nrOfTracks) {
        this.nrOfTracks = nrOfTracks;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
