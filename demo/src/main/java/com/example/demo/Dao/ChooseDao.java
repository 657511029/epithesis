package com.example.demo.Dao;

import com.example.demo.Entity.Choose;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChooseDao extends CrudRepository<Choose,Long> {
    List<Choose> findAll();
}
