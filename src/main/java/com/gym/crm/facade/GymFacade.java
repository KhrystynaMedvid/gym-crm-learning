package com.gym.crm.facade;

import com.gym.crm.entity.Trainee;
import com.gym.crm.entity.Trainer;
import com.gym.crm.entity.Training;
import com.gym.crm.service.TraineeService;
import com.gym.crm.service.TrainerService;
import com.gym.crm.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private  final TrainingService trainingService;

    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee createTrainee(Trainee trainee){
        return traineeService.createTrainee(trainee);
    }

    public Optional<Trainee> getTraineeById(Long id){
        return traineeService.getById(id);
    }

    public Trainee getTraineeByUsername(String username){
        return traineeService.getByUsername(username);
    }

    public void updateTrainee(Trainee trainee){
        traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Trainee trainee){
        traineeService.deleteTrainee(trainee);
    }

    public Trainer createTrainer(Trainer trainer){
        return trainerService.createTrainer(trainer);
    }

    public Optional<Trainer> getTrainerById(Long id){
        return trainerService.getById(id);
    }

    public Trainer getTrainerByUsername(String username){
        return trainerService.getByUsername(username);
    }

    public void updateTrainer(Trainer trainer){
        trainerService.updateTrainer(trainer);
    }

    public Training createTraining(Training training){
        return trainingService.createTraining(training);
    }

    public Optional<Training> getTrainingById(Long id){
        return trainingService.getById(id);
    }

    public List<Training> getAllTrainings(){
        return trainingService.getAll();
    }
}
