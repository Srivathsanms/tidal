package com.tidal.interview.tidal.services;

import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.PlaylistTrack;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.exception.PlaylistException;
import com.tidal.interview.tidal.interfaces.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

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
            if (toIndex > size || toIndex < 0) {
                toIndex = size;
            }

           /* //Dont think this validation is needed, Since -1 is allowed assuming that any negative value should be allowed and set in the last index position
            if (!validateIndexes(toIndex, playList.getNrOfTracks())) {
                return Collections.emptyList();
            }*/

            Set<PlaylistTrack> originalSet = playList.getPlaylistTracks();
            List<PlaylistTrack> originalList;
            if (originalSet == null || originalSet.isEmpty())
                originalList = new LinkedList<PlaylistTrack>();
            else
                originalList = new LinkedList<PlaylistTrack>(originalSet);
//NOT Needed
            //Collections.sort(originalList);

            List<PlaylistTrack> added = new ArrayList<PlaylistTrack>(tracksToAdd.size());
            Set<Track> tracksToAddFinal = new HashSet<>();
            List<Integer> x = new LinkedList<>();
            for(PlaylistTrack t : originalList){
                x.add(t.getTrackId());
            }
            for (Track track : tracksToAdd) {
                if(!(x.contains(track.getTrackID()))){
                    tracksToAddFinal.add(track);
                }
                }

            for(Track finalTracks : tracksToAddFinal){
                PlaylistTrack playlistTrack = new PlaylistTrack();
                playlistTrack.setPlaylistID(playList.getId());
                playlistTrack.setPlaylist(playList);
                playlistTrack.setDateAdded(new Date());
                playlistTrack.setTrackId(finalTracks.getTrackID());
                //TODO: Not required duration can be fetched from TRACK table using trackID
                playList.setDuration(addTrackDurationToPlaylist(playList, finalTracks));
                originalList.add(toIndex, playlistTrack);
                added.add(playlistTrack);
                toIndex++;
            }
//
            int i = 0;
            for (PlaylistTrack track : originalList) {
                track.setTrackIndex(i++);
            }

            playList.getPlaylistTracks().clear();
            playList.getPlaylistTracks().addAll(originalList);
            playList.setNrOfTracks(originalList.size());
            playlistRepository.save(playList);
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

    /*private boolean validateIndexes(int toIndex, int length) {
        return toIndex >= 0 && toIndex <= length;
    }*/

    private float addTrackDurationToPlaylist(Playlist playList, Track track) {
        return (track != null ? track.getDuration() : 0)
                + (playList != null && playList.getDuration() != null ? playList.getDuration() : 0);
    }
}
