package com.serhii.handler;

import com.serhii.dao.UserDao;
import com.serhii.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserHandlerTest {
    private User testUser;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserHandler userHandler;

    @Before
    public void initData() {
        testUser = new User();
        testUser.setPassword("test");
        testUser.setNickname("test");
    }

    @Test
    public void testAddUser() {
        User user = userHandler.addUser(testUser);
        Assert.assertTrue(testUser.getNickname().equals(user.getNickname()));
        Assert.assertTrue(testUser.getPassword().equals(user.getPassword()));
    }

    @Test
    public void testGetUser() {
        Mockito.when(userDao.findByNickname(testUser.getNickname())).thenReturn(testUser);
        Assert.assertTrue(userHandler.getUser(testUser.getNickname()).equals(testUser));
    }
}
