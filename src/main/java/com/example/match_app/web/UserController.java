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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
  String allList(Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    List<User> users = userService.findUsers(LoginUser.getUser().getId());
    for (User user : users) {
      if (user.getImage() != null) {
        user.setImage(changeBase64String(user.getImage()));
      }
    }
    model.addAttribute("users", users);
    return "users/index";
  }

  @GetMapping(path = "show")
  String show(Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    Image image = imageService.findUserImage(LoginUser.getUser().getId());
    if (image != null) {
      changeBase64String(image);
      model.addAttribute("image", image);
    }
    // model.addAttribute("key", principal.getName());
    return "users/show";
  }

  @GetMapping(path = "edit")
  String edit(Principal principal, UserForm form, userImageForm imageForm) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    Image image = imageService.findUserImage(LoginUser.getUser().getId());
    if (image != null) {
      // Base64変換
      changeBase64String(image);
      BeanUtils.copyProperties(image, imageForm);
    } else {
      Image newImage = new Image();
      BeanUtils.copyProperties(imageForm, newImage);
    }
    BeanUtils.copyProperties(LoginUser.getUser(), form);
    return "users/edit";
  }

  // ユーザアイコン登録
  @PostMapping(path = "imageConfig")
  String imageCreate(@Validated userImageForm imageForm, BindingResult result, Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    User loginUser = LoginUser.getUser();
    if (result.hasErrors()) {
      return "redirect:edit";
    }
    try {
      String original_name = imageForm.getImage().getOriginalFilename();
      byte[] data = imageForm.getImage().getBytes();
      Integer user_id = LoginUser.getUser().getId();
      if (loginUser.getImage() == null) {
        // cerate
        Image uploadImage = imageService.create(user_id, original_name, data, loginUser);
        // user image 連携(初回登録時のみ)
        loginUser.setImage(uploadImage);
        userService.defaultUpdate(loginUser);
      } else {
        // update image dataの更新のみ行う。
        imageService.updateImage(user_id, original_name, data);
      }
    } catch (IOException e) {
    }
    return "redirect:show";
  }

  // ユーザ情報更新
  @PostMapping(value = "update")
  String update(@Validated UserForm form, BindingResult result, Model model, Principal principal) {
    if (result.hasErrors()) {
      return "redirect: edit";
    }
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    User user = LoginUser.getUser();
    BeanUtils.copyProperties(form, user);
    userService.update(user.getId(), user.getName(), user.getProfile(), user.getId());
    return "redirect:show";
  }

  // Base64変換メソッド
  public Image changeBase64String(Image image) {
    // 拡張子取得
    image.setExtension(
        image.getOriginal_name().substring(image.getOriginal_name().length() - 4, image.getOriginal_name().length()));
    // byte → Base64に変換(java.util)
    image.setBase64string(Base64.getEncoder().encodeToString(image.getData()));
    // byte削除
    image.setData(null);
    return image;
  }

}
