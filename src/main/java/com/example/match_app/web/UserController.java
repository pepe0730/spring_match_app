package com.example.match_app.web;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.example.match_app.Service.FollowService;
import com.example.match_app.Service.ImageService;
import com.example.match_app.Service.LoginUserDetails;
import com.example.match_app.Service.MatchService;
import com.example.match_app.Service.UserService;
import com.example.match_app.domain.Image;
import com.example.match_app.domain.Matches;
import com.example.match_app.domain.User;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//返り値は遷移する画面の名前
@Controller
// マッピング
@RequestMapping("users")

public class UserController {
  @Autowired
  UserService userService;
  @Autowired
  ImageService imageService;
  @Autowired
  FollowService followService;
  @Autowired
  MatchService matchService;

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

  @GetMapping(path = "showOtherUser/{userId}")
  String showOtherUser(@PathVariable("userId") String id, Model model) {
    Integer user_id = Integer.parseInt(id);
    User user = userService.findOne(user_id);
    Image image = user.getImage();
    if (image != null) {
      changeBase64String(image);
      model.addAttribute("image", image);
    }
    model.addAttribute("user", user);
    return "users/showOtherUser";
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
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    user.setUpdated_at(currentTime);
    userService.update(user);

    return "redirect:show";
  }

  // いいねボタンクリック後
  @PostMapping(value = "follow")
  String follow(@RequestParam Integer receiveId, Principal principal, RedirectAttributes redirectAttributes) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    User loginUser = LoginUser.getUser();
    User receiveUser = userService.findOne(receiveId);
    // followService
    followService.followCreate(receiveUser, loginUser);
    try {
      Long countMutual = followService.checkMutualFollow(receiveUser, loginUser);
      // 相互フォロー確認
      if (countMutual != 0) {
        matchService.matchCreate(loginUser, receiveUser);
        // リダイレクト先にマッチした相手のオブジェクトを渡す
        redirectAttributes.addFlashAttribute("opponent", receiveUser);
        return "redirect:matchSuccess";
      }
      return "redirect:";
    } catch (NullPointerException e) {
      return "redirect:";
    }
  }

  @GetMapping(path = "matchSuccess")
  String matchSuccess(Model model) {
    User user = (User) model.getAttribute("opponent");
    Image image = user.getImage();
    if (image != null) {
      changeBase64String(image);
      model.addAttribute("image", image);
    }
    model.addAttribute("user", user);
    return "users/matchSuccess";
  }

  @GetMapping(path = "messageList")
  String messageList(Model model, Principal principal) {
    Authentication auth = (Authentication) principal;
    LoginUserDetails LoginUser = (LoginUserDetails) auth.getPrincipal();
    User loginUser = LoginUser.getUser();
    List<Matches> matches = matchService.getMutualUsers(loginUser);
    List<User> mulualUsers = new ArrayList<User>();
    User mulualUser = new User();
    for (Matches match : matches) {
      if (match.getUser1().getId() == loginUser.getId()) {
        mulualUser = match.getUser2();
      } else if (match.getUser2().getId() == loginUser.getId()) {
        mulualUser = match.getUser1();
      }
      if (mulualUser.getImage() != null) {
        mulualUser.setImage(changeBase64String(mulualUser.getImage()));
      }
      mulualUsers.add(mulualUser);
    }
    model.addAttribute("matchUsers", mulualUsers);
    return "users/messageList";
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
