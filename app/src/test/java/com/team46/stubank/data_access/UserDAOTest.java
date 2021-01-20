package com.team46.stubank.data_access;

import com.team46.stubank.User;

import junit.framework.TestCase;

import org.junit.Assert;

public class UserDAOTest extends TestCase {

    public void testGetUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();
        testuser.setUserID(0);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("01/10/1999");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);
        //new id made

        userDAO.getUser(testuser.getUserID());
        //new random ids are added back to user object

        User user2 = userDAO.getUser(testuser.getUserID());
        // creates new object with same ids as test user

        try {

           Assert.assertEquals(user2.getUserID(), testuser.getUserID());
           Assert.assertEquals(user2.getAccountID(), testuser.getAccountID());
           Assert.assertEquals(user2.getUserDetailsID(), testuser.getUserDetailsID());

           // userDAO.deleteUser(testuser);
            userDAO.deleteUser(user2);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }

    }

    public void testInsertUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();
        //User testuser = userDAO.getUser(10);
        testuser.setUserID(0);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("01/10/1999");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);

        try {
            User user2 = userDAO.getUser(testuser.getUserID());
            Assert.assertEquals(user2.getUserID(), testuser.getUserID());
            Assert.assertEquals(user2.getAccountID(), testuser.getAccountID());
            Assert.assertEquals(testuser.getUserDetailsID(), testuser.getUserDetailsID());

            userDAO.deleteUser(user2);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setUserID(0);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("01/10/1999");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);

        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("01/10/1999");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.updateUser(testuser);

        try {
            User updatedUser = userDAO.getUser(1);
            Assert.assertEquals(updatedUser.getUsername(), "NewUserName1");
            Assert.assertEquals(updatedUser.getAccountID(), "2");
            Assert.assertEquals(updatedUser.getUserDetailsID(), 2);

            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setUserID(0);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("01/10/1999");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);
        userDAO.getUser(testuser.getUserID());
        userDAO.deleteUser(testuser);

        try {
            Assert.assertNull(userDAO.getUser(testuser.getUserID()));

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }


    public void testGetUserDetails(){
        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setEmail("b9034879@newcastle.ac.uk");
        testuser.setPhoneNumber("077454322167");
        testuser.setDob("01/10/1999");

        userDAO.insertUser(testuser);
        //userDAO.getUser(testuser.getUserID());
        userDAO.getUserDetails(testuser.getUserDetailsID());

        try {

            User user2 = userDAO.getUser(testuser.getUserID());
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getUserDetailsID());
            Assert.assertEquals(testuser.getFirstName(), user2.getFirstName());
            Assert.assertEquals(testuser.getLastName(), user2.getLastName());
            Assert.assertEquals(testuser.getEmail(), user2.getEmail());
            Assert.assertEquals(testuser.getPhoneNumber(), user2.getPhoneNumber());
            Assert.assertEquals(testuser.getDob(), user2.getDob());
            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}