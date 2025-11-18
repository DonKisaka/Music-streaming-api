package com.example.musicstreaming.service;

import com.example.musicstreaming.Dto.PlaylistDto;
import com.example.musicstreaming.mapper.PlaylistDtoMapper;
import com.example.musicstreaming.model.Playlist;
import com.example.musicstreaming.model.Song;
import com.example.musicstreaming.model.User;
import com.example.musicstreaming.repository.PlaylistRepository;
import com.example.musicstreaming.repository.SongRepository;
import com.example.musicstreaming.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final PlaylistDtoMapper playlistDtoMapper;

    public PlaylistService(PlaylistRepository playlistRepository, UserRepository userRepository, SongRepository songRepository, PlaylistDtoMapper playlistDtoMapper) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
        this.playlistDtoMapper = playlistDtoMapper;
    }

    @Transactional
    public PlaylistDto createPlaylist(PlaylistDto dto){
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Set<Song> songs = dto.songIds().stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() -> new RuntimeException("Song Not Found with ID: " + songId)))
                .collect(Collectors.toSet());

        Playlist playlist = Playlist.builder()
                .name(dto.name())
                .description(dto.description())
                .user(user)
                .songs(songs)
                .build();
        return playlistDtoMapper.apply(playlistRepository.save(playlist));

    }

    public PlaylistDto getPlaylistById(Long id){
        return playlistDtoMapper.apply(playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found")));
    }


    public Page<PlaylistDto> getAllPlaylists(Pageable pageable){
       return playlistRepository.findAll(pageable)
               .map(playlistDtoMapper);
    }

    public Page<PlaylistDto> getPlaylistByName(String name, Pageable pageable){
        return playlistRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(playlistDtoMapper);
    }

    public Page<PlaylistDto> getAllPlaylistsByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)){
            throw new IllegalArgumentException("User not found");
        }

        return playlistRepository.findAllByUserId(userId, pageable)
                .map(playlistDtoMapper);
    }

    @Transactional
    public PlaylistDto updatePlaylist(Long id, PlaylistDto dto){
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Playlist not found"));

        playlist.setName(dto.name());
        playlist.setDescription(dto.description());

        if (!playlist.getUser().getId().equals(dto.userId())){
            User newUser = userRepository.findById(dto.userId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            playlist.setUser(newUser);
        }

        Set<Song> songs = dto.songIds().stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() -> new IllegalArgumentException("Song not found with Id: " + songId)))
                .collect(Collectors.toSet());
        playlist.setSongs(songs);
        return playlistDtoMapper.apply(playlistRepository.save(playlist));
    }


    @Transactional
    public void deletePlaylist(Long id){
        if (!playlistRepository.existsById(id)){
            throw new IllegalArgumentException("Playlist not found");
        }
        playlistRepository.deleteById(id);
    }
}
