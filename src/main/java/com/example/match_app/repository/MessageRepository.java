package com.example.match_app.repository;

import com.example.match_app.domain.Message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {
  

}
