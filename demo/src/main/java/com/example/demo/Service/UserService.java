package com.example.demo.Service;

import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.User;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;

import com.example.demo.Exception.IncorrectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String registerTime = (String)map.get("registerTime");
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
        newUser.setRegisterTime(registerTime);
        newUser.setAvatarUrl("static/userPic/userPic.jpg");
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
        Long id =  ((Integer)map.get("id")).longValue();
        String username = (String)map.get("username");
        String email = (String) map.get("email");
        String phoneNumber = (String)map.get("phoneNumber");
        String career = (String)map.get("career");
        String age = (String)map.get("age");
        System.out.println(map.get("sex"));
        String sex = (String)map.get("sex");
        String address = (String)map.get("address");
        String interest = (String)map.get("interest");
        String avatarUrl = (String)map.get("avatarUrl");
        List<User> userEntity = userDao.findAllByEmail(email);
        List<User> userEntity1 = userDao.findAllByUsername(username);
        if (userEntity.size() > 1) {
            throw new HasBeenFoundException("email: " + email);
        }
        if(userEntity1.size() > 1){
            throw new HasBeenFoundException("username: " + username);
        }

        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setEmail(email);
            user.setUsername(username);
            user.setPhoneNumber(phoneNumber);
            user.setCareer(career);
            user.setAge(age);
            user.setSex(sex);
            user.setAddress(address);
            user.setInterest(interest);
            user.setAvatarUrl(avatarUrl);
            userDao.save(user);
            return user;
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }
    public User lookUserInfo(Map<String,Object> map){
        Long id = Long.parseLong((String) map.get("id"));
        Optional<User> userOptional = userDao.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return user;
        }
        else {
            throw new CannotBeenFoundException("user");
        }
    }
}
