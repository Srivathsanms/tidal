package com.tidal.interview.tidal.controller;


import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@RestController
public class PlayListController {

    @Autowired
    PlaylistService service;

    @PostMapping(value = "/create/{accountId}", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addToplaylist() {

        return new ResponseEntity<>("Account Created", OK);
    }

    //TODO: should be POST call???
    @PostMapping(value = "/add/{uuid}/index/{index}", produces = {APPLICATION_JSON_VALUE})
    public ResponseEntity<String> addPlayList(@PathVariable("uuid") String uuid, @PathVariable("index") int index, @RequestBody List<Track> trackList) {
        /*List<Track> trackList = getTracks();*/
        service.addTracks(uuid, trackList, index);
        return new ResponseEntity<>("Tracks Added", OK);
    }

    @PostMapping(value = "/remove/{uuid}", produces = {APPLICATION_JSON_VALUE},consumes = {APPLICATION_JSON_VALUE})
    public void removePlayList(@PathVariable("uuid") String uuid, @RequestBody List<Integer> indexes) {
        List<Integer> indixes = new LinkedList<>();
        indixes.add(1);
        indixes.add(2);
        service.removeTracks(uuid, indexes);
    }

    private List<Track> getTracks() {
        List<Track> trackList = new ArrayList<Track>();

        Track track = new Track();
        track.setArtistId(5);
        track.setTitle("Track 13");
        track.setTrackId(760);
        track.setDuration(3.05f);

        Track track1 = new Track();
        track1.setArtistId(4);
        track1.setTitle("Track 18");
        track1.setTrackId(700);
        track1.setDuration(2.55f);

        trackList.add(track);
        trackList.add(track1);
        return trackList;
    }


    @PostMapping(value = "/track", produces = {APPLICATION_JSON_VALUE},consumes = {APPLICATION_JSON_VALUE})
    public void getTracks(@RequestBody List<Track> track){

        for(Track t : track){
            System.out.println("Track ID :::::" + t.getTrackId());
        }
    }
}
