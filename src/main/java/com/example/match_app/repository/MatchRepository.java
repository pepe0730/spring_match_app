package com.example.match_app.repository;

import com.example.match_app.domain.Matches;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Matches, Integer> {

}
