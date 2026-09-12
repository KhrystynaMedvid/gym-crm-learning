package com.gym.crm.service;

import com.gym.crm.dao.TraineeDao;
import com.gym.crm.dao.TrainerDao;
import com.gym.crm.model.Trainer;
import com.gym.crm.storage.TrainerStorage;
import com.gym.crm.util.UsernamePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {
    private TrainerDao trainerDao;
    private TraineeDao traineeDao;
    private TrainerStorage trainerStorage;
    private UsernamePasswordGenerator usernamePasswordGenerator;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainerStorage(TrainerStorage trainerStorage) {
        this.trainerStorage = trainerStorage;
    }

    @Autowired
    public void setUsernamePasswordGenerator(UsernamePasswordGenerator usernamePasswordGenerator) {
        this.usernamePasswordGenerator = usernamePasswordGenerator;
    }

    public Trainer createTrainer(Trainer trainer){
        validate(trainer);

        trainer.setId(trainerStorage.generateId());
        String username = usernamePasswordGenerator.generateUsername(
                trainer.getFirstName(),
                trainer.getLastName(),
                u -> trainerDao.exists(u) || traineeDao.exists(u)
        );

        trainer.setUsername(username);
        trainer.setPassword(usernamePasswordGenerator.generatePassword());
        trainer.setActive(true);

        trainerDao.save(trainer);
        return trainer;
    }

    public void updateTrainer(Trainer trainer){
        validateUpdate(trainer);
        trainerDao.update(trainer);
    }

    public Trainer getById(Long id){
        if (id == null) throw new IllegalArgumentException("Id is null");
        return trainerDao.findById(id);
    }

    public Trainer getByUsername(String username){
        if (isBlank(username)) throw new IllegalArgumentException("Username is blank");
        return trainerDao.findByUsername(username);
    }


    private void validate(Trainer trainer){
        if (trainer == null) throw new IllegalArgumentException("Trainer is null");
        if (isBlank(trainer.getFirstName()))
            throw new IllegalArgumentException("First name is blank");
        if (isBlank(trainer.getLastName()))
            throw new IllegalArgumentException("Last name is blank");
    }

    private void validateUpdate(Trainer trainer){
        if (trainer == null || trainer.getId() == null) throw new IllegalArgumentException("Invalid trainer");
        if (isBlank(trainer.getUsername())) throw new IllegalArgumentException("Username blank");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
