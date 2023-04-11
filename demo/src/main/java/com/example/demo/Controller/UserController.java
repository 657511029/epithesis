package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @RequestMapping(value = "/api/register",method = {RequestMethod.POST})
    public ResponseEntity<?> register(@RequestBody Map<String,Object> map) {
        logger.debug("RegisterForm: " + map.toString());
        User user = userService.register(map);
        Map<String,Object> response = new HashMap<>();
        response.put("user",user);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/login",method = {RequestMethod.POST})
    public ResponseEntity<?> login(@RequestBody Map<String,Object> map) {
        logger.debug("LoginForm: " + map.toString());
        User user = userService.login(map);
        Map<String,Object> response = new HashMap<>();
        response.put("user",user);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/modifyUserInfo",method = {RequestMethod.POST})
    public ResponseEntity<?> modifyUserInfo(@RequestBody Map<String,Object> map) {
        logger.debug("modifyUserInfo: " + map.toString());
        User user = userService.modifyUserInfo(map);
        Map<String,Object> response = new HashMap<>();
        response.put("user",user);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookUserInfo",method = {RequestMethod.POST})
    public ResponseEntity<?> lookUserInfo(@RequestBody Map<String,Object> map) {
        logger.debug("modifyUserInfo: " + map.toString());
        User user = userService.lookUserInfo(map);
        Map<String,Object> response = new HashMap<>();
        response.put("user",user);
        return ResponseEntity.ok(response);
    }
}
