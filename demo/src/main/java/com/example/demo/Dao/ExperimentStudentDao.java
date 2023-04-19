package com.example.demo.Dao;

import com.example.demo.Entity.ExperimentStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public interface ExperimentStudentDao extends CrudRepository<ExperimentStudent,Long> {
    @Transactional
    public void deleteAllByUserIdAndCourseId(String userId,String courseId);

    @Transactional
    public void deleteAllByUserId(String userId);

    @Transactional
    public void deleteAllByCourseId(String courseId);
}
