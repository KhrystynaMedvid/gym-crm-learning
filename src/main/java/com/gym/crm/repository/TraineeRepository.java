package com.gym.crm.repository;

import com.gym.crm.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    public Trainee getByUserUsername(String username);
}
