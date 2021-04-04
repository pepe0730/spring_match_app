package com.example.match_app.Service;

import com.example.match_app.domain.Image;
import com.example.match_app.domain.User;
import com.example.match_app.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// ロールバック
@Transactional
public class ImageService {

  @Autowired
  ImageRepository imageRepository;

  public Image findUserImage(Integer id) {
    return imageRepository.findUserImage(id);
  }

  public void updateImage(Integer user_id, String original_name, byte[] data) {
    imageRepository.setImage(user_id, original_name, data);
  }

  public Image create(Integer user_id, String original_name, byte[] data, User user) {
    Image image = new Image();
    image.setData(data);
    image.setOriginal_name(original_name);
    image.setUser_id(user_id);
    return imageRepository.save(image);
  }
}
