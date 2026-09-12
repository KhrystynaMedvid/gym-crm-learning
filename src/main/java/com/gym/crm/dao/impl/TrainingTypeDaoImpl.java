package com.gym.crm.dao.impl;

import com.gym.crm.dao.TrainingTypeDao;
import com.gym.crm.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class TrainingTypeDaoImpl implements TrainingTypeDao {
    private static final Logger logger = LoggerFactory.getLogger(TrainingTypeDaoImpl.class);
    @Override
    public List<TrainingType> findAll() {
        List<TrainingType> types = Arrays.asList(TrainingType.values());
        logger.info("Found {} training types", types.size());
        logger.debug("Available training types: {}", types);
        return types;
    }

    @Override
    public TrainingType findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            logger.warn("Training type search skipped. Name is null or empty");
            return null;
        }

        TrainingType type = Arrays.stream(TrainingType.values())
                .filter(t -> t.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);

        if (type == null) {
            logger.warn("Training type not found by name={}", name);
        } else {
            logger.debug("Training type found: {}", type);
        }

        return type;
    }
}
