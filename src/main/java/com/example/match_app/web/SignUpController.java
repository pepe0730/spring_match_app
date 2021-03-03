package com.example.match_app.web;

import java.sql.Timestamp;
import java.util.List;

import com.example.match_app.Service.TagService;
import com.example.match_app.Service.UserService;
import com.example.match_app.domain.Tag;
import com.example.match_app.domain.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("signup")
public class SignUpController {
  @Autowired
  UserService userService;
  @Autowired
  TagService tagService;

  @ModelAttribute
  UserForm setUpForm() {
    return new UserForm();
  }

  @GetMapping(path = "new")
  String signup(Model model) {
    return "signup/new";
  }

  @GetMapping(path = "getTag")
  String getTag(Model model) {
    List<Tag> tags = tagService.findAll();
    model.addAttribute("tags", tags);
    return "signup/getTag";
  }

  @PostMapping(path = "create")
  String create(@Validated UserForm form, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return signup(model);
    }
    User user = new User();
    BeanUtils.copyProperties(form, user);
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    user.setCreated_at(currentTime);
    user.setUpdated_at(currentTime);
    userService.create(user);
    return "redirect:getTag";
  }

}
