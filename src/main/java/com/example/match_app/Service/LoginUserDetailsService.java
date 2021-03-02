package com.example.match_app.Service;

import com.example.match_app.domain.User;
import com.example.match_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  // usernameをオーバーライド 検索はemail
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findLoginUser(email);
    if (user == null) {
      throw new UsernameNotFoundException("The requested user is not found");
    }
    return new LoginUserDetails(user);
  }

}
