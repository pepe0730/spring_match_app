package com.example.match_app.Service;

import java.util.List;

import com.example.match_app.domain.Message;
import com.example.match_app.domain.User;
import com.example.match_app.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
  @Autowired
  MessageRepository messageRepository;

  public List<Message> getMessages(User receiveUser, User loginUser) {
    return messageRepository.getMessages(receiveUser, loginUser);
  }

}
