package com.example.match_app.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

import com.example.match_app.Service.ImageService;
import com.example.match_app.Service.LoginUserDetails;
import com.example.match_app.Service.UserService;
import com.example.match_app.domain.Image;
import com.example.match_app.domain.User;
import com.example.match_app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//返り値は遷移する画面の名前
@Controller
// マッピング
@RequestMapping("users")
public class UserController {
  @Autowired
  UserService userService;
  @Autowired
  ImageService imageService;

  @GetMapping // Modelは画面に値を渡すオブジェクト
  String allList(Model model) {
    // 今はログインユーザなしなので全件取得
    List<User> users = userService.findAll();
    model.addAttribute("users", users);
    return "users/index";
  }

  @GetMapping(path = "show")
  String show(Model model, Principal principal) {
    // Authentication principal =
    // SecurityContextHolder.getContext().getAuthentication();
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    Image image = imageService.findUserImage(LoginUser.getUser().getId());
    if (image != null) {
      // 拡張子を取得
      image.setExtension(
          image.getOriginal_name().substring(image.getOriginal_name().length() - 4, image.getOriginal_name().length()));
      // byte → Base64に変換(java.util)
      image.setBase64string(Base64.getEncoder().encodeToString(image.getData()));
      // byte削除
      image.setData(null);
      model.addAttribute("image", image);
    }
    // model.addAttribute("key", principal.getName());
    return "users/show";
  }

  @GetMapping(path = "edit")
  String edit(Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    model.addAttribute("user", LoginUser);
    model.addAttribute("userImageForm", new userImageForm());
    return "users/edit";
  }

  // ユーザアイコン登録
  @PostMapping(path = "imageConfig")
  String imageCreate(@Validated userImageForm imageForm, BindingResult result, Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    if (result.hasErrors()) {
      return "redirect:edit";
    }
    try {
      String original_name = imageForm.getImage().getOriginalFilename();
      byte[] data = imageForm.getImage().getBytes();
      Integer user_id = LoginUser.getUser().getId();
      imageService.updateImage(user_id, original_name, data);
    } catch (IOException e) {
    }
    /*
     * String imageCreate(@Validated userImageForm imageForm, BindingResult result,
     * Model model) { if (result.hasErrors()) { return edit(imageForm.getUserId(),
     * model); } try { System.out.println(imageForm.getImage().getBytes());
     * System.out.println(imageForm.getUserId()); User user =
     * userService.findOne(Integer.parseInt(imageForm.getUserId()));
     * 
     * byte[] ImageBinary = new byte[(int) (imageForm.getImage().getSize())];
     * ImageBinary = imageForm.getImage().getBytes(); user.setImage(ImageBinary);
     * userService.create(user);
     * 
     * } catch (IOException e) { }
     */

    /* StringBuffer data = new StringBuffer(); */
    return "redirect:show";
  }

}
