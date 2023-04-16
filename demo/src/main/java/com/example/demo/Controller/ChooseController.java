package com.example.demo.Controller;

import com.example.demo.Entity.Choose;
import com.example.demo.Entity.CourseInfo;
import com.example.demo.Service.ChooseService;
import com.example.demo.Service.CourseService;
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
public class ChooseController {
    private ChooseService chooseService;
    Logger logger = LoggerFactory.getLogger(ChooseController.class);
    @Autowired
    public ChooseController(ChooseService chooseService) {
        this.chooseService = chooseService;
    }

    @RequestMapping(value = "/api/lookChooseList",method = {RequestMethod.POST})
    public ResponseEntity<?> lookChooseList(@RequestBody Map<String,Object> map) {
        logger.debug("lookChooseList: " + map.toString());
        List<Choose> chooseList = chooseService.lookChooseList(map);
        Map<String,Object> response = new HashMap<>();
        response.put("chooseList",chooseList);
        return ResponseEntity.ok(response);
    }
}
