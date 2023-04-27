package com.example.demo.Dao;

import com.example.demo.Entity.Discuss;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiscussDao extends CrudRepository<Discuss,Long> {
   public List<Discuss> findAllByExperimentId(String experimentId);
}
