package com.example.demo.Service;

import com.example.demo.Dao.CourseTeacherDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.CourseTeacher;
import com.example.demo.Entity.Experiment;
import com.example.demo.Entity.User;
import com.example.demo.Exception.CannotBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseTeacherService {
    @Autowired
    public CourseTeacherDao courseTeacherDao;

    @Autowired
    public UserDao userDao;
    public List<Map> lookTeacherOfCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        System.out.println(courseId);
        List list = new ArrayList();
        List<CourseTeacher> courseTeacherList = courseTeacherDao.findAllByCourseId(courseId);
        for(int i = 0;i < courseTeacherList.size();i++){
            Map map1 = new HashMap();
            String userId = courseTeacherList.get(i).getUserId();
            Optional<User> userOptional = userDao.findById(Long.parseLong(userId));
            if(userOptional.isPresent()){
                User user = userOptional.get();
                map1.put("userId",userId);
                map1.put("username",user.getUsername());
                map1.put("userPic",user.getAvatarUrl());
                list.add(map1);
            }
            else {
                throw new CannotBeenFoundException("user");
            }
        }
        return list;
    }
}
