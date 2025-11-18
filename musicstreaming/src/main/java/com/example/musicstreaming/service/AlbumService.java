package com.example.musicstreaming.service;

import com.example.musicstreaming.Dto.AlbumDto;
import com.example.musicstreaming.mapper.AlbumDtoMapper;
import com.example.musicstreaming.model.Album;
import com.example.musicstreaming.model.Artist;
import com.example.musicstreaming.model.Song;
import com.example.musicstreaming.repository.AlbumRepository;
import com.example.musicstreaming.repository.ArtistRepository;
import com.example.musicstreaming.repository.SongRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumDtoMapper  albumDtoMapper;

    public AlbumService(AlbumRepository albumRepository, SongRepository songRepository, ArtistRepository artistRepository, AlbumDtoMapper albumDtoMapper) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumDtoMapper = albumDtoMapper;
    }

    @Transactional
    public AlbumDto createAlbum(AlbumDto dto) {
        Artist artist = artistRepository.findById(dto.artistId())
                .orElseThrow(() -> new RuntimeException("Album Not Found"));

        Set<Song> songs = dto.songIds().stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() -> new RuntimeException("Song Not Found with ID: " + songId)))
                .collect(Collectors.toSet());


        Album album = Album.builder()
                .title(dto.title())
                .releaseYear(dto.releaseYear())
                .artist(artist)
                .songs(songs)
                .build();
        return albumDtoMapper.apply(albumRepository.save(album));
    }

    public AlbumDto getAlbumById(Long albumId) {
        return albumDtoMapper.apply(albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album Not Found with ID: " + albumId)));
    }

    public Page<AlbumDto> getAllAlbums(Pageable pageable) {
        return albumRepository.findAll(pageable)
                .map(albumDtoMapper);
    }

    public Page<AlbumDto> getAlbumsByArtistId(Long artistId, Pageable pageable) {
        return albumRepository.findAllByArtistId(artistId, pageable)
                .map(albumDtoMapper);
    }

    public Page<AlbumDto> getAlbumsByTitle(String title, Pageable pageable) {
        Page<Album> albums = albumRepository.findByTitleContainingIgnoreCase(title, pageable);
        return albums.map(albumDtoMapper);
    }

    public Page<AlbumDto> getAlbumByReleaseYear(Integer year, Pageable pageable) {
        Page<Album> albums = albumRepository.findByReleaseYear(year, pageable);
        return albums.map(albumDtoMapper);

    }

   @Transactional
   public AlbumDto updateAlbum(Long albumId, AlbumDto dto) {
        Album album =  albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Album Not Found"));

        album.setTitle(dto.title());
        album.setReleaseYear(dto.releaseYear());

        Artist artist = artistRepository.findById(dto.artistId())
                .orElseThrow(() -> new RuntimeException("Artist Not Found with ID: " + dto.artistId()));

       Set<Song> songs = dto.songIds().stream()
               .map(songId -> songRepository.findById(songId)
                       .orElseThrow(() -> new IllegalArgumentException("Song not found with ID: " + songId)))
               .collect(Collectors.toSet());

       album.setSongs(songs);
       album.setArtist(artist);

       return albumDtoMapper.apply(albumRepository.save(album));
   }


    @Transactional
    public void deleteAlbum(Long albumId) {
        if (!albumRepository.existsById(albumId)) {
            throw new RuntimeException("Album Not Found with ID: " + albumId);
        }
        albumRepository.deleteById(albumId);

    }

}
