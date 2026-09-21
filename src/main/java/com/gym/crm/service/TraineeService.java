package com.gym.crm.service;

import com.gym.crm.entity.Trainee;
import com.gym.crm.entity.User;

import com.gym.crm.repository.TraineeRepository;
import com.gym.crm.repository.TrainerRepository;
import com.gym.crm.repository.UserRepository;
import com.gym.crm.util.UsernamePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TraineeService {
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;
    private UserRepository userRepository;
    private UsernamePasswordGenerator usernamePasswordGenerator;

    @Autowired
    public TraineeService(TrainerRepository trainerRepository, TraineeRepository traineeRepository,
                            UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.userRepository = userRepository;

    }

    @Autowired
    public void setUsernamePasswordGenerator(UsernamePasswordGenerator usernamePasswordGenerator) {
        this.usernamePasswordGenerator = usernamePasswordGenerator;
    }

    public Trainee createTrainee(Trainee trainee){
        validate(trainee);
        User user = trainee.getUser();

        String username = usernamePasswordGenerator.generateUsername(
                user.getFirstName(),
                user.getLastName(),
                u -> userRepository.existsByUsername(u)
        );
        user.setUsername(username);

        user.setPassword(usernamePasswordGenerator.generatePassword());
        trainee.getUser().setActive(true);

        traineeRepository.save(trainee);

        return trainee;
    }

    public void updateTrainee(Trainee trainee){
        validateUpdate(trainee);
        traineeRepository.save(trainee);
    }

    public void deleteTrainee(Trainee trainee){
        if(trainee == null) throw new IllegalArgumentException("Trainee is null");
        traineeRepository.delete(trainee);

    }

    public Optional<Trainee> getById(Long id){
        if(id == null) throw new IllegalArgumentException("Id is null");
        return traineeRepository.findById(id);
    }

    public Trainee getByUsername(String username){
        if(isBlank(username)) throw new IllegalArgumentException("Username is blank");
        return traineeRepository.getByUserUsername(username);
    }


    private void validate(Trainee trainee) {
        if (trainee == null) throw new IllegalArgumentException("Trainee is null");
        if (isBlank(trainee.getUser().getFirstName()))
            throw new IllegalArgumentException("First name blank");
        if (isBlank(trainee.getUser().getLastName()))
            throw new IllegalArgumentException("Last name is blank");
    }

    private void validateUpdate(Trainee trainee) {
        if (trainee == null || trainee.getId() == null)
            throw new IllegalArgumentException("Invalid trainee");
        if (isBlank(trainee.getUser().getUsername()))
            throw new IllegalArgumentException("Username blank");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
