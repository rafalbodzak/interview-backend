package com.interview.domain.playlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@Valid @RequestBody PlaylistRequest request,
                                                    Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        
        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .isPublic(request.getIsPublic())
                .build();
        
        Playlist created = playlistService.createPlaylist(userId, playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id) {
        Playlist playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{id}/with-tracks")
    public ResponseEntity<Playlist> getPlaylistWithTracks(@PathVariable Long id) {
        Playlist playlist = playlistService.getPlaylistWithTracks(id);
        return ResponseEntity.ok(playlist);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my")
    public ResponseEntity<List<Playlist>> getMyPlaylists(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        List<Playlist> playlists = playlistService.getUserPlaylists(userId);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/public")
    public ResponseEntity<List<Playlist>> getPublicPlaylists() {
        List<Playlist> playlists = playlistService.getPublicPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{playlistId}/tracks/{trackId}")
    public ResponseEntity<Playlist> addTrackToPlaylist(@PathVariable Long playlistId,
                                                        @PathVariable Long trackId,
                                                        Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        Playlist updated = playlistService.addTrackToPlaylist(playlistId, trackId, userId);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id,
                                                    @Valid @RequestBody PlaylistRequest request,
                                                    Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        
        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .isPublic(request.getIsPublic())
                .build();
        
        Playlist updated = playlistService.updatePlaylist(id, playlist, userId);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id,
                                                Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        playlistService.deletePlaylist(id, userId);
        return ResponseEntity.noContent().build();
    }
}
