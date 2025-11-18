package com.example.musicstreaming.repository;

import com.example.musicstreaming.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SongRepository extends JpaRepository<Song, Long> {
    Page<Song> findAllByAlbumId(Long albumId, Pageable pageable);

    Page<Song> findByTitleContainingIgnoreCase(String title,  Pageable pageable);

    @Query("select s from Song s where s.durationInSeconds > :duration")
    Page<Song> findLongerThan(@Param("duration") Integer duration,  Pageable pageable);

    @Query("SELECT DISTINCT s FROM Song s JOIN s.artists a WHERE a.id = :artistId")
    Page<Song> findSongsByArtist(@Param("artistId") Long artistId,  Pageable pageable);
}
