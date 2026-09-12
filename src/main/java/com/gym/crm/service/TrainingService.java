package com.gym.crm.service;

import com.gym.crm.dao.TraineeDao;
import com.gym.crm.dao.TrainerDao;
import com.gym.crm.dao.TrainingDao;
import com.gym.crm.model.Training;
import com.gym.crm.storage.TrainingStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingService {
    private TrainerDao trainerDao;
    private TraineeDao traineeDao;
    private TrainingDao trainingDao;
    private TrainingStorage trainingStorage;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Autowired
    public void setTrainingStorage(TrainingStorage trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Training createTraining(Training training){
        validate(training);
        if(traineeDao.findById(training.getTraineeId()) == null) throw new IllegalArgumentException("Trainee not found");
        if(trainerDao.findById(training.getTrainerId()) == null) throw new IllegalArgumentException("Trainer not found");

        training.setId(trainingStorage.generateId());
        trainingDao.save(training);

        return training;
    }

    public List<Training> getAll(){
        return trainingDao.findAll();
    }

    public Training getById(Long id){
        if(id == null) throw new IllegalArgumentException("Id null");
        return trainingDao.findById(id);
    }

    private void validate(Training training){
        if(training == null) throw new IllegalArgumentException("Training is null");
        if (isBlank(training.getTrainingName())) throw new IllegalArgumentException("Training name blank");
        if(training.getTrainingType() == null) throw new IllegalArgumentException("Training type null");
        if (training.getTraineeId() == null) throw new IllegalArgumentException("Trainee null");
        if (training.getTrainerId() == null) throw new IllegalArgumentException("Trainer null");
        if(training.getTrainingDate() == null) throw new IllegalArgumentException("Training date null");
        if (training.getTrainingDuration() == null || training.getTrainingDuration() <= 0) throw new IllegalArgumentException("Training duration invalid");
    }

    private boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
