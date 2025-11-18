package com.example.musicstreaming.mapper;


import com.example.musicstreaming.Dto.PlaylistDto;
import com.example.musicstreaming.model.Playlist;
import com.example.musicstreaming.model.Song;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PlaylistDtoMapper implements Function<Playlist, PlaylistDto> {
    @Override
    public PlaylistDto apply(Playlist playlist) {
        Long userId = (playlist.getUser() != null) ? playlist.getUser().getId() : null;

        return new PlaylistDto(
                playlist.getId(),
                playlist.getName(),
                playlist.getDescription(),
                playlist.getCreatedAt(),
                userId,
                playlist.getSongs().stream()
                        .map(Song::getId)
                        .collect(Collectors.toList())
        );
    }

}
