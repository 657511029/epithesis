package com.example.demo.Dao;

import com.example.demo.Entity.CourseStudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public interface CourseStudentDao  extends CrudRepository<CourseStudent,Long> {
    CourseStudent findByCourseIdAndUserId(String courseId,String userId);

    List<CourseStudent> findAllByUserId(String userId);
    @Query(value = "SELECT * FROM course_student where course_id = :courseId order by pass_number DESC ",nativeQuery = true)
    List<CourseStudent> findAllByCourseId(@Param("courseId") String courseId);

    @Transactional
    public void deleteAllByUserId(String userId);
}
