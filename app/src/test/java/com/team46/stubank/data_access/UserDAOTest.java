package com.team46.stubank.data_access;

import com.team46.stubank.User;

import junit.framework.TestCase;

import org.junit.Assert;

import java.util.ArrayList;

public class UserDAOTest extends TestCase {

    public void testGetUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();
        testuser.setUserID(0);
        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("2012-05-03");
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

        testuser.setUserID(0);
        testuser.setUsername("SamS123");
        testuser.setPassword("Password123");
        testuser.setDob("2012-05-03");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Sam");
        testuser.setLastName("Smith");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);

        try {
            User user2 = userDAO.getUser(testuser.getUserID());
            Assert.assertEquals(user2.getUserID(), testuser.getUserID());
            Assert.assertEquals(user2.getAccountID(), testuser.getAccountID());
            Assert.assertEquals(testuser.getUserDetailsID(), testuser.getUserDetailsID());

            //userDAO.deleteUser(user2);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testUpdateUser() {
        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setUsername("NewTester");
        testuser.setPassword("Password123");
        testuser.setDob("2012-05-03");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);
        //generates ids

        userDAO.getUser(testuser.getUserID());
        //adds new ids to object

        testuser.setUsername("updatedName");
        testuser.setUserID(21);
        testuser.setUserDetailsID(21);
        testuser.setAccountID("21");

        userDAO.updateUser(testuser);

        userDAO.getUser(testuser.getUserID());

        try {

          User user2 = userDAO.getUser(testuser.getUserID());

            Assert.assertEquals(user2.getUsername(), testuser.getUsername());
            Assert.assertEquals(user2.getUserID(), testuser.getUserID());
            Assert.assertEquals(user2.getAccountID(), testuser.getAccountID());
            Assert.assertEquals(user2.getUserDetailsID(), testuser.getUserDetailsID());

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
        testuser.setDob("2012-05-03");
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



    public void testUpdateUserDetails(){

        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("2012-05-03");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);
        //new id made
        userDAO.getUser(testuser.getUserID());
        //new random ids are added back to user object

        testuser.setDob("2012-05-03");
        testuser.setEmail("harryalexander2@gmail.com");
        testuser.setFirstName("James");
        testuser.setLastName("Brown");
        testuser.setPhoneNumber("0722222222221");

        userDAO.updateUserDetails(testuser);
        //new attributes from object added to user in db

        userDAO.getUserDetails(testuser.getUserDetailsID());
        // user details from user in db readded to user object

        try {

            User user2 = userDAO.getUserDetails(testuser.getUserDetailsID());

            Assert.assertEquals(testuser.getFirstName(), user2.getFirstName());
            Assert.assertEquals(testuser.getLastName(), user2.getLastName());
            Assert.assertEquals(testuser.getEmail(), user2.getEmail());
            Assert.assertEquals(testuser.getPhoneNumber(), user2.getPhoneNumber());
            //Assert.assertEquals(testuser.getDob(), user2.getDob());
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getUserDetailsID());
            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }




    public void testGetUserDetails(){
        UserDAO userDAO = new UserDAO();
        User testuser = new User();

        testuser.setUsername("TestUser1");
        testuser.setPassword("Password123");
        testuser.setDob("2012-05-03");
        testuser.setEmail("harryalexander1@gmail.com");
        testuser.setFirstName("Harry");
        testuser.setLastName("Alexander");
        testuser.setPhoneNumber("07743211926");

        userDAO.insertUser(testuser);
        //new id made

       // userDAO.getUserDetails(testuser.getUserID());
        //new random ids are added back to user object

        userDAO.getUser(testuser.getUserID());
        //new random ids are added back to user object

       // userDAO.updateUserDetails(testuser);

        userDAO.getUserDetails(testuser.getUserDetailsID());

        try {

            User user2 = userDAO.getUserDetails(testuser.getUserDetailsID());

            Assert.assertEquals(testuser.getFirstName(), user2.getFirstName());
            Assert.assertEquals(testuser.getLastName(), user2.getLastName());
            Assert.assertEquals(testuser.getEmail(), user2.getEmail());
            Assert.assertEquals(testuser.getPhoneNumber(), user2.getPhoneNumber());
            Assert.assertEquals(testuser.getDob(), user2.getDob());
            Assert.assertEquals(testuser.getUserDetailsID(), user2.getUserDetailsID());
            userDAO.deleteUser(testuser);

        } catch (final NullPointerException e) {
            Assert.assertFalse(true);
            e.printStackTrace();
        }
    }

    public void testGetAllUsers(){
        UserDAO userDAO = new UserDAO();
        ArrayList<User> Allusers = userDAO.getAllUsers();

        try{
            System.out.println(Allusers);
            Assert.assertNotNull(Allusers);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/*    public void testGetUser(){
        UserDAO userDAO = new UserDAO();
        //userDAO.getUser(userid);
        //User user = new User();

        User user = (userDAO.getUser(1253));
        System.out.println(user.getUserID());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
    }*/
}