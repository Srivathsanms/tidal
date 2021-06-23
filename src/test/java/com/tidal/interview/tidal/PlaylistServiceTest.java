package com.tidal.interview.tidal;

import com.tidal.interview.tidal.data.PlayListTrackDto;
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

import javax.persistence.EntityManager;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

	@InjectMocks
	private PlaylistService playlistService;

	@Mock
	private PlaylistRepository playlistRepository;

	@Mock
	private EntityManager entityManager;

	@Test
	public void testAddTracksSuccess() {

		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(new Playlist()));
		List<PlayListTrackDto> playlistTracks = playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 5);
		assertTrue(playlistTracks.size() > 0);
	}

	@Test
	public void addTracksToAPlaylistThatDoesNotExists() {
		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.empty());
		final PlaylistException playlistException =assertThrows(PlaylistException.class, () -> playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 2));
		assertEquals("Playlist does not exists", playlistException.getMessageCode());
	}

	@Test
	public void addTracksBeyondThresholdTest() {
		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(getPlaylistToCheckThreshold()));
		final PlaylistException playlistException = assertThrows(PlaylistException.class, () -> playlistService.addTracks(UUID.randomUUID().toString(), getTracks(), 130));
		assertEquals("Playlist cannot have more than 500 tracks", playlistException.getMessageCode());
	}

	@Test
	public void testRemoveTracksSuccess() {
		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(getPlaylistToCheckRemove()));
		Mockito.doNothing().when(entityManager).persist(any());
		List< PlayListTrackDto > playlistTracks = playlistService.removeTracks(UUID.randomUUID().toString(), getIndices());
		assertEquals(1, playlistTracks.size());
	}

	@Test
	public void testRemoveTracksFromEmptyPlayListTrack() {
		Mockito.when(playlistRepository.findById(anyString())).thenReturn(Optional.of(getEmptyPlayListTrack()));
		Mockito.doNothing().when(entityManager).persist(any());
		List< PlayListTrackDto > playlistTracks = playlistService.removeTracks(UUID.randomUUID().toString(), getIndices());
		assertEquals(0, playlistTracks.size());
	}

	private Playlist getEmptyPlayListTrack() {
		Playlist playlist = new Playlist();
		playlist.setPlaylistTracks(Collections.EMPTY_SET);
		return playlist;
	}


	private List<Track> getTracks() {
		List<Track> trackList = new ArrayList<>();
		Track track = mock(Track.class);
		trackList.add(track);

		return trackList;
	}

	private List<Integer> getIndices() {
		List<Integer> indexesToRemove = new ArrayList<>();
		indexesToRemove.add(0);
		return indexesToRemove;
	}

	private Playlist getPlaylistToCheckThreshold() {

		Playlist playlist = new Playlist();
		playlist.setNrOfTracks(500);
		playlist.setPlaylistName("Playlist1");
		return playlist;
	}

	private Playlist getPlaylistToCheckRemove() {
		Set<PlaylistTrack> playlistTracks = new HashSet <>();
		PlaylistTrack mockPlaylist = new PlaylistTrack();
		mockPlaylist.setTrackIndex(0);
		mockPlaylist.setTrackId(2);

		PlaylistTrack mockPlaylist1	 = new PlaylistTrack();
		mockPlaylist1.setTrackIndex(1);
		mockPlaylist1.setTrackId(3);
		playlistTracks.add(mockPlaylist);
		playlistTracks.add(mockPlaylist1);

		Playlist playlist = new Playlist();
		playlist.setNrOfTracks(2);
		playlist.setPlaylistTracks(playlistTracks);
		return playlist;
	}


}
