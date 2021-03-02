package com.example.match_app.Service;

import com.example.match_app.domain.User;

import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Data;

@Data
public class LoginUserDetails extends org.springframework.security.core.userdetails.User {
  private final User user;

  public LoginUserDetails(User user) {
    // ユーザ認証情報をLoginUserDetailsに格納。authorityUtilsはロール(権限のまとまり)
    super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_USER"));
    this.user = user;

  }

}
