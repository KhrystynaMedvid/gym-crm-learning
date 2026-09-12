package com.gym.crm.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class StorageDataLoader implements BeanPostProcessor {
    private final Environment environment;
    private DataWrapper cachedData;

    public StorageDataLoader(Environment environment) {
        this.environment = environment;
    }

    private void loadDataOnce() {
        if (cachedData != null) {
            return;
        }
        try {
            String storageFile = environment.getProperty("storage.file");
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            InputStream stream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(storageFile);
            if (stream == null) {
                throw new IllegalStateException("Cannot find file: " + storageFile);
            }

            cachedData = mapper.readValue(stream, DataWrapper.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            loadDataOnce();

            if (bean instanceof TraineeStorage traineeStorage) {
                cachedData.getTrainees().forEach(trainee -> traineeStorage.put(trainee.getId(), trainee));
            }

            if (bean instanceof TrainerStorage trainerStorage) {
             cachedData.getTrainers().forEach(trainer -> trainerStorage.put(trainer.getId(), trainer));
            }

            if (bean instanceof TrainingStorage trainingStorage) {
                cachedData.getTrainings().forEach(training -> trainingStorage.put(training.getId(), training));
            }

            return bean;
     }
}
