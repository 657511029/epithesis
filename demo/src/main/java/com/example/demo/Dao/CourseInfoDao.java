package com.example.demo.Dao;

import com.example.demo.Entity.CourseInfo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CourseInfoDao extends CrudRepository<CourseInfo,Long> {
    public Optional<CourseInfo> findById(Long id);

    public List<CourseInfo> findAllByCourseNameLike(String courseName);
}
