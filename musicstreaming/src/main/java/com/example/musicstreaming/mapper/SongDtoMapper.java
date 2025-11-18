package com.example.musicstreaming.mapper;

import com.example.musicstreaming.Dto.SongDto;
import com.example.musicstreaming.model.Artist;
import com.example.musicstreaming.model.Song;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SongDtoMapper implements Function<Song, SongDto> {
    @Override
    public SongDto apply(Song song) {
        return new SongDto(
                song.getId(),
                song.getTitle(),
                song.getDurationInSeconds(),
                song.getAlbum().getId(),
                song.getArtists().stream()
                        .map(Artist::getId)
                        .collect(Collectors.toList())
        );
    }
}
