package com.example.demo.Dao;

import com.example.demo.Entity.CourseInfo;
import com.example.demo.Entity.CourseTeacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseTeacherDao extends CrudRepository<CourseTeacher,Long> {
    public List<CourseTeacher> findAllByCourseId(String courseId);
}
