package com.example.match_app.Service;

import com.example.match_app.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MessageService {
  @Autowired
  MessageRepository messageRepository;

}
