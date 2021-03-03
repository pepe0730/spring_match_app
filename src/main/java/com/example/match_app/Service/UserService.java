package com.example.match_app.Service;

import java.util.List;

import com.example.match_app.domain.User;
import com.example.match_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// ロールバック
@Transactional
public class UserService {
  @Autowired
  UserRepository userRepository;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public List<User> findUsers(Integer id) {
    return userRepository.findUsers(id);
  }

  public User create(User user) {
    return userRepository.save(user);
  }
}
