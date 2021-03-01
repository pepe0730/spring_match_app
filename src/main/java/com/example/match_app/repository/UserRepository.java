package com.example.match_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import com.example.match_app.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE u.id != :id")
  List<User> findUsers(Integer id);
}
