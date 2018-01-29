package com.serhii.handler;

import com.serhii.context.AppContextTest;
import com.serhii.dao.UserDao;
import com.serhii.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppContextTest.class})
public class UserHandlerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserHandler userHandler;

    public User initData() {
        User user = new User();
        user.setPassword("test");
        user.setNickname("test");
        return user;
    }

    @Test
    public void testAddUser() {
        User testUser = initData();
       /* User userFromDB = userHandler.addUser(testUser.getNickname(), testUser.getPassword());
        Assert.assertTrue(testUser.getNickname().equals(userFromDB.getNickname()));
        Assert.assertTrue(testUser.getPassword().equals(userFromDB.getPassword()));*/
    }
}
