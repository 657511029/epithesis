package com.example.demo.Dao;

import com.example.demo.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDao extends CrudRepository<User,Long> {
    public User findByEmail(String email);

    public List<User> findAllByEmail(String email);

    public List<User> findAllByUsername(String username);
    public User findByUsername(String username);
    public Optional<User> findById(Long id);

}
