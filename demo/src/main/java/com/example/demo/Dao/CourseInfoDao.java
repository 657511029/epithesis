package com.example.demo.Dao;

import com.example.demo.Entity.CourseInfo;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface CourseInfoDao extends CrudRepository<CourseInfo,Long> {
    public Optional<CourseInfo> findById(Long id);

    public CourseInfo findByCourseName(String courseName);

    public List<CourseInfo> findAllByCourseNameLike(String courseName);

    List<CourseInfo> findAllByChooseName(String chooseName);
    @Query(value = "SELECT * FROM course_info order by student_number DESC LIMIT 5 ",nativeQuery = true)
    List<CourseInfo> findAllRecommend();

    @Query(value = "SELECT * FROM course_info",nativeQuery = true)
    List<CourseInfo> findAll();

}
