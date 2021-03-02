package com.example.match_app.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignUpController {
  @GetMapping
  String signup() {
    return "signup/new";
  }

}
