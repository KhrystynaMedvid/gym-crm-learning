package com.gym.crm.service;

import com.gym.crm.entity.Trainer;
import com.gym.crm.entity.User;
import com.gym.crm.repository.TraineeRepository;
import com.gym.crm.repository.TrainerRepository;
import com.gym.crm.repository.UserRepository;
import com.gym.crm.util.UsernamePasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainerService {
    private TrainerRepository trainerRepository;
    private TraineeRepository traineeRepository;
    private UserRepository userRepository;
    private UsernamePasswordGenerator usernamePasswordGenerator;

    public TrainerService(TrainerRepository trainerRepository, TraineeRepository traineeRepository, UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.traineeRepository = traineeRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUsernamePasswordGenerator(UsernamePasswordGenerator usernamePasswordGenerator) {
        this.usernamePasswordGenerator = usernamePasswordGenerator;
    }

    public Trainer createTrainer(Trainer trainer){
        validate(trainer);

        User user = trainer.getUser();
        String username = usernamePasswordGenerator.generateUsername(
                user.getFirstName(),
                user.getLastName(),
                u -> userRepository.existsByUsername(u)
        );

        user.setUsername(username);
        user.setPassword(usernamePasswordGenerator.generatePassword());
        user.setActive(true);

        trainerRepository.save(trainer);
        return trainer;
    }

    public void updateTrainer(Trainer trainer){
        validateUpdate(trainer);
        trainerRepository.save(trainer);
    }

    public Optional<Trainer> getById(Long id){
        if (id == null) throw new IllegalArgumentException("Id is null");
        return trainerRepository.findById(id);
    }

    public Trainer getByUsername(String username){
        if (isBlank(username)) throw new IllegalArgumentException("Username is blank");
        return trainerRepository.findByUserUsername(username);
    }


    private void validate(Trainer trainer){
        if (trainer == null) throw new IllegalArgumentException("Trainer is null");
        if (isBlank(trainer.getUser().getFirstName()))
            throw new IllegalArgumentException("First name is blank");
        if (isBlank(trainer.getUser().getLastName()))
            throw new IllegalArgumentException("Last name is blank");
    }

    private void validateUpdate(Trainer trainer){
        if (trainer == null || trainer.getId() == null) throw new IllegalArgumentException("Invalid trainer");
        if (isBlank(trainer.getUser().getUsername())) throw new IllegalArgumentException("Username blank");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
