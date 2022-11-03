package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public class UserController {
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(path = "/tenmo_user/{username}/id", method = RequestMethod.GET )
    public int findIdByUsername(@PathVariable String username){
        return userDao.findIdByUsername(username);
    }

    @RequestMapping(path = "/tenmo_user/{userId}", method = RequestMethod.GET)
    public User getUserById (@PathVariable int userId){
        return userDao.getUserById(userId);
    }

    @RequestMapping (path = "/tenmo_user/all", method = RequestMethod.GET)
    public List<User> findAll(){
        return userDao.findAll();
    }

    @RequestMapping (path = "/tenmo_user/{username}", method = RequestMethod.GET)
    public User findByUsername (@PathVariable String username){
        return userDao.findByUsername(username);
    }

    @ResponseStatus (HttpStatus.CREATED)
    @RequestMapping (path = "/tenmo_user/create", method = RequestMethod.POST)
    public boolean create (String username, String password){
        return userDao.create(username, password);
    }




}
