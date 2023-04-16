package com.example.demo.Service;

import com.example.demo.Dao.ChooseDao;
import com.example.demo.Entity.Choose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChooseService {
    @Autowired
    public ChooseDao chooseDao;

    public List<Choose> lookChooseList(Map<String,Object> map){
        List<Choose> chooseList = chooseDao.findAll();
        return chooseList;
    }
}
