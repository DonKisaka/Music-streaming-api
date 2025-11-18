package com.example.musicstreaming.service;

import com.example.musicstreaming.Dto.SongDto;
import com.example.musicstreaming.mapper.SongDtoMapper;
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
public class SongService {

    private final SongRepository songRepository;
    private final SongDtoMapper songDtoMapper;
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    public SongService(SongRepository songRepository, SongDtoMapper songDtoMapper, AlbumRepository albumRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.songDtoMapper = songDtoMapper;
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional
    public SongDto createSong(SongDto dto) {
        Album album = albumRepository.findById(dto.albumId())
                .orElseThrow(() -> new RuntimeException("Album Not Found"));

        Set<Artist> artists = dto.artistIds().stream()
                .map(artistId -> artistRepository.findById(artistId)
                        .orElseThrow(() -> new RuntimeException("Artist Not Found with ID: " + artistId)))
                .collect(Collectors.toSet());

        Song song = Song.builder()
                .title(dto.title())
                .durationInSeconds(dto.durationInSeconds())
                .album(album)
                .artists(artists)
                .build();
        return songDtoMapper.apply(songRepository.save(song));
    }

    public SongDto getSongById(Long id) {
        return songDtoMapper.apply(songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found")));
    }

    public Page<SongDto> getAllSongs(Pageable pageable) {
        return songRepository.findAll(pageable)
                .map(songDtoMapper);
    }

    public Page<SongDto> getSongsByAlbumId(Long albumId, Pageable pageable) {
        return songRepository.findAllByAlbumId(albumId, pageable)
                .map(songDtoMapper);
    }

    @Transactional
    public SongDto updateSong(Long id, SongDto dto) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Song not found"));

        song.setTitle(dto.title());
        song.setDurationInSeconds(dto.durationInSeconds());

        Album album = albumRepository.findById(dto.albumId())
                .orElseThrow(() -> new IllegalArgumentException("Album not found"));

        Set<Artist> artists = dto.artistIds().stream()
                .map(artistId -> artistRepository.findById(artistId)
                        .orElseThrow(() -> new IllegalArgumentException("Artist not found with ID: " + artistId)))
                .collect(Collectors.toSet());
        song.setAlbum(album);
        song.setArtists(artists);

        return songDtoMapper.apply(songRepository.save(song));


    }

    public Page<SongDto> getSongsByTitle(String title, Pageable pageable) {
        return songRepository.findByTitleContainingIgnoreCase(title,  pageable)
                .map(songDtoMapper);
    }

    public Page<SongDto> getSongsByArtistId(Long artistId,  Pageable pageable) {
        return songRepository.findSongsByArtist(artistId, pageable)
                .map(songDtoMapper);
    }

    @Transactional
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new IllegalArgumentException("Song not found");
        }
        songRepository.deleteById(id);
    }


}
