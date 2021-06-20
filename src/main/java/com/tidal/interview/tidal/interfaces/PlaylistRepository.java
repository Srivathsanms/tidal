package com.tidal.interview.tidal.interfaces;

import com.tidal.interview.tidal.data.Playlist;


import java.util.Optional;

public interface PlaylistRepository {

    Optional<Playlist> getPlaylistByUUID(String uuid);

}
