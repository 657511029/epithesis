package com.example.demo.Service;

import com.example.demo.Dao.ExperimentStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExperimentStudentService {
    @Autowired
    public ExperimentStudentDao experimentStudentDao;


}
