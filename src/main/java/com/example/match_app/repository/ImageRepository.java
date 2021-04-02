package com.example.match_app.repository;

import com.example.match_app.domain.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {
  @Query("SELECT i FROM Image i WHERE i.user_id = :id")
  Image findUserImage(Integer id);

  @Modifying
  @Query("UPDATE Image i SET i.original_name = :original_name, i.data = :data where i.user_id = :user_id")
  Integer setImage(Integer user_id, String original_name, byte[] data);
}
