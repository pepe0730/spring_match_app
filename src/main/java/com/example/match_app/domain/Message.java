package com.example.match_app.domain;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "send_user", nullable = false)
  private User sendUser;
  @ManyToOne
  @JoinColumn(name = "receive_user", nullable = false)
  private User receiveUser;
  @Column(name = "text", nullable = false)
  private String text;
  @Column(name = "created_at")
  private Timestamp created_at;
  @Column(name = "delete_flag")
  private Integer delete_flag;
}
