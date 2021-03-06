package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void a_findUserById()
    {
        assertEquals("cinnamon",
            userService.findUserById(7)
                .getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void aa_findUserByIdFail()
    {
        assertEquals("barnbarn",
            userService.findUserById(777)
                .getUsername());
    }

    @Test
    public void b_findByNameContaining()
    {
        assertEquals(1,
            userService.findByNameContaining("cinna")
                .size());
    }

    @Test
    public void c_findAll()
    {
        assertEquals(5,
            userService.findAll()
                .size());
        // have 5 named users and 25 faker users generated by a commented out for loop
    }

    @Test
    public void d_delete()
    {
        userService.delete(4);
        assertEquals(4,
            userService.findAll()
                .size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void da_deleteFail()
    {
        userService.delete(777);
        assertEquals(4,
            userService.findAll()
                .size());
    }

    @Test
    public void e_findByName()
    {
        assertEquals("misskitty",
            userService.findByName("misskitty")
                .getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ea_findByNameFail()
    {
        assertEquals("test admin",
            userService.findByName("xXxadminxXx")
                .getUsername());
    }

    @Test
    public void f_save()
    {
        User newUser = new User("test one",
            "pass one",
            "myemail@email");

        User addUser = userService.save(newUser);
        assertNotNull(addUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals(addUser.getUsername(),
            foundUser.getUsername());
    }

    @Test
    public void g_update()
    {
        User newUser = new User("test two",
            "pass two",
            "myemail@email");

        User updateUser = userService.update(newUser,
            11);
        assertEquals("pass two",
            updateUser.getPassword());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ga_updateFail()
    {
        User newUser = new User("test two",
            "pass two",
            "myemail@email");

        User updateUser = userService.update(newUser,
            777);
        assertEquals("pass two",
            updateUser.getPassword());
    }

    @Test
    public void h_deleteAll()
    {
    }
}