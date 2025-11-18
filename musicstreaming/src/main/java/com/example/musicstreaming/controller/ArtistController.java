package com.example.musicstreaming.controller;

import com.example.musicstreaming.Dto.ArtistDto;
import com.example.musicstreaming.service.ArtistService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping
    public ResponseEntity<ArtistDto> createArtist(
            @Valid @RequestBody ArtistDto input
    ){
        ArtistDto artist = artistService.createArtist(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(artist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtistById(
            @PathVariable Long id
    ){
        ArtistDto artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<ArtistDto>> getAllArtists(){
        List<ArtistDto> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArtistDto>> getArtistsByName(
            @RequestParam String name
    ){
        List<ArtistDto> artists = artistService.getArtistsByName(name);
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/genre")
    public ResponseEntity<List<ArtistDto>> getArtistsByGenre(
            @RequestParam String genre
    ){
        List<ArtistDto> artists = artistService.getArtistsByGenre(genre);
        return ResponseEntity.ok(artists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistDto> updateArtist(
            @PathVariable Long id,
            @Valid @RequestBody ArtistDto input
    ){
        ArtistDto artist = artistService.updateArtist(id, input);
        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(
            @PathVariable Long id
    ){
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

}
