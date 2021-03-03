package com.example.match_app.repository;

import com.example.match_app.domain.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}
