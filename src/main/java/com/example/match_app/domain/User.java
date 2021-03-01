package com.example.match_app.domain;

import java.sql.Timestamp;

import javax.persistence.*;

//import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "email", nullable = false, unique = true)
  private String email;
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "image")
  private byte[] image;
  @Column(name = "age", nullable = false)
  private Integer age;
  @Column(name = "profile")
  private String profile;
  @Column(name = "created_at", nullable = false)
  private Timestamp created_at;
  @Column(name = "updated_at", nullable = false)
  private Timestamp updated_at;
  @Column(name = "authority", nullable = false)
  private Integer authority;

}
