package com.gym.crm.dao.impl;

import com.gym.crm.dao.TrainerDao;
import com.gym.crm.model.Trainer;
import com.gym.crm.storage.TrainerStorage;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDaoImpl implements TrainerDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainerDaoImpl.class);
    private TrainerStorage trainerStorage;

    @Autowired
    public void setTrainerStorage(TrainerStorage trainerStorage){
        this.trainerStorage = trainerStorage;
        logger.debug("TrainerStorage injected successfully. Current size: {}",
                trainerStorage.getStorage().size());
    }

    @Override
    public void save(Trainer trainer) {
        trainerStorage.put(trainer.getId(), trainer);
        logger.info("Trainer saved successfully: id={}, username={}",
                trainer.getId(), trainer.getUsername());
        logger.debug("Trainer storage size after save: {}",
                trainerStorage.getStorage().size());
    }

    @Override
    public Trainer findByUsername(String username) {
        Trainer trainer = trainerStorage.getStorage()
                .values()
                .stream()
                .filter(t -> username.equals(t.getUsername()))
                .findFirst()
                .orElse(null);
        if (trainer == null) {
            logger.warn("Trainer not found by username={}", username);
        } else {
            logger.debug("Trainer found: id={}, username={}", trainer.getId(), trainer.getUsername());
        }
        return trainer;
    }

    @Override
    public Trainer findById(Long id) {
        Trainer trainer = trainerStorage.findById(id);
        if (trainer == null) {
            logger.warn("Trainer not found by id={}", id);
        } else {
            logger.debug("Trainer found: id={}, username={}", trainer.getId(), trainer.getUsername());
        }
        return trainer;
    }

    @Override
    public void update(Trainer trainer) {
       if(!trainerStorage.getStorage().containsKey(trainer.getId())){
           logger.error("Cannot update trainer. Trainer not found id={}", trainer.getId());
           throw new IllegalArgumentException("Trainer not found: " + trainer.getId());
       }
        trainerStorage.put(trainer.getId(), trainer);
        logger.info("Trainer updated successfully id={}, username={}", trainer.getId(), trainer.getUsername());

    }

    @Override
    public boolean exists(String username) {
        boolean exists = trainerStorage.getStorage()
                .values()
                .stream()
                .anyMatch(t -> username.equals(t.getUsername()));
        logger.debug("Trainer username={} exists={}", username, exists);
        return exists;
    }
}
