package com.example.match_app.Service;

import com.example.match_app.domain.FollowRelations;
import com.example.match_app.domain.User;
import com.example.match_app.repository.FollowRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// ロールバック
@Transactional
public class FollowService {

  @Autowired
  FollowRepository followRepository;

  public void followCreate(User receiveUser, User loginUser) {
    FollowRelations followRelations = new FollowRelations();
    followRelations.setReceiveUser(receiveUser);
    followRelations.setActiveUser(loginUser);
    followRepository.save(followRelations);
  }

  public Long checkMutualFollow(User active, User receive) {
    return followRepository.checkMutualFollow(receive, active);
  }

}
