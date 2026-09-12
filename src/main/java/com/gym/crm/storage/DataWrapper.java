package com.gym.crm.storage;

import com.gym.crm.model.Trainee;
import com.gym.crm.model.Trainer;
import com.gym.crm.model.Training;
import lombok.Data;

import java.util.List;

@Data
public class DataWrapper {
    private List<Trainee> trainees;
    private List<Trainer> trainers;
    private List<Training> trainings;
}
