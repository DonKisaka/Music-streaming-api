package com.example.musicstreaming.repository;

import com.example.musicstreaming.model.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Playlist> findAllByUserId(Long userId, Pageable pageable);

    Playlist findByUserIdAndNameContainingIgnoreCase(Long userId, String name);



}
