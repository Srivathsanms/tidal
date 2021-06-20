package com.tidal.interview.tidal.services;

import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.PlaylistTrack;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.exception.PlaylistException;
import com.tidal.interview.tidal.interfaces.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PlaylistService {

    @Autowired
    public PlaylistRepository playlistRepository;

   /* public PlaylistService(PlaylistRepository playlistRepository){
        this.playlistRepository = playlistRepository;
    }*/
    /**
     * Add tracks to the index
     */
    public List<PlaylistTrack> addTracks(String uuid, List<Track> tracksToAdd, int toIndex) throws PlaylistException {

        try {

            Playlist playList = playlistRepository.findById(uuid).get();

            //We do not allow > 500 tracks in new playlists
            if (playList.getNrOfTracks() + tracksToAdd.size() > 500) {
                throw new PlaylistException("Playlist cannot have more than " + 500 + " tracks");
            }

            // The index is out of bounds, put it in the end of the list.
            int size = playList.getPlaylistTracks() == null ? 0 : playList.getPlaylistTracks().size();
            if (toIndex > size || toIndex == -1) {
                toIndex = size;
                System.out.println("to Index : " + toIndex);
                System.out.println("No of Tracks : " + playList.getNrOfTracks());
            }

            if (!validateIndexes(toIndex, playList.getNrOfTracks())) {
                return Collections.EMPTY_LIST;
            }

            Set<PlaylistTrack> originalSet = playList.getPlaylistTracks();
            List<PlaylistTrack> original;
            if (originalSet == null || originalSet.size() == 0)
                original = new ArrayList<PlaylistTrack>();
            else
                original = new ArrayList<PlaylistTrack>(originalSet);

            Collections.sort(original);

            List<PlaylistTrack> added = new ArrayList<PlaylistTrack>(tracksToAdd.size());

            for (Track track : tracksToAdd) {
                System.out.println("Track : " + track);
                PlaylistTrack playlistTrack = new PlaylistTrack();
                //playlistTrack.setTrack(track);
                //playlistTrack.setPlaylist(playList);
                playlistTrack.setDateAdded(new Date());
               // playlistTrack.setTrack(track);

                playlistTrack.setTrackId(track.getTrackID());
                //TODO: Not required duration can be fetched from TRACK table using trackID
                playList.setDuration(addTrackDurationToPlaylist(playList, track));
                original.add(toIndex, playlistTrack);
                added.add(playlistTrack);
                System.out.println("Original :::" + original);
                System.out.println("playListTracks :::" + playlistTrack);
                toIndex++;
            }
//
            int i = 0;
            for (PlaylistTrack track : original) {
                track.setTrackIndex(i++);
            }

            playList.getPlaylistTracks().clear();
            playList.getPlaylistTracks().addAll(original);
            playList.setNrOfTracks(original.size());

            return added;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistException("Generic error");
        }
    }
    
    /**
     * Remove the tracks from the playlist located at the sent indexes
     *
     * @param uuid identifies the playlist
     * @param indexes indexes of the tracks in the playlist that need to be removed
     * @return the tracks in the playlist after the removal
     * @throws PlaylistException
     */
	public List<PlaylistTrack> removeTracks(String uuid, List<Integer> indexes) throws PlaylistException {
		// TODO
		return Collections.EMPTY_LIST;
	}

    private boolean validateIndexes(int toIndex, int length) {
        return toIndex >= 0 && toIndex <= length;
    }

    private float addTrackDurationToPlaylist(Playlist playList, Track track) {
        return (track != null ? track.getDuration() : 0)
                + (playList != null && playList.getDuration() != null ? playList.getDuration() : 0);
    }
}
