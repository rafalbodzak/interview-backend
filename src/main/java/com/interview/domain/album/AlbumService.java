package com.interview.domain.album;

import com.interview.domain.artist.Artist;
import com.interview.domain.artist.ArtistService;
import com.interview.infrastructure.exception.ResourceNotFoundException;
import com.interview.infrastructure.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;
    private final ArtistService artistService;
    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public Album createAlbum(Long artistId, Album album) {
        Artist artist = artistService.getArtistById(artistId);
        album.setArtist(artist);
        Album saved = albumRepository.save(album);
        
        notificationService.sendAlbumCreatedNotification(artist.getName(), saved.getTitle());
        
        return saved;
    }

    public Album getAlbumById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album", id));
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public List<Album> getAlbumsByArtist(Long artistId) {
        return albumRepository.findByArtistId(artistId);
    }

    public Album getAlbumWithTracks(Long id) {
        return albumRepository.findByIdWithTracks(id);
    }

    public Album updateAlbum(Long id, Album updatedAlbum) {
        Album album = getAlbumById(id);
        album.setTitle(updatedAlbum.getTitle());
        album.setReleaseYear(updatedAlbum.getReleaseYear());
        return albumRepository.save(album);
    }

    public void deleteAlbum(Long id) {
        albumRepository.deleteById(id);
    }

    // Track operations
    public Track createTrack(Long albumId, Track track) {
        Album album = getAlbumById(albumId);
        track.setAlbum(album);
        return trackRepository.save(track);
    }

    public Track getTrackById(Long id) {
        return trackRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Track", id));
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public List<Track> getTracksByAlbum(Long albumId) {
        return trackRepository.findByAlbumId(albumId);
    }

    public List<Track> getMostPlayedTracks() {
        return trackRepository.findMostPlayed();
    }

    public void incrementPlayCount(Long trackId) {
        trackRepository.incrementPlayCount(trackId);
    }

    public Track updateTrack(Long id, Track updatedTrack) {
        Track track = getTrackById(id);
        track.setTitle(updatedTrack.getTitle());
        track.setDurationSeconds(updatedTrack.getDurationSeconds());
        track.setTrackNumber(updatedTrack.getTrackNumber());
        return trackRepository.save(track);
    }

    public void deleteTrack(Long id) {
        trackRepository.deleteById(id);
    }
}
