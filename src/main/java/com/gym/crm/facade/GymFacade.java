package com.gym.crm.facade;

import com.gym.crm.model.Trainee;
import com.gym.crm.model.Trainer;
import com.gym.crm.model.Training;
import com.gym.crm.service.TraineeService;
import com.gym.crm.service.TrainerService;
import com.gym.crm.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Trainee getTraineeById(Long id){
        return traineeService.getById(id);
    }

    public Trainee getTraineeByUsername(String username){
        return traineeService.getByUsername(username);
    }

    public void updateTrainee(Trainee trainee){
        traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Long id){
        traineeService.deleteTrainee(id);
    }

    public Trainer createTrainer(Trainer trainer){
        return trainerService.createTrainer(trainer);
    }

    public Trainer getTrainerById(Long id){
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

    public Training getTrainingById(Long id){
        return trainingService.getById(id);
    }

    public List<Training> getAllTrainings(){
        return trainingService.getAll();
    }
}
