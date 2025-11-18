package com.example.musicstreaming.Dto;

import java.util.List;

public record AlbumDto(
        Long id,
        String title,
        Integer releaseYear,
        Long artistId,
        List<Long> songIds
) {}
