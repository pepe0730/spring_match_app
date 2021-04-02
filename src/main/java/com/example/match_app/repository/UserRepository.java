package com.example.match_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

import com.example.match_app.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE u.id != :id")
  List<User> findUsers(Integer id);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  User findLoginUser(String email);

  @Query("SELECT u FROM User u WHERE u.id = :id")
  User findOne(Integer id);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  User findOne(String email);

  @Query("UPDATE User u SET u.name = :name, u.age = :age, u.profile = :profile, u.updated_at = :updated_at WHERE u.id = :id")
  User updateUser(Integer id, String name, Integer age, String profile, Timestamp updated_at);
}
