package com.interview.domain.album;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists/{artistId}/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    // Album endpoints
    @PreAuthorize("hasRole('ARTIST')")
    @PostMapping
    public ResponseEntity<Album> createAlbum(@PathVariable Long artistId,
                                           @Valid @RequestBody AlbumRequest request) {
        Album album = Album.builder()
                .title(request.getTitle())
                .releaseYear(request.getReleaseYear())
                .build();
        
        Album created = albumService.createAlbum(artistId, album);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping
    public ResponseEntity<List<Album>> getAlbumsByArtist(@PathVariable Long artistId) {
        List<Album> albums = albumService.getAlbumsByArtist(artistId);
        return ResponseEntity.ok(albums);
    }

    @GetMapping
    public ResponseEntity<Album> getAlbum(@PathVariable Long artistId,
                                          @RequestParam Long albumId) {
        Album album = albumService.getAlbumById(albumId);
        return ResponseEntity.ok(album);
    }

    @PreAuthorize("hasRole('ARTIST')")
    @PostMapping("/{albumId}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long artistId,
                                            @PathVariable Long albumId,
                                            @Valid @RequestBody AlbumRequest request) {
        Album album = Album.builder()
                .title(request.getTitle())
                .releaseYear(request.getReleaseYear())
                .build();
        
        Album updated = albumService.updateAlbum(albumId, album);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ARTIST')")
    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long artistId,
                                            @PathVariable Long albumId) {
        albumService.deleteAlbum(albumId);
        return ResponseEntity.noContent().build();
    }

    // Track endpoints
    @PreAuthorize("hasRole('ARTIST')")
    @PostMapping("/{albumId}/create-track")
    public ResponseEntity<Track> createTrack(@PathVariable Long albumId,
                                           @Valid @RequestBody TrackRequest request) {
        Track track = Track.builder()
                .title(request.getTitle())
                .durationSeconds(request.getDurationSeconds())
                .trackNumber(request.getTrackNumber())
                .build();
        
        Track created = albumService.createTrack(albumId, track);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{albumId}/tracks")
    public ResponseEntity<List<Track>> getTracksByAlbum(@PathVariable Long albumId) {
        List<Track> tracks = albumService.getTracksByAlbum(albumId);
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{albumId}/tracks/{trackId}")
    public ResponseEntity<Track> getTrack(@PathVariable Long trackId) {
        Track track = albumService.getTrackById(trackId);
        return ResponseEntity.ok(track);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{albumId}/tracks/{trackId}/play")
    public ResponseEntity<Void> playTrack(@PathVariable Long trackId) {
        albumService.incrementPlayCount(trackId);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ARTIST')")
    @PutMapping("/{albumId}/tracks/{trackId}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long trackId,
                                           @Valid @RequestBody TrackRequest request) {
        Track track = Track.builder()
                .title(request.getTitle())
                .durationSeconds(request.getDurationSeconds())
                .trackNumber(request.getTrackNumber())
                .build();
        
        Track updated = albumService.updateTrack(trackId, track);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('ARTIST')")
    @DeleteMapping("/{albumId}/tracks/{trackId}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long trackId) {
        albumService.deleteTrack(trackId);
        return ResponseEntity.noContent().build();
    }
}
