package com.serhii.handler;

import com.serhii.dao.UserDao;
import com.serhii.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserHandler {
    @Autowired
    private UserDao userDao;

    public User addUser(String nickname, String password) {
        User user = new User(nickname, password);
        return userDao.save(user);
    }

    public User getUser(String nickname) {
        return userDao.findByNickname(nickname);
    }

    public User delete(User user){
        return userDao.deleteUserByNickname(user.getNickname());
    }

}
