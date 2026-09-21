package com.gym.crm.repository;

import com.gym.crm.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    public Trainer findByUserUsername(String username);
}
