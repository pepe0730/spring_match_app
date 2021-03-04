package com.example.match_app.Service;

import com.example.match_app.domain.Tag;
import com.example.match_app.domain.TagsRelations;
import com.example.match_app.domain.User;
import com.example.match_app.repository.TagRelatinsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagRelationsService {
  @Autowired
  TagRelatinsRepository tagRelatinsRepository;

  public TagsRelations create(Tag tag, User user) {
    TagsRelations tagsRelations = new TagsRelations();
    tagsRelations.setTag(tag);
    tagsRelations.setUser(user);
    return tagRelatinsRepository.save(tagsRelations);
  }

}
