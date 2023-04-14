package com.example.demo.Controller;

import com.example.demo.Service.CourseStudentService;
import com.example.demo.Service.ExperimentStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ExperimentStudentController {
    private ExperimentStudentService experimentStudentService;
    Logger logger = LoggerFactory.getLogger(ExperimentStudentController.class);
    @Autowired
    public ExperimentStudentController(ExperimentStudentService experimentStudentService) {
        this.experimentStudentService = experimentStudentService;
    }
}
