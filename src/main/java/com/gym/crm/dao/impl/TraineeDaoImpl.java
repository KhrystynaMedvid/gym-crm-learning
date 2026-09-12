package com.gym.crm.dao.impl;

import com.gym.crm.dao.TraineeDao;
import com.gym.crm.model.Trainee;
import com.gym.crm.storage.TraineeStorage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.slf4j.Logger;


@Repository
public class TraineeDaoImpl implements TraineeDao {
    private static final Logger logger =
            LoggerFactory.getLogger(TraineeDaoImpl.class);
    private TraineeStorage traineeStorage;

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage){
        this.traineeStorage = traineeStorage;
        logger.debug("TraineeStorage injected successfully. Current size: {}",
                traineeStorage.getStorage().size());
    }

    @Override
    public void save(Trainee trainee) {
        traineeStorage.put(trainee.getId(), trainee);
        logger.info("Trainee saved successfully: id={}, username={}",
                trainee.getId(), trainee.getUsername());
        logger.debug("Trainee storage size after save: {}",
                traineeStorage.getStorage().size());
    }

    @Override
    public Trainee findById(Long id) {
        Trainee trainee = traineeStorage.findById(id);
        if (trainee == null) {
            logger.warn("Trainee not found by id={}", id);
        } else {
            logger.debug("Trainee found: id={}, username={}", trainee.getId(), trainee.getUsername());
        }
        return trainee;
    }

    @Override
    public Trainee findByUsername(String username) {
        Trainee trainee = traineeStorage.getStorage()
                .values()
                .stream()
                .filter(t -> username.equals(t.getUsername()))
                .findFirst()
                .orElse(null);

        if (trainee == null) {
            logger.warn("Trainee not found by username={}", username);
        } else {
            logger.debug("Trainee found: id={}, username={}", trainee.getId(), trainee.getUsername());
        }
        return trainee;
    }

    @Override
    public List<Trainee> findAll() {
        List<Trainee> trainees = traineeStorage.findAll();
        logger.info("Found {} trainees", trainees.size());
        return trainees;
    }

    @Override
    public void delete(Long id) {
        Trainee removed = traineeStorage.getStorage().remove(id);
        if (removed == null) {
            logger.warn("Cannot delete trainee. Trainee not found id={}", id);
        } else {
            logger.info("Trainee deleted successfully id={}, username={}", removed.getId(), removed.getUsername());
        }
        logger.debug("Trainee storage size after delete: {}", traineeStorage.getStorage().size());
    }

    @Override
    public void update(Trainee trainee) {
        if (!traineeStorage.getStorage().containsKey(trainee.getId())) {
            logger.error("Cannot update trainee. Trainee not found id={}", trainee.getId());
            throw new IllegalArgumentException("Trainee not found: " + trainee.getId());
        }
        traineeStorage.put(trainee.getId(), trainee);
        logger.info("Trainee updated successfully id={}, username={}", trainee.getId(), trainee.getUsername());
    }

    @Override
    public boolean exists(String username) {
        boolean exists = traineeStorage.getStorage()
                .values()
                .stream()
                .anyMatch(t -> username.equals(t.getUsername()));
        logger.debug("Trainee username={} exists={}", username, exists);
        return exists;
    }
}
