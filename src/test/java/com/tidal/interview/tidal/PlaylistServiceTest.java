package com.tidal.interview.tidal;

import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.PlaylistTrack;
import com.tidal.interview.tidal.data.Track;
import com.tidal.interview.tidal.exception.PlaylistException;
import com.tidal.interview.tidal.interfaces.PlaylistRepository;
import com.tidal.interview.tidal.services.PlaylistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

	@InjectMocks
	private PlaylistService playlistService;

	@Mock
	private PlaylistRepository playlistRepository;

	@Test
	public void testAddTracksSuccess() {

		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(new Playlist()));
		List<PlaylistTrack> playlistTracks = playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 5);
		assertTrue(playlistTracks.size() > 0);
	}

	@Test
    public void addTracksToAPlaylistThatDoesNotExists() {
	    Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.empty());
	    final PlaylistException playlistException =assertThrows(PlaylistException.class, () -> playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 2));
        assertEquals("Playlist does not exists", playlistException.getMessage());
    }

   /* @Test
    public void addTracksBeyondThresholdTest() {
	    Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(getPlaylist()));
	    final PlaylistException playlistException = assertThrows(PlaylistException.class, () -> playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 130));
	    assertEquals("Playlist cannot have more than 500 tracks", playlistException.getMessage());
    }*/

   @Test
   public void testRemoveTracksSuccess() {
   		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(new Playlist()));
   		List<PlaylistTrack> playlistTracks = playlistService.removeTracks(UUID.randomUUID().toString(), getIndices());
   }
    private List<Track> getTracks() {
        List<Track> trackList = new ArrayList<>();

        Track track = new Track();
        track.setArtistId(4);
        track.setTrackId(700);
        track.setTitle("Track1");
        trackList.add(track);

       return trackList;
    }

    private List<Integer> getIndices() {
   		List<Integer> indexesToRemove = new ArrayList<>();
		indexesToRemove.add(1);
		return indexesToRemove;
	}

    private Playlist getPlaylist() {
	    Playlist playlist = new Playlist();
	    playlist.setNrOfTracks(499);
	    playlist.setPlaylistName("Playlist1");
	    return playlist;

    }
}
