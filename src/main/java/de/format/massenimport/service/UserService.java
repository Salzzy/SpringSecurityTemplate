package de.format.massenimport.service;

import de.format.massenimport.entity.User;
import de.format.massenimport.entity.UserInfo;

import java.util.List;

public interface UserService {

    void save(User user);

    void saveUserInfo(User user, UserInfo userinfo);

    void saveNormal(User user);

    User findByUsername(String username);

    List<User> findAll();

}
