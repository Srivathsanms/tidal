package com.tidal.interview.tidal.controller;


import com.tidal.interview.tidal.data.PlayListTrackDto;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@RestController
public class PlayListController {

    @Autowired
    PlaylistService service;


    @PostMapping(value = "/add/{uuid}/index/{index}", produces = {APPLICATION_JSON_VALUE})
    public List<PlayListTrackDto> addPlayList(@PathVariable("uuid") String uuid, @PathVariable("index") int index, @RequestBody List<Track> trackList) {
        return service.addTracks(uuid, trackList, index);
    }

    @PostMapping(value = "/remove/{uuid}", produces = {APPLICATION_JSON_VALUE},consumes = {APPLICATION_JSON_VALUE})
    public List <PlayListTrackDto> removePlayList(@PathVariable("uuid") String uuid, @RequestBody List<Integer> indexes) {
        return service.removeTracks(uuid, indexes);
    }

}
