package com.tidal.interview.tidal.services;

import com.tidal.interview.tidal.data.PlayListTrackDto;
import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.PlaylistTrack;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.exception.CustomErrors;
import com.tidal.interview.tidal.exception.PlaylistException;
import com.tidal.interview.tidal.interfaces.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    public PlaylistRepository playlistRepository;

    @PersistenceContext
    EntityManager entityManager;
    /**
     * Add tracks to the index
     * @return
     */
    @Transactional
    public List<PlayListTrackDto> addTracks(String uuid, List<Track> tracksToAdd, int toIndex) throws PlaylistException {

        try {

            Playlist playList = playlistRepository.findById(uuid).get();

            //We do not allow > 500 tracks in new playlists
            if (playList.getNrOfTracks() + tracksToAdd.size() > 500) {
                throw new PlaylistException();
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

            Collections.sort(originalList);

            List<PlayListTrackDto> afterAdding = new LinkedList<>();
            Set<Track> tracksToAddFinal;
            List<Integer> checkDuplicates = originalList.stream().map(PlaylistTrack::getTrackId).collect(Collectors.toCollection(LinkedList::new));
            tracksToAddFinal = tracksToAdd.stream().filter(track -> !(checkDuplicates.contains(track.getTrackId()))).collect(Collectors.toSet());

            for(Track finalTracks : tracksToAddFinal){
                PlaylistTrack playlistTrack = new PlaylistTrack();
                playlistTrack.setPlaylistId(playList.getId());
                playlistTrack.setPlaylist(playList);
                playlistTrack.setDateAdded(new Date());
                playlistTrack.setTrackId(finalTracks.getTrackId());
                playList.setDuration(addTrackDurationToPlaylist(playList, finalTracks));
                originalList.add(toIndex, playlistTrack);
                toIndex++;
            }

            setPlaylistTracks(playList,originalList);
            getPlayListAfterRemoval(afterAdding,originalList);

            return afterAdding;

        }catch (PlaylistException pe) {
            throw new PlaylistException(CustomErrors.NO_MORE_ADDITION_OF_TRACKS_ALLOWED);
        }
        catch(NoSuchElementException nseException) {
            nseException.printStackTrace();
            throw new PlaylistException(CustomErrors.PLAYLIST_NOT_FOUND);
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistException(CustomErrors.GENERIC_ERROR);
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
    @Transactional
    public List<PlayListTrackDto> removeTracks(String uuid, List<Integer> indexes) throws PlaylistException {

		try {

		    List<PlayListTrackDto> playListsAfterRemoval = new LinkedList <>();
        Playlist playList = playlistRepository.findById(uuid).get();
        List<PlaylistTrack> removePlaylist = new ArrayList<>(playList.getPlaylistTracks());
        Collections.sort(removePlaylist);

            indexes.stream().mapToInt(i -> i).<Predicate<? super PlaylistTrack>>mapToObj(i -> track -> i == track.getTrackIndex()).forEach(removePlaylist::removeIf);
            setPlaylistTracks(playList,removePlaylist);
            getPlayListAfterRemoval(playListsAfterRemoval, removePlaylist);

        System.out.println("Remove PlayList ::::" + removePlaylist);
            return playListsAfterRemoval;
	}catch (PlaylistException pe) {
            throw new PlaylistException(CustomErrors.NO_MORE_ADDITION_OF_TRACKS_ALLOWED);
        }
        catch(NoSuchElementException nseException) {
            nseException.printStackTrace();
            throw new PlaylistException(CustomErrors.PLAYLIST_NOT_FOUND);
        }

        catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistException(CustomErrors.GENERIC_ERROR);
        }
    }
    private void getPlayListAfterRemoval(List < PlayListTrackDto > playListsAfterRemoval, List < PlaylistTrack > removePlaylist) {
        removePlaylist.forEach(playlistTrackAfterRemoval -> {
            PlayListTrackDto playListTrackDto = new PlayListTrackDto();
            playListTrackDto.setId(playlistTrackAfterRemoval.getId());
            playListTrackDto.setDateAdded(playlistTrackAfterRemoval.getDateAdded());
            playListTrackDto.setPlaylistId(playlistTrackAfterRemoval.getPlaylistId());
            playListTrackDto.setTrackId(playlistTrackAfterRemoval.getTrackId());
            playListTrackDto.setTrackIndex(playlistTrackAfterRemoval.getTrackIndex());
            playListsAfterRemoval.add(playListTrackDto);
        });
    }
    /*private boolean validateIndexes(int toIndex, int length) {
        return toIndex >= 0 && toIndex <= length;
    }*/

    private float addTrackDurationToPlaylist(Playlist playList, Track track) {
        return (track != null ? track.getDuration() : 0)
                + (playList != null && playList.getDuration() != null ? playList.getDuration() : 0);
    }

    private void setPlaylistTracks(Playlist playList, List<PlaylistTrack> original) {

        int i = 0;
        for (PlaylistTrack track : original) {
            track.setTrackIndex(i++);
        }

        playList.getPlaylistTracks().clear();
        playList.getPlaylistTracks().addAll(original);
        playList.setNrOfTracks(original.size());
        entityManager.persist(playList);
    }
}
