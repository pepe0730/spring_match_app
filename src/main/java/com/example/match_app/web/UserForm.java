package com.example.match_app.web;

import lombok.Data;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Data
public class UserForm {
  @NotNull
  @Size(min = 1, max = 40)
  private String email;
  @NotNull
  private String password;
  @NotNull
  private String name;
  @NotNull
  @Size(min = 1, max = 3)
  private Integer age;
  @NotNull
  private String authority;
  
}