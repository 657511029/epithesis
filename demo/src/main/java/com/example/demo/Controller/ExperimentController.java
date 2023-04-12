package com.example.demo.Controller;

import com.example.demo.Entity.Experiment;
import com.example.demo.Entity.User;
import com.example.demo.Service.ExperimentService;
import com.example.demo.Service.UserService;
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
public class ExperimentController {
    private ExperimentService experimentService;
    Logger logger = LoggerFactory.getLogger(ExperimentController.class);
    @Autowired
    public ExperimentController(ExperimentService experimentService) {
        this.experimentService =experimentService;
    }
    @RequestMapping(value = "/api/lookExperimentOfCourse",method = {RequestMethod.POST})
    public ResponseEntity<?> lookExperimentOfCourse(@RequestBody Map<String,Object> map) {
        logger.debug("lookExperimentOfCourse: " + map.toString());
        List<Experiment> experimentList = experimentService.lookExperimentOfCourse(map);
        Map<String,Object> response = new HashMap<>();
        response.put("experimentList",experimentList);
        return ResponseEntity.ok(response);
    }
}
