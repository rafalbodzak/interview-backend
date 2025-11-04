package com.interview.domain.artist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody ArtistRequest request) {
        Artist artist = Artist.builder()
                .name(request.getName())
                .country(request.getCountry())
                .build();
        
        Artist created = artistService.createArtist(artist);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
        Artist artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/country/{country}")
    public ResponseEntity<List<Artist>> getArtistsByCountry(@PathVariable String country) {
        List<Artist> artists = artistService.getArtistsByCountry(country);
        return ResponseEntity.ok(artists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, 
                                               @Valid @RequestBody ArtistRequest request) {
        Artist artist = Artist.builder()
                .name(request.getName())
                .country(request.getCountry())
                .build();
        
        Artist updated = artistService.updateArtist(id, artist);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }
}
