package com.example.demo.Service;

import com.example.demo.Dao.CourseStudentDao;
import com.example.demo.Entity.CourseStudent;
import com.example.demo.Exception.CannotBeenFoundException;
import com.example.demo.Exception.HasBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CourseStudentService {
    @Autowired
    public CourseStudentDao courseStudentDao;

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
        return courseStudent;
    }
}
