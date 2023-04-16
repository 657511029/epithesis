package com.example.demo.Service;

import com.example.demo.Dao.CourseInfoDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Entity.CourseInfo;
import com.example.demo.Exception.CannotBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    public UserDao userDao;
    @Autowired
    public CourseInfoDao courseInfoDao;

    public CourseInfo lookCourseInfo(Map<String,Object> map){
        Long courseId = Long.parseLong((String) map.get("courseId"));
        System.out.println(courseId);
        Optional<CourseInfo> optionalCourseInfo = courseInfoDao.findById(courseId);
        if(optionalCourseInfo.isPresent()){
            CourseInfo courseInfo = optionalCourseInfo.get();
            return courseInfo;
        }
        else {
            throw new CannotBeenFoundException("course");
        }
    }
    public List<CourseInfo> lookCourseInfoList(Map<String,Object> map){
        String message = (String) map.get("message");
        List<CourseInfo> courseInfoList = courseInfoDao.findAllByCourseNameLike("%" + message + "%");
        return courseInfoList;
    }
    public List<CourseInfo> lookCourseInfoRecommendList(Map<String,Object> map){
        List<CourseInfo> courseInfoList = courseInfoDao.findAllRecommend();
        return courseInfoList;
    }

    public List<CourseInfo> lookCourseInfoAllList(Map<String,Object> map){
        List<CourseInfo> courseInfoList = courseInfoDao.findAll();
        return courseInfoList;
    }
    public List<CourseInfo> lookCourseInfoChooseList(Map<String,Object> map){
        String chooseName = (String)map.get("chooseName");
        List<CourseInfo> courseInfoList = courseInfoDao.findAllByChooseName(chooseName);
        return courseInfoList;
    }
}
