package com.example.demo.Service;

import com.example.demo.Dao.DiscussDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.Discuss;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiscussService {
    @Autowired
    public DiscussDao discussDao;

    @Autowired
    public UserDao userDao;
    public List<Map> lookDiscussList(Map<String,Object> map){
        String experimentId = (String) map.get("experimentId");
        List<Discuss> discussList = discussDao.findAllByExperimentId(experimentId);
        List<Map> mapList = new ArrayList<>();
        for(int i = 0;i < discussList.size();i++){
            Discuss discuss = discussList.get(i);
            Map map1 = new HashMap();
            map1.put("id",discuss.getId());
            Optional<User> userOptional = userDao.findById(Long.parseLong(discuss.getUserId()));
            if(userOptional.isPresent()){
                User user = userOptional.get();
                map1.put("username",user.getUsername());
                map1.put("userPic",user.getAvatarUrl());
            }
            map1.put("message",discuss.getMessage());
            map1.put("time",discuss.getTime());
            mapList.add(map1);
        }
        return mapList;
    }

    public Discuss addDiscuss(Map<String,Object> map){
        String experimentId = (String) map.get("experimentId");
        String userId = (String) map.get("userId");
        String message = (String) map.get("message");
        String time = (String) map.get("time");
        Discuss discuss = new Discuss();
        discuss.setExperimentId(experimentId);
        discuss.setMessage(message);
        discuss.setTime(time);
        discuss.setUserId(userId);
        discussDao.save(discuss);
        return discuss;
    }
}
