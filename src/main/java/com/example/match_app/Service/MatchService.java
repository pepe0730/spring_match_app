package com.example.match_app.Service;

import java.util.List;

import com.example.match_app.domain.Matches;
import com.example.match_app.domain.User;
import com.example.match_app.repository.MatchRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MatchService {
  @Autowired
  MatchRepository matchRepository;

  public void matchCreate(User user1, User user2) {
    Matches matches = new Matches();
    matches.setUser1(user1);
    matches.setUser2(user2);
    matchRepository.save(matches);
  }

  public List<Matches> getMutualUsers(User user) {
    return matchRepository.getMatcheUsers(user);
  }
}
