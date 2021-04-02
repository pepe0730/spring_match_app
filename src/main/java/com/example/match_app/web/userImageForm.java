package com.example.match_app.web;

import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class userImageForm {
  @NotNull
  private MultipartFile image;
}
