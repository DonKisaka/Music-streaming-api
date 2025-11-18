package com.example.musicstreaming.service;

import com.example.musicstreaming.Dto.ArtistDto;
import com.example.musicstreaming.mapper.ArtistDtoMapper;
import com.example.musicstreaming.model.Artist;
import com.example.musicstreaming.repository.ArtistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArtistService {


    private final ArtistRepository artistRepository;
    private final ArtistDtoMapper artistDtoMapper;

    public ArtistService(ArtistRepository artistRepository, ArtistDtoMapper artistDtoMapper) {
        this.artistRepository = artistRepository;
        this.artistDtoMapper = artistDtoMapper;
    }

    @Transactional
    public ArtistDto createArtist(ArtistDto dto) {
        Artist artist = Artist.builder()
                .name(dto.name())
                .genre(dto.genre())
                .biography(dto.biography())
                .build();
        return artistDtoMapper.apply(artistRepository.save(artist));
    }

    public ArtistDto getArtistById(Long id) {
        return artistDtoMapper.apply(artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found")));
    }

    public List<ArtistDto> getArtistsByName(String name) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(name);
        if (artists.isEmpty()) {
            throw new IllegalArgumentException("Artist not found with name containing: " + name);
        }
        return artists.stream()
                .map(artistDtoMapper)
                .toList();

    }

    public List<ArtistDto> getArtistsByGenre(String genre) {
        return artistRepository.findByGenre(genre).stream()
                .map(artistDtoMapper)
                .toList();
    }


    public List<ArtistDto> getAllArtists() {
       return artistRepository.findAll().stream()
               .map(artistDtoMapper)
               .toList();
    }



    @Transactional
    public ArtistDto updateArtist(Long id, ArtistDto dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Artist not found"));
        artist.setName(dto.name());
        artist.setGenre(dto.genre());
        artist.setBiography(dto.biography());
        return artistDtoMapper.apply(artistRepository.save(artist));
    }

    @Transactional
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new IllegalArgumentException("Artist not found");
        }
        artistRepository.deleteById(id);
    }
}
