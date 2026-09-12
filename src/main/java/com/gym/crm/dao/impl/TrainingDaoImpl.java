package com.gym.crm.dao.impl;

import com.gym.crm.dao.TrainingDao;
import com.gym.crm.model.Training;
import com.gym.crm.storage.TrainingStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDaoImpl implements TrainingDao {
    private static final Logger logger  = LoggerFactory.getLogger(TrainingDaoImpl.class);
    private TrainingStorage trainingStorage;

    @Autowired
    public void setTrainingStorage(TrainingStorage trainingStorage){
        this.trainingStorage = trainingStorage;
        logger.debug("TrainingStorage injected successfully. Current size: {}",
                trainingStorage.getStorage().size());
    }

    @Override
    public void save(Training training) {
        trainingStorage.put(training.getId(), training);
        logger.info("Training saved successfully: id={}, trainingname={}",
                training.getId(), training.getTrainingName());
        logger.debug("Training storage size after save: {}",
                trainingStorage.getStorage().size());
    }

    @Override
    public Training findById(Long id) {
       Training training = trainingStorage.findById(id);
        if (training == null) {
            logger.warn("Training not found by id={}", id);
        } else {
            logger.debug("Training found: id={}, trainingname ={}", training.getId(), training.getTrainingName());
        }
       return training;
    }

    @Override
    public List<Training> findAll() {
        List<Training> trainings = trainingStorage.findAll();
        logger.info("Found {} training", trainings.size());
        return trainings;
    }
}
