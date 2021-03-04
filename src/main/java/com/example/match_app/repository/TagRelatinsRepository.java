package com.example.match_app.repository;

import com.example.match_app.domain.TagsRelations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRelatinsRepository extends JpaRepository<TagsRelations, Integer> {

}
