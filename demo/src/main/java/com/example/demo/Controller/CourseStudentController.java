package com.example.demo.Controller;

import com.example.demo.Entity.CourseInfo;
import com.example.demo.Entity.CourseStudent;
import com.example.demo.Service.CourseStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CourseStudentController {
    private CourseStudentService courseStudentService;
    Logger logger = LoggerFactory.getLogger(CourseStudentController.class);
    @Autowired
    public CourseStudentController(CourseStudentService courseStudentService) {
        this.courseStudentService =courseStudentService;
    }
    @RequestMapping(value = "/api/lookIfJoinCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> lookIfJoin(@RequestBody Map<String,Object> map) {
        logger.debug("lookIfJoin: " + map.toString());
        String lookIfJoin =courseStudentService.lookIfJoinCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("lookIfJoin",lookIfJoin);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/joinCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> joinCourse(@RequestBody Map<String,Object> map) {
        logger.debug("joinCourse: " + map.toString());
        CourseStudent courseStudent =courseStudentService.joinCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseStudent",courseStudent);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/outCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> outCourse(@RequestBody Map<String,Object> map) {
        logger.debug("outCourse: " + map.toString());
        CourseStudent courseStudent =courseStudentService.outCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseStudent",courseStudent);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseStudent",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseStudent(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseStudent: " + map.toString());
        CourseStudent courseStudent =courseStudentService.lookCourseStudent(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseStudent",courseStudent);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseStudentList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseStudentList(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseStudentList: " + map.toString());
       List<Map> courseStudentList =courseStudentService.lookCourseStudentList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseStudentList",courseStudentList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/lookCourseListOfUser",method = {RequestMethod.POST})
    public ResponseEntity<?> lookCourseListOfUser(@RequestBody Map<String,Object> map) {
        logger.debug("lookCourseListOfUser: " + map.toString());
        List<CourseInfo> courseStudentList =courseStudentService.lookCourseListOfUser(map);
        Map<String,Object> response = new HashMap<>();
        response.put("courseList",courseStudentList);
        return ResponseEntity.ok(response);
    }
}
