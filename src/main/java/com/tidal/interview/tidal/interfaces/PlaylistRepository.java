package com.tidal.interview.tidal.interfaces;

import com.tidal.interview.tidal.data.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface PlaylistRepository extends CrudRepository<Playlist,String> {



    Optional<Playlist> findById(String uuid);
}
