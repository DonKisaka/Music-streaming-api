package com.example.musicstreaming.mapper;

import com.example.musicstreaming.Dto.ArtistDto;
import com.example.musicstreaming.model.Artist;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ArtistDtoMapper implements Function<Artist, ArtistDto> {
    @Override
    public ArtistDto apply(Artist artist) {
        return new ArtistDto(
                artist.getId(),
                artist.getName(),
                artist.getGenre(),
                artist.getBiography()
        );
    }
}
