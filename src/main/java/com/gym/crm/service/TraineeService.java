package com.gym.crm.service;

import com.gym.crm.dao.TraineeDao;
import com.gym.crm.dao.TrainerDao;
import com.gym.crm.model.Trainee;
import com.gym.crm.storage.TraineeStorage;
import com.gym.crm.util.UsernamePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeService {
    private TraineeDao traineeDao;
    private TrainerDao trainerDao;
    private TraineeStorage traineeStorage;
    private UsernamePasswordGenerator usernamePasswordGenerator;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setTraineeStorage(TraineeStorage traineeStorage) {
        this.traineeStorage = traineeStorage;
    }

    @Autowired
    public void setUsernamePasswordGenerator(UsernamePasswordGenerator usernamePasswordGenerator) {
        this.usernamePasswordGenerator = usernamePasswordGenerator;
    }

    public Trainee createTrainee(Trainee trainee){
        validate(trainee);
        trainee.setId(traineeStorage.generateId());
        String username = usernamePasswordGenerator.generateUsername(
                trainee.getFirstName(),
                trainee.getLastName(),
                u -> traineeDao.exists(u) || trainerDao.exists(u)
        );
        trainee.setUsername(username);

        trainee.setPassword(usernamePasswordGenerator.generatePassword());
        trainee.setActive(true);

        traineeDao.save(trainee);

        return trainee;
    }

    public void updateTrainee(Trainee trainee){
        validateUpdate(trainee);
        traineeDao.update(trainee);
    }

    public void deleteTrainee(Long id){
        if(id == null) throw new IllegalArgumentException("Id is null");
        traineeDao.delete(id);

    }

    public Trainee getById(Long id){
        if(id == null) throw new IllegalArgumentException("Id is null");
        return traineeDao.findById(id);
    }

    public Trainee getByUsername(String username){
        if(isBlank(username)) throw new IllegalArgumentException("Username is blank");
        return traineeDao.findByUsername(username);
    }


    private void validate(Trainee trainee) {
        if (trainee == null) throw new IllegalArgumentException("Trainee is null");
        if (isBlank(trainee.getFirstName()))
            throw new IllegalArgumentException("First name blank");
        if (isBlank(trainee.getLastName()))
            throw new IllegalArgumentException("Last name is blank");
    }

    private void validateUpdate(Trainee trainee) {
        if (trainee == null || trainee.getId() == null)
            throw new IllegalArgumentException("Invalid trainee");
        if (isBlank(trainee.getUsername()))
            throw new IllegalArgumentException("Username blank");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
