package com.example.match_app.web;

import java.util.List;

import com.example.match_app.Service.UserService;
import com.example.match_app.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//返り値は遷移する画面の名前
@Controller
// マッピング
@RequestMapping("users")
public class UserController {
  @Autowired
  UserService userService;

  @GetMapping // Modelは画面に値を渡すオブジェクト
  String allList(Model model) {
    // 今はログインユーザなしなので全件取得
    List<User> users = userService.findAll();
    model.addAttribute("users", users);
    return "users/index";
  }

}
