package com.gym.crm.dao;

import com.gym.crm.model.TrainingType;

import java.util.List;

public interface TrainingTypeDao {
    List<TrainingType> findAll();
    TrainingType findByName(String name);
}

