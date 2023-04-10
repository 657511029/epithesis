package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;

import com.example.demo.Exception.IncorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    public UserDao userDao;
    public User register(Map<String,Object> map) {
        //获取信息
//        String phoneNumber = (String)map.get("phoneNumber");
        String username = (String)map.get("username");
        String password = (String) map.get("password");
        String email = (String) map.get("email");
        String authority = (String)map.get("authority");
        User userEntity = userDao.findByEmail(email);
        User userEntity1 = userDao.findByUsername(username);

        if (userEntity != null) {
            throw new HasBeenFoundException("email: " + email);
        }
        if(userEntity1 != null){
            throw new HasBeenFoundException("username: " + username);
        }
        User newUser = new User();
//        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setAuthority(authority);
        userDao.save(newUser);
        return newUser;
    }
    public User login(Map<String,Object> map){
        String username = (String)map.get("username");
        String password = (String) map.get("password");
        System.out.println(username);
        System.out.println(password);
        User userEntity = userDao.findByUsername(username);
        if (userEntity == null) {
            throw new CannotBeenFoundException("username: " + username);
        }
        System.out.println(userEntity.getPassword());
        if(!password.equals(userEntity.getPassword())){
            throw new IncorrectException("password");
        }
        return userEntity;
    }

    public User modifyUserInfo(Map<String,Object> map){
        String username = (String)map.get("username");
        String oldUsername = (String)map.get("oldUsername");
        String email = (String) map.get("email");
        String phoneNumber = (String)map.get("phoneNumber");
        User userEntity = userDao.findByEmail(email);
        User userEntity1 = userDao.findByUsername(oldUsername);
        if (userEntity != null) {
            throw new HasBeenFoundException("email: " + email);
        }
        if(userEntity1 != null){
            throw new HasBeenFoundException("username: " + username);
        }
        User user = userDao.findByUsername(oldUsername);
        user.setEmail(email);
        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        userDao.save(user);
        return user;
    }
}
