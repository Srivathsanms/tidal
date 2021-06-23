package com.tidal.interview.tidal.data;


import javax.persistence.*;
import java.util.*;


/**
 * A very simplified version of TrackPlaylist
 */
@Entity
@Table(name = "PLAYLIST")
public class Playlist {

    @Id
    @Column(name = "id")
    private String id;
    private String playlistName;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "playlistId", referencedColumnName = "id", updatable = false, insertable = false, nullable = false)
    private Set<PlaylistTrack> playlistTracks = new TreeSet<>();
    private Date registeredDate;
    private Date lastUpdatedDate;

    private int nrOfTracks;
    private boolean deleted;
    private Float duration;

    public Playlist() {
        this.id = UUID.randomUUID().toString();
        Date d = new Date();
        this.registeredDate = d;
        this.lastUpdatedDate = d;
        this.playlistTracks = new HashSet<>();
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
