package com.example.demo.Dao;

import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends CrudRepository<User,Long> {
    public User findByEmail(String email);
    public User findByUsername(String username);
}
