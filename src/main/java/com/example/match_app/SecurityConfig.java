package com.example.match_app;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

//Security基本設定
@EnableWebSecurity
// セキュリティデフォルト設定を継承
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/webjars/**", "/css/**", "/image/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/loginForm").permitAll().anyRequest().authenticated().and().formLogin()
        .loginProcessingUrl("/login").loginPage("/loginForm").failureUrl("/loginForm?error")
        .defaultSuccessUrl("/users", true).usernameParameter("email").passwordParameter("password").and().logout()
        .logoutSuccessUrl("/loginForm");
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new Pbkdf2PasswordEncoder();
  }

}
