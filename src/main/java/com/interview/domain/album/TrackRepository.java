package com.interview.domain.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
    
    List<Track> findByAlbumId(Long albumId);
    
    @Query("SELECT t FROM Track t WHERE t.album.artist.id = :artistId")
    List<Track> findByArtistId(@Param("artistId") Long artistId);
    
    @Query("SELECT t FROM Track t ORDER BY t.playCount DESC")
    List<Track> findMostPlayed();
    
    @Modifying
    @Query("UPDATE Track t SET t.playCount = t.playCount + 1 WHERE t.id = :trackId")
    void incrementPlayCount(@Param("trackId") Long trackId);
}
