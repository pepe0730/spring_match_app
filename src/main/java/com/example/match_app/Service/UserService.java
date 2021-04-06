package com.example.match_app.Service;

import java.sql.Timestamp;
import java.util.List;

import com.example.match_app.domain.User;
import com.example.match_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// ロールバック
@Transactional
public class UserService {
  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> findUsers(Integer id) {
    return userRepository.findUsers(id);
  }

  public User findOne(Integer id) {
    return userRepository.findOne(id);
  }

  public User getLoginUser(String email) {
    return userRepository.findLoginUser(email);
  }

  public User create(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User defaultUpdate(User user) {
    return userRepository.save(user);
  }

  // User 基本情報 update
  public Integer update(Integer id, String name, String profile, Integer age) {
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    return userRepository.updateUser(id, name, age, profile, currentTime);
  }
}
