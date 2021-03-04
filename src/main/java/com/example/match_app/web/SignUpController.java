package com.example.match_app.web;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import com.example.match_app.Service.TagRelationsService;
import com.example.match_app.Service.TagService;
import com.example.match_app.Service.UserService;
import com.example.match_app.domain.Tag;

import com.example.match_app.domain.User;

import org.hibernate.boot.model.source.internal.hbm.ModelBinder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("signup")
public class SignUpController {
  @Autowired
  UserService userService;
  @Autowired
  TagService tagService;
  @Autowired
  TagRelationsService tagRelationsService;

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
    System.out.println(model.getAttribute("id"));
    /* リダイレクト元からユーザIDを受け取る */
    model.addAttribute("user_id", model.getAttribute("id"));
    model.addAttribute("tags", tags);
    model.addAttribute("setTags", new SetTags());
    return "signup/getTag";
  }

  @PostMapping(path = "create")
  String create(@Validated UserForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return signup(model);
    }
    User user = new User();
    BeanUtils.copyProperties(form, user);
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    user.setCreated_at(currentTime);
    user.setUpdated_at(currentTime);
    userService.create(user);
    /* 仮登録ユーザのIDをリダイレクト先へflashパラメータで送信 */
    redirectAttributes.addFlashAttribute("id", user.getId());
    return "redirect:getTag";
  }

  @PostMapping(path = "tagRelated")
  String tagRelated(@ModelAttribute SetTags tags, Model model) {
    if (tags.getTagIds().size() == 0) {
      return "redirect:../loginForm";
    }
    Iterator it = tags.getTagIds().iterator();
    Integer user_id = Integer.parseInt(tags.getUserId());
    User user = userService.findOne(user_id);
    Tag tag = new Tag();
    while (it.hasNext()) {
      /* tagRelationsService.create((Integer)it.next(), user_id); */
      tag = tagService.findOne((Integer) it.next());
      tagRelationsService.create(tag, user);
    }
    return "redirect:../loginForm";
  }
}
