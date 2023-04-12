package com.example.demo.Dao;


import com.example.demo.Entity.Experiment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExperimentDao extends CrudRepository<Experiment,Long> {

    public Optional<Experiment> findById(Long id);

    public List<Experiment> findAllByCourseId(String courseId);
}
