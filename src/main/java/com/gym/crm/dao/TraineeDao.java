package com.gym.crm.dao;

import com.gym.crm.model.Trainee;

import java.util.List;

public interface TraineeDao {
    void save(Trainee trainee);
    Trainee findById(Long id);
    Trainee findByUsername(String username);
    List<Trainee> findAll();
    void delete(Long id);
    void update(Trainee trainee);
    boolean exists(String username);
}
