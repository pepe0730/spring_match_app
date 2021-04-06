package com.example.match_app.repository;

import com.example.match_app.domain.FollowRelations;
import com.example.match_app.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<FollowRelations, Integer> {
  @Query("SELECT COUNT(f) FROM FollowRelations f WHERE receive = :receive AND active = :active")
  Long checkMutualFollow(User receive, User active);
}
