package com.example.musicstreaming.controller;

import com.example.musicstreaming.Dto.AlbumDto;
import com.example.musicstreaming.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@Valid @RequestBody AlbumDto albumDto) {
        AlbumDto album = albumService.createAlbum(albumDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(album);
    }


    @GetMapping
    public ResponseEntity<Page<AlbumDto>> getAllAlbums(Pageable pageable) {
        Page<AlbumDto> albums = albumService.getAllAlbums(pageable);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable Long id) {
        AlbumDto album = albumService.getAlbumById(id);
        return ResponseEntity.ok(album);
    }

    @GetMapping("/search/by-artist")
    public ResponseEntity<Page<AlbumDto>> getAlbumsByArtistId(@RequestParam Long artistId, Pageable pageable) {
        Page<AlbumDto> albums = albumService.getAlbumsByArtistId(artistId, pageable);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/search/by-title")
    public ResponseEntity<Page<AlbumDto>> getAlbumsByTitle(@RequestParam String title, Pageable pageable) {
        Page<AlbumDto> albums = albumService.getAlbumsByTitle(title, pageable);
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/search/by-release-year")
    public ResponseEntity<Page<AlbumDto>> getAlbumsByReleaseYear(@RequestParam Integer year, Pageable pageable) {
        Page<AlbumDto> albums = albumService.getAlbumByReleaseYear(year, pageable);
        return ResponseEntity.ok(albums);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumDto> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumDto albumDto) {
        AlbumDto album = albumService.updateAlbum(id, albumDto);
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
}
