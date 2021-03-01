package com.example.match_app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "m_emp")
public class Employee {
  @Id
  @Column(name = "empno")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String empname;

}
