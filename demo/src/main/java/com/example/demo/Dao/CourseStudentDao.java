package com.example.demo.Dao;

import com.example.demo.Entity.CourseStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public interface CourseStudentDao  extends CrudRepository<CourseStudent,Long> {
    CourseStudent findByCourseIdAndUserId(String courseId,String userId);
    @Transactional
    public void deleteAllByUserId(String userId);
}
