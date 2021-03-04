package com.example.match_app.Service;

import java.util.List;

import com.example.match_app.domain.Tag;
import com.example.match_app.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// ロールバック
@Transactional
public class TagService {
  @Autowired
  TagRepository tagRepository;

  public List<Tag> findAll() {
    List<Tag> tags = tagRepository.findAll();
    return tags;
  }

  public Tag findOne(Integer id) {
    return tagRepository.findOne(id);
  }

}
