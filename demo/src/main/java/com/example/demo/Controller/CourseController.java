package com.example.demo.Controller;

import com.example.demo.Entity.CourseInfo;
import com.example.demo.Service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseController {
    private CourseService courseService;
    Logger logger = LoggerFactory.getLogger(CourseController.class);
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @RequestMapping(value = "/api/lookCourseInfo",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseInfo(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseInfo: " + map.toString());
        CourseInfo courseInfo = courseService.lookCourseInfo(map);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseInfoList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseInfoList(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseInfoList: " + map.toString());
        List<CourseInfo> courseInfoList = courseService.lookCourseInfoList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseList",courseInfoList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseInfoRecommendList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseInfoRecommendList(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseInfoRecommendList: " + map.toString());
        List<CourseInfo> courseInfoList = courseService.lookCourseInfoRecommendList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseList",courseInfoList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseInfoChooseList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseInfoChooseList(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseInfoChooseList: " + map.toString());
        List<CourseInfo> courseInfoList = courseService.lookCourseInfoChooseList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseList",courseInfoList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseInfoAllList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseInfoAllList(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseInfoAllList: " + map.toString());
        List<CourseInfo> courseInfoList = courseService.lookCourseInfoAllList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseList",courseInfoList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/addCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> addCourse(@RequestBody Map<String,Object> map) {
        logger.debug("addCourse: ");
       CourseInfo courseInfo = courseService.addCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/modifyCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> modifyCourse(@RequestBody Map<String,Object> map) {
        logger.debug("addCourse: ");
        CourseInfo courseInfo = courseService.modifyCourseInfo(map);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/deleteCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> deleteCourse(@RequestBody Map<String,Object> map) {
        logger.debug("deleteCourse: " + map.toString());
        CourseInfo courseInfo = courseService.deleteCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/modifyDisplayUrl",method = {RequestMethod.POST})
    public ResponseEntity<?> modifyDisplayUrl(@RequestParam("displayFile")MultipartFile file,@RequestParam("courseId") String courseId) {
        logger.debug("modifyDisplayUrl: ");
        System.out.println("display");
        CourseInfo courseInfo = courseService.modifyDisplayUrl(file,courseId);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/modifyBackgroundUrl",method = {RequestMethod.POST})
    public ResponseEntity<?> modifyBackgroundUrl(@RequestParam("backgroundFile")MultipartFile file,@RequestParam("courseId") String courseId) {
        logger.debug("modifyDisplayUrl: ");
        System.out.println("background");
        CourseInfo courseInfo = courseService.modifyBackgroundUrl(file,courseId);
        Map<String,Object> response = new HashMap<>();
        response.put("course",courseInfo);
        return ResponseEntity.ok(response);
    }
}
