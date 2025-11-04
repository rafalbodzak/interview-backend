package com.interview.domain.artist;

import com.interview.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public List<Artist> getArtistsByCountry(String country) {
        return artistRepository.findByCountry(country);
    }

    public Artist updateArtist(Long id, Artist updatedArtist) {
        Artist artist = getArtistById(id);
        artist.setName(updatedArtist.getName());
        artist.setCountry(updatedArtist.getCountry());
        return artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.deleteById(id);
    }
}
