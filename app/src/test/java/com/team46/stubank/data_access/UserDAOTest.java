package com.team46.stubank.data_access;

import com.team46.stubank.User;

import junit.framework.TestCase;

import org.junit.Assert;

public class UserDAOTest extends TestCase {

    public void testGetUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = userDAO.getUser(10);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setAccountID(10);
        testuser.setUserDetailsID(10);

        userDAO.insertUser(testuser);

        try {
           User user2 = userDAO.getUser(10);
            Assert.assertEquals(testuser.getUsername(), user2.getUsername());
            Assert.assertEquals(testuser.getPassword(), user2.getPassword());
            Assert.assertEquals(testuser.getAccountID(), user2.getAccountID());
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getAccountID());

            userDAO.deleteUser(testuser);
            //userDAO.deleteUser(user2);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }

    }

    public void testInsertUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = userDAO.getUser(10);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setAccountID(10);
        testuser.setUserDetailsID(10);

        userDAO.insertUser(testuser);

        try {
            User user2 = userDAO.getUser(10);
            Assert.assertEquals(testuser.getUsername(), user2.getUsername());
            Assert.assertEquals(testuser.getPassword(), user2.getPassword());
            Assert.assertEquals(testuser.getAccountID(), user2.getAccountID());
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getAccountID());

            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = userDAO.getUser(10);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setAccountID(10);
        testuser.setUserDetailsID(10);
        userDAO.insertUser(testuser);

        testuser.setUsername("NewUserName1");
        testuser.setPassword("Password321");
        testuser.setAccountID(11);
        testuser.setUserDetailsID(11);
        userDAO.updateUser(testuser);

        try {
            User updatedUser = userDAO.getUser(10);
            Assert.assertEquals(updatedUser.getUsername(), "NewUserName1");
            Assert.assertEquals(updatedUser.getPassword(), "Password321");
            Assert.assertEquals(updatedUser.getAccountID(), 11);
            Assert.assertEquals(updatedUser.getUserDetailsID(), 11);

            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testDeleteUser() {
        UserDAO userDAO = new UserDAO();

        User testuser = userDAO.getUser(10);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setAccountID(10);
        testuser.setUserDetailsID(10);

        userDAO.insertUser(testuser);
        userDAO.deleteUser(testuser);

        try {
            Assert.assertNull(userDAO.getUser(10));

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testInsertUserDetails(){
        UserDAO userDAO = new UserDAO();
        User testuser = userDAO.getUser(10);
        testuser.setUserDetailsID(1);
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setEmail("b9034879@newcastle.ac.uk");
        testuser.setPhoneNumber("077454322167");
        testuser.setDob("01/10/1999");

        userDAO.insertUserDetails(testuser);
        userDAO.deleteUser(testuser);

        try {
            User user2 = userDAO.getUser(10);
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getUserDetailsID());
            Assert.assertEquals(testuser.getFirstName(), user2.getFirstName());
            Assert.assertEquals(testuser.getLastName(), user2.getLastName());
            Assert.assertEquals(testuser.getEmail(), user2.getEmail());
            Assert.assertEquals(testuser.getPhoneNumber(), user2.getPhoneNumber());
            Assert.assertEquals(testuser.getDob(), user2.getDob());

        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void testGetUserDetails(){
        UserDAO userDAO = new UserDAO();
        User testuser = userDAO.getUser(10);
        testuser.setUserDetailsID(1);
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setEmail("b9034879@newcastle.ac.uk");
        testuser.setPhoneNumber("077454322167");
        testuser.setDob("01/10/1999");

        userDAO.insertUserDetails(testuser);
        userDAO.deleteUser(testuser);

        try {
            User user2 = userDAO.getUser(10);
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getUserDetailsID());
            Assert.assertEquals(testuser.getFirstName(), user2.getFirstName());
            Assert.assertEquals(testuser.getLastName(), user2.getLastName());
            Assert.assertEquals(testuser.getEmail(), user2.getEmail());
            Assert.assertEquals(testuser.getPhoneNumber(), user2.getPhoneNumber());
            Assert.assertEquals(testuser.getDob(), user2.getDob());

        } catch (final NullPointerException e) {
            e.printStackTrace();
        }
    }
}