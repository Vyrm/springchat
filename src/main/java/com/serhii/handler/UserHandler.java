package com.serhii.handler;

import com.serhii.dao.UserDao;
import com.serhii.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    @Autowired
    private UserDao userDao;

    public User addUser(User user) {
        return userDao.save(user);
    }

    public User getUser(String nickname) {
        return userDao.findByNickname(nickname);
    }

}
