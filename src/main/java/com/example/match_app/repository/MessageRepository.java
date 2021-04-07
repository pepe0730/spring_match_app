package com.example.match_app.repository;

import java.util.List;

import com.example.match_app.domain.Message;
import com.example.match_app.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MessageRepository extends JpaRepository<Message, Integer> {

  @Query("SELECT m FROM Message m WHERE (m.sendUser = :loginUser AND m.receiveUser = :receiveUser) OR (m.sendUser = :receiveUser AND m.receiveUser = :loginUser)")
  List<Message> getMessages(User receiveUser, User loginUser);

}
