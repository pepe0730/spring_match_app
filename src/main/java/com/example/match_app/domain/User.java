package com.example.match_app.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

//import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@ToString(exclude = "followed_tags")
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
  @Column(name = "age")
  private Integer age;
  @Column(name = "profile")
  private String profile;
  @Column(name = "created_at", nullable = false)
  private Timestamp created_at;
  @Column(name = "updated_at", nullable = false)
  private Timestamp updated_at;
  @Column(name = "authority", nullable = false)
  private String authority;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  private List<TagsRelations> followed_tags;
}
