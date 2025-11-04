package com.interview.domain.playlist;

import com.interview.domain.album.AlbumService;
import com.interview.domain.album.Track;
import com.interview.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final AlbumService albumService;

    public Playlist createPlaylist(String userId, Playlist playlist) {
        playlist.setUserId(userId);
        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", id));
    }

    public List<Playlist> getUserPlaylists(String userId) {
        return playlistRepository.findByUserId(userId);
    }

    public List<Playlist> getPublicPlaylists() {
        return playlistRepository.findByIsPublicTrue();
    }

    public Playlist getPlaylistWithTracks(Long id) {
        return playlistRepository.findByIdWithTracks(id);
    }

    public Playlist addTrackToPlaylist(Long playlistId, Long trackId, String userId) {
        Playlist playlist = getPlaylistById(playlistId);
        
        if (!playlist.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        Track track = albumService.getTrackById(trackId);
        playlist.getTracks().add(track);
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Long id, Playlist updatedPlaylist, String userId) {
        Playlist playlist = getPlaylistById(id);
        
        if (!playlist.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        playlist.setName(updatedPlaylist.getName());
        playlist.setIsPublic(updatedPlaylist.getIsPublic());
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id, String userId) {
        Playlist playlist = getPlaylistById(id);
        
        if (!playlist.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        playlistRepository.deleteById(id);
    }
}
