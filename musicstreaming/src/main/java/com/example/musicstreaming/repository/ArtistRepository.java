package com.example.musicstreaming.repository;

import com.example.musicstreaming.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    List<Artist> findByNameContainingIgnoreCase(String name);
    List<Artist> findByGenre(String genre);
    boolean existsByName(String name);
    Optional<Artist> findByname(String name);
}
