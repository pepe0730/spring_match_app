package com.example.match_app.web;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MessageForm {
  @NotNull
  private String text;
  @NotNull
  private Integer receiveUserId;

}
