package com.example.match_app.repository;

import java.util.List;

import com.example.match_app.domain.Matches;
import com.example.match_app.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MatchRepository extends JpaRepository<Matches, Integer> {

  @Query("SELECT m FROM Matches m WHERE m.user1 = :user or m.user2 = :user")
  List<Matches> getMatcheUsers(User user);
}
