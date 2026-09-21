package com.gym.crm.repository;

import com.gym.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public Boolean existsByUsername(String username);
}
