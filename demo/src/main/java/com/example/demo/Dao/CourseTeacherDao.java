package com.example.demo.Dao;

import com.example.demo.Entity.CourseTeacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface CourseTeacherDao extends CrudRepository<CourseTeacher,Long> {
    public List<CourseTeacher> findAllByCourseId(String courseId);

    public CourseTeacher findByUserId(String userId);

    public List<CourseTeacher> findAllByUserId(String userId);
    @Transactional
    public void deleteAllByUserId(String userId);
}
