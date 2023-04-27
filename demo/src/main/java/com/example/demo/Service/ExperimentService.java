package com.example.demo.Service;

import com.example.demo.Dao.ExperimentDao;
import com.example.demo.Entity.CourseInfo;
import com.example.demo.Entity.Experiment;
import com.example.demo.Exception.CannotBeenFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ExperimentService {
    @Autowired
    public ExperimentDao experimentDao;

    public List<Experiment> lookExperimentOfCourse(Map<String,Object> map){
        String courseId = (String) map.get("courseId");
        System.out.println(courseId);
       List<Experiment> experimentList = experimentDao.findAllByCourseId(courseId);
        return experimentList;
    }

    public Experiment lookExperiment(Map<String,Object> map){
        String experimentId = (String) map.get("experimentId");
        Optional<Experiment> experimentOptional = experimentDao.findById(Long.parseLong(experimentId));
        if(experimentOptional.isPresent()){
            Experiment experiment = experimentOptional.get();
            return experiment;
        }
        else {
            throw new CannotBeenFoundException("experiment");
        }
    }
}
