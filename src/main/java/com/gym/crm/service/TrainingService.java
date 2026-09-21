package com.gym.crm.service;

import com.gym.crm.entity.Training;
import com.gym.crm.repository.TraineeRepository;
import com.gym.crm.repository.TrainerRepository;
import com.gym.crm.repository.TrainingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;
    private TrainingRepository trainingRepository;

    public TrainingService(TraineeRepository traineeRepository,
                           TrainerRepository trainerRepository,
                           TrainingRepository trainingRepository){
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.trainingRepository = trainingRepository;
    }

    public Training createTraining(Training training){
        validate(training);
        if(traineeRepository.findById(training.getTrainee().getId()).isEmpty()) throw new IllegalArgumentException("Trainee not found");
        if(trainerRepository.findById(training.getTrainer().getId()).isEmpty()) throw new IllegalArgumentException("Trainer not found");

        trainingRepository.save(training);

        return training;
    }

    public List<Training> getAll(){
        return trainingRepository.findAll();
    }

    public Optional<Training> getById(Long id){
        if(id == null) throw new IllegalArgumentException("Id null");
        return trainingRepository.findById(id);
    }

    private void validate(Training training){
        if(training == null) throw new IllegalArgumentException("Training is null");
        if (isBlank(training.getTrainingName())) throw new IllegalArgumentException("Training name blank");
        if(training.getTrainingType() == null) throw new IllegalArgumentException("Training type null");
        if (training.getTrainee().getId() == null) throw new IllegalArgumentException("Trainee null");
        if (training.getTrainer().getId() == null) throw new IllegalArgumentException("Trainer null");
        if(training.getTrainingDate() == null) throw new IllegalArgumentException("Training date null");
        if (training.getTrainingDuration() == null || training.getTrainingDuration() <= 0) throw new IllegalArgumentException("Training duration invalid");
    }

    private boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
