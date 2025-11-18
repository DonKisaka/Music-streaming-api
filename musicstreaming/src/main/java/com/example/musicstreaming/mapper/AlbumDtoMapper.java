package com.example.musicstreaming.mapper;

import com.example.musicstreaming.Dto.AlbumDto;
import com.example.musicstreaming.model.Album;
import com.example.musicstreaming.model.Song;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AlbumDtoMapper implements Function<Album, AlbumDto> {
    @Override
    public AlbumDto apply(Album album) {
        return new AlbumDto(
                album.getId(),
                album.getTitle(),
                album.getReleaseYear(),
                album.getArtist().getId(),
                album.getSongs().stream()
                        .map(Song::getId)
                        .collect(Collectors.toList())
        );
    }
}
