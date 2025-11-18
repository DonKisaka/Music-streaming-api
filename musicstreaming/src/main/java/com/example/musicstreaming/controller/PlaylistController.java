package com.example.musicstreaming.controller;

import com.example.musicstreaming.Dto.PlaylistDto;
import com.example.musicstreaming.service.PlaylistService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<PlaylistDto> createPlaylist(
            @Valid @RequestBody PlaylistDto dto
    ){
        PlaylistDto playlist = playlistService.createPlaylist(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(playlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(
            @PathVariable Long id
    ){
        PlaylistDto playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDto> updatePlaylist(
            @PathVariable Long id, @Valid @RequestBody PlaylistDto dto
    ) {
        PlaylistDto playlist = playlistService.updatePlaylist(id, dto);
        return ResponseEntity.ok(playlist);
    }



    @GetMapping
    public ResponseEntity<Page<PlaylistDto>> searchPlaylists(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        if (userId != null) {
            return ResponseEntity.ok(playlistService.getAllPlaylistsByUserId(userId, pageable));
        }

        if (name != null) {
            return ResponseEntity.ok(playlistService.getPlaylistByName(name, pageable));
        }

        return ResponseEntity.ok(playlistService.getAllPlaylists(pageable));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(
            @PathVariable Long id
    ){
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }
}
