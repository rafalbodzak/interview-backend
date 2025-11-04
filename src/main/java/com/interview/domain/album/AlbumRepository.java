package com.interview.domain.album;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    
    List<Album> findByArtistId(Long artistId);
    
    List<Album> findByReleaseYear(Integer year);
    
    @Query("SELECT a FROM Album a JOIN FETCH a.tracks WHERE a.id = :albumId")
    Album findByIdWithTracks(@Param("albumId") Long albumId);
}
