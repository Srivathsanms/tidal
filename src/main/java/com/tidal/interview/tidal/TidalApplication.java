package com.tidal.interview.tidal;

import com.tidal.interview.tidal.data.Playlist;
import com.tidal.interview.tidal.data.PlaylistTrack;
import com.tidal.interview.tidal.interfaces.PlaylistRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TidalApplication {

	public static void main(String[] args) {
		SpringApplication.run(TidalApplication.class, args);
	}


	@Bean
	CommandLineRunner runner(PlaylistRepository playlistRepository){
		return args -> {
			Playlist playlist = new Playlist();
			playlist.setNrOfTracks(2);
			playlist.setDuration(12.0f);
			playlist.setDeleted(false);
			playlist.setPlaylistName("PlayList1");
			playlist.setPlaylistTracks(getPlayListTracks(playlist));
			playlistRepository.save(playlist);
		};
	}

	private Set<PlaylistTrack> getPlayListTracks(Playlist playlist) {
		Set<PlaylistTrack> playlistTracks = new HashSet<>();
		PlaylistTrack playlistTrack = new PlaylistTrack();
		playlistTrack.setPlaylist(playlist);
		playlistTrack.setTrackIndex(0);
		playlistTrack.setPlaylistID(playlist.getId());
		playlistTrack.setTrackId(200);

		PlaylistTrack playlistTrack1 = new PlaylistTrack();
		playlistTrack1.setPlaylist(playlist);
		playlistTrack1.setTrackIndex(1);
		playlistTrack1.setPlaylistID(playlist.getId());
		playlistTrack1.setTrackId(202);

		playlistTracks.add(playlistTrack);
		playlistTracks.add(playlistTrack1);
		return playlistTracks;
	}
}
