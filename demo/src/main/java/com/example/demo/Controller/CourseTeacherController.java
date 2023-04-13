package com.example.demo.Controller;

import com.example.demo.Entity.CourseTeacher;
import com.example.demo.Entity.Experiment;
import com.example.demo.Service.CourseTeacherService;
import com.example.demo.Service.ExperimentService;
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
public class CourseTeacherController {
    private CourseTeacherService courseTeacherService;
    Logger logger = LoggerFactory.getLogger(CourseTeacherController.class);
    @Autowired
    public CourseTeacherController(CourseTeacherService courseTeacherService) {
        this.courseTeacherService =courseTeacherService;
    }
    @RequestMapping(value = "/api/lookTeacherOfCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> lookTeacherOfCourse(@RequestBody Map<String,Object> map) {
        logger.debug("lookTeacherOfCourse: " + map.toString());
        List<Map> courseTeacherList =courseTeacherService.lookTeacherOfCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("teacherList",courseTeacherList);
        return ResponseEntity.ok(response);
    }
}
