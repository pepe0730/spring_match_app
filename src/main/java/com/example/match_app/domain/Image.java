package com.example.match_app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "user_id", nullable = false)
  private Integer user_id;
  @Column(name = "original_name", nullable = false)
  private String original_name;
  @Column(name = "data")
  private byte[] data;
  @Column(name = "extension")
  private String extension;
  @Column(name = "base64string")
  private String base64string;
}
