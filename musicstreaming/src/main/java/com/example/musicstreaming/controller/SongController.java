package com.example.musicstreaming.controller;

import com.example.musicstreaming.Dto.SongDto;
import com.example.musicstreaming.service.SongService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public ResponseEntity<SongDto> createSong(@Valid @RequestBody SongDto songDto) {
        SongDto song = songService.createSong(songDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(song);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable long id) {
        SongDto song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping
    public ResponseEntity<Page<SongDto>> getAllSongs(Pageable pageable) {
        Page<SongDto> songs = songService.getAllSongs(pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search/by-album")
    public ResponseEntity<Page<SongDto>> getSongsByAlbumId(
            @RequestParam long albumId, Pageable pageable
    ) {
        Page<SongDto> songs = songService.getSongsByAlbumId(albumId, pageable);
        return ResponseEntity.ok(songs);
    }



    @PutMapping("/{id}")
    public ResponseEntity<SongDto> updateSong(
            @PathVariable Long id, @Valid @RequestBody SongDto songDto
    ) {
        SongDto song = songService.updateSong(id, songDto);
        return ResponseEntity.ok(song);
    }

    @GetMapping("/search/by-title")
    public ResponseEntity<Page<SongDto>> getSongByTitle(
           @RequestParam String title, Pageable pageable
    ) {
        Page<SongDto> songs = songService.getSongsByTitle(title, pageable);
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search/by-artist")
    public ResponseEntity<Page<SongDto>> getSongsByArtistId(
            @RequestParam Long artistId, Pageable pageable
    ) {
        Page<SongDto> songs = songService.getSongsByArtistId(artistId, pageable);
        return ResponseEntity.ok(songs);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.noContent().build();
    }





}
