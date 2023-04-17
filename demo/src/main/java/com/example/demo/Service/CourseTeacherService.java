package com.example.demo.Service;

import com.example.demo.Dao.CourseInfoDao;
import com.example.demo.Dao.CourseTeacherDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.*;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseTeacherService {
    @Autowired
    public CourseTeacherDao courseTeacherDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public CourseInfoDao courseInfoDao;
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

    public CourseTeacher addTeacher(Map<String,Object> map){
        String userId = (String) map.get("userId");
        String courseId = (String)map.get("courseId");
        CourseTeacher courseTeacherEntity = courseTeacherDao.findByUserId(userId);
        if(courseTeacherEntity != null){
            throw new HasBeenFoundException("teacher in this course");
        }
        CourseTeacher newCourseTeacher = new CourseTeacher();
        newCourseTeacher.setCourseId(courseId);
        newCourseTeacher.setUserId(userId);
        CourseTeacher courseTeacher = courseTeacherDao.save(newCourseTeacher);
        return courseTeacher;
    }
    public CourseTeacher deleteTeacher(Map<String,Object> map){
        String userId = (String) map.get("userId");
        String courseId = (String)map.get("courseId");
        CourseTeacher courseTeacherEntity = courseTeacherDao.findByUserId(userId);
        if(courseTeacherEntity == null){
            throw new CannotBeenFoundException("teacher in this course");
        }
        CourseTeacher newCourseTeacher = new CourseTeacher();
        newCourseTeacher.setCourseId(courseId);
        newCourseTeacher.setUserId(userId);
        CourseTeacher courseTeacher = courseTeacherDao.save(newCourseTeacher);
        return courseTeacher;
    }
    public List<CourseInfo> lookCourseListOfTeacher(Map<String,Object> map){
        String userId = (String) map.get("userId");
        List<CourseTeacher> courseTeacherList = courseTeacherDao.findAllByUserId(userId);
        List<CourseInfo> list = new ArrayList<>();
        for(int i = 0; i < courseTeacherList.size();i++){
            String courseId = courseTeacherList.get(i).getCourseId();
            Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
            if(courseInfoOptional.isPresent()){
                CourseInfo courseInfo = courseInfoOptional.get();
                list.add(courseInfo);
            }
        }
        return list;
    }
}
