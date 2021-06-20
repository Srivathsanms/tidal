package com.tidal.interview.tidal.controller;


import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.services.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class PlayListController {


    PlaylistService service;

    @PostMapping(value = "/create/{accountId}", produces ={MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<String> addToplaylist(){

        return new ResponseEntity<>("Account Created", OK);
    }

    @GetMapping(value = "/account/{accountId}/balance",produces ={MediaType.APPLICATION_JSON_VALUE} )
    @ResponseBody
    public void getPlayList(){
        List<Track> trackList = new ArrayList<Track>();
        Track track = new Track();
        track.setArtistId(5);
        track.setTitle("A brand new track");
        track.setId(76868);
        Track track1 = new Track();
        track1.setArtistId(4);
        track1.setTitle("A brand new track");
        track1.setId(7000);

        trackList.add(track);
        trackList.add(track1);
    service.addTracks("111232131",trackList,2);
    }
}
