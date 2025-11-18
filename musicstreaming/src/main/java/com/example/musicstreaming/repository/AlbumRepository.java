package com.example.musicstreaming.repository;

import com.example.musicstreaming.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Album findByArtistIdAndTitle(Long artistId, String title);
    Page<Album> findAllByArtistId(Long artistId, Pageable pageable);
    @Query("select a from Album a where a.releaseYear = ?1")
    Page<Album> findByReleaseYear(Integer releaseYear, Pageable pageable);
}
