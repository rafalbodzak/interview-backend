package com.interview.domain.playlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    
    List<Playlist> findByUserId(String userId);
    
    List<Playlist> findByIsPublicTrue();
    
    @Query("SELECT p FROM Playlist p JOIN FETCH p.tracks WHERE p.id = :playlistId")
    Playlist findByIdWithTracks(@Param("playlistId") Long playlistId);
}
