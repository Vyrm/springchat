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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppContextTest.class})
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
        Mockito.when(userDao.save(testUser)).thenReturn(testUser);

        User user = userHandler.addUser(testUser.getNickname(), testUser.getPassword());

        Assert.assertTrue(testUser.getNickname().equals(user.getNickname()));
        Assert.assertTrue(testUser.getPassword().equals(user.getPassword()));
    }
}
