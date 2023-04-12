package com.example.demo.Service;

import com.example.demo.Dao.CourseTeacherDao;
import com.example.demo.Entity.CourseTeacher;
import com.example.demo.Entity.Experiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseTeacherService {
    @Autowired
    public CourseTeacherDao courseTeacherDao;

    public List<CourseTeacher> lookTeacherOfCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        System.out.println(courseId);
        List<CourseTeacher> courseTeacherList = courseTeacherDao.findAllByCourseId(courseId);
        return courseTeacherList;
    }
}
