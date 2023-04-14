package com.example.demo.Service;

import com.example.demo.Dao.CourseInfoDao;
import com.example.demo.Dao.CourseStudentDao;
import com.example.demo.Dao.ExperimentStudentDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.CourseInfo;
import com.example.demo.Entity.CourseStudent;
import com.example.demo.Entity.User;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseStudentService {
    @Autowired
    public CourseStudentDao courseStudentDao;

    @Autowired
    public ExperimentStudentDao experimentStudentDao;

    @Autowired
    public UserDao userDao;

    @Autowired
    public CourseInfoDao courseInfoDao;
    public String lookIfJoinCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        String userId = (String) map.get("userId");
        CourseStudent courseStudent = courseStudentDao.findByCourseIdAndUserId(courseId,userId);
        if(courseStudent != null){
            return "true";
        }
        else {
            return "false";
        }
    }

    public  CourseStudent joinCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        String userId = (String) map.get("userId");
        CourseStudent courseStudent = courseStudentDao.findByCourseIdAndUserId(courseId,userId);
        if(courseStudent != null){
            throw new HasBeenFoundException("user in this course");
        }
        CourseStudent newCourseStudent = new CourseStudent();
        newCourseStudent.setCourseId(courseId);
        newCourseStudent.setUserId(userId);
        newCourseStudent.setPassNumber(0);
        courseStudentDao.save(newCourseStudent);
        return newCourseStudent;
    }
    public  CourseStudent outCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        String userId = (String) map.get("userId");
        CourseStudent courseStudent = courseStudentDao.findByCourseIdAndUserId(courseId,userId);
        if(courseStudent == null){
            throw new CannotBeenFoundException("user in this course");
        }
        courseStudentDao.delete(courseStudent);
        experimentStudentDao.deleteAllByUserIdAndCourseId(userId,courseId);
        return courseStudent;
    }

    public CourseStudent lookCourseStudent(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        String userId = (String) map.get("userId");
        CourseStudent courseStudent = courseStudentDao.findByCourseIdAndUserId(courseId,userId);
        return courseStudent;
    }
    public List<Map> lookCourseStudentList(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        List<CourseStudent> courseStudentList = courseStudentDao.findAllByCourseId(courseId);
        List list = new ArrayList();
        for(int i = 0; i < courseStudentList.size();i++){
            Optional<User> userOptional = userDao.findById(Long.parseLong(courseStudentList.get(i).getUserId()));
            if(userOptional.isPresent()){
                User user = userOptional.get();
                Map map1 = new HashMap();
                map1.put("username",user.getUsername());
                map1.put("passNumber",courseStudentList.get(i).getPassNumber());
                list.add(map1);
            }
        }
        return list;
    }
    public List<CourseInfo> lookCourseListOfUser(Map<String,Object> map){
        String userId = (String) map.get("userId");
        List<CourseStudent> courseStudentList = courseStudentDao.findAllByUserId(userId);
        List<CourseInfo> list = new ArrayList<>();
        for(int i = 0; i < courseStudentList.size();i++){
            String courseId = courseStudentList.get(i).getCourseId();
            Optional<CourseInfo> courseInfoOptional = courseInfoDao.findById(Long.parseLong(courseId));
            if(courseInfoOptional.isPresent()){
                CourseInfo courseInfo = courseInfoOptional.get();
                list.add(courseInfo);
            }
        }
        return list;
    }
}
