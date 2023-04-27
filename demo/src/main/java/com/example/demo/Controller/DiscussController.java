package com.example.demo.Controller;

import com.example.demo.Entity.Discuss;
import com.example.demo.Entity.Experiment;
import com.example.demo.Service.DiscussService;
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
public class DiscussController {
    private DiscussService discussService;
    Logger logger = LoggerFactory.getLogger(DiscussController.class);
    @Autowired
    public DiscussController(DiscussService discussService) {
        this.discussService =discussService;
    }

    @RequestMapping(value = "/api/lookDiscussList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookDiscussList(@RequestBody Map<String,Object> map) {
        logger.debug("lookDiscussList: " + map.toString());
        List<Map> discussList = discussService.lookDiscussList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("discussList",discussList);
        return ResponseEntity.ok(response);
    }
    @RequestMapping(value = "/api/addDiscuss",method = {RequestMethod.POST})
    public ResponseEntity<?> addDiscuss(@RequestBody Map<String,Object> map) {
        logger.debug("addDiscuss: " + map.toString());
        Discuss discuss = discussService.addDiscuss(map);
        Map<String,Object> response = new HashMap<>();
        response.put("discuss",discuss);
        return ResponseEntity.ok(response);
    }
}
