package com.gym.crm.service;

import com.gym.crm.dao.TraineeDao;
import com.gym.crm.dao.TrainerDao;
import com.gym.crm.model.Trainee;
import com.gym.crm.storage.TraineeStorage;
import com.gym.crm.util.UsernamePasswordGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
    @Mock
    private TraineeDao traineeDao;
    @Mock
    private TrainerDao trainerDao;
    @Mock
    private TraineeStorage traineeStorage;
    @Mock
    private UsernamePasswordGenerator usernamePasswordGenerator;
    @InjectMocks
    private TraineeService traineeService;

    private Trainee validTrainee(){
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Smith");
        return trainee;
    }

    @Test
    void createTrainee_success(){
        Trainee trainee = validTrainee();

        when(traineeStorage.generateId()).thenReturn(1L);
        when(usernamePasswordGenerator.generateUsername(anyString(), anyString(), any()))
                .thenReturn("John.Doe");
        when(usernamePasswordGenerator.generatePassword()).thenReturn("abnjuehl123");

        Trainee result = traineeService.createTrainee(trainee);

        assertEquals(1L, result.getId());
        assertEquals("John.Doe", result.getUsername());
        assertEquals("abnjuehl123", result.getPassword());
        assertTrue(result.isActive());

        verify(traineeDao).save(result);
    }

    @Test
    void createTrainee_nullTrainee_throwException(){
        assertThrows(IllegalArgumentException.class,
                () -> traineeService.createTrainee(null));

        verifyNoInteractions(traineeDao);
    }

    @Test
    void createTrainee_emptyFirstName_throwException(){
        Trainee trainee = validTrainee();
        trainee.setFirstName("");

        assertThrows(IllegalArgumentException.class, () -> traineeService.createTrainee(trainee));
    }

    @Test
    void createTrainee_emptyLastName_throwException(){
        Trainee trainee = validTrainee();
        trainee.setLastName("");

        assertThrows(IllegalArgumentException.class, () -> traineeService.createTrainee(trainee));
    }

    @Test
    void createTrainee_usernameExistsInTrainerDao_checksAvailability(){
        Trainee trainee = validTrainee();

        when(trainerDao.exists("John.Doe")).thenReturn(true);
        when(usernamePasswordGenerator.generateUsername(anyString(), anyString(), any()))
                .thenAnswer(invocation -> {
                    Function<String, Boolean> checker = invocation.getArgument(2);
                    assertTrue(checker.apply("John.Doe"));
                    return "John.Doe1";
                });
        when(traineeStorage.generateId()).thenReturn(1L);
        when(usernamePasswordGenerator.generatePassword()).thenReturn("password");

        Trainee result = traineeService.createTrainee(trainee);
        assertEquals("John.Doe1", result.getUsername());
    }

    @Test
    void createTrainee_usernameExistsInTraineeDao_checksAvailability() {
        Trainee trainee = validTrainee();

        when(traineeDao.exists("John.Doe")).thenReturn(true);
        when(usernamePasswordGenerator.generateUsername(anyString(), anyString(), any()))
                .thenAnswer(invocation -> {
                    Function<String, Boolean> cheсker = invocation.getArgument(2);
                    assertTrue(cheсker.apply("John.Doe"));
                    return "John.Doe1";
                });
        when(traineeStorage.generateId()).thenReturn(1L);
        when(usernamePasswordGenerator.generatePassword()).thenReturn("password");

        Trainee result = traineeService.createTrainee(trainee);
        assertEquals("John.Doe1", result.getUsername());
    }

    @Test
    void updateTrainee_success(){
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("John.Doe");

        traineeService.updateTrainee(trainee);

        verify(traineeDao).update(trainee);
    }

    @Test
    void updateTrainee_nullTrainee_throwException(){
        assertThrows(IllegalArgumentException.class,
                () -> traineeService.updateTrainee(null));
    }

    @Test
    void updateTrainee_withoutId_throwException(){
        Trainee trainee = new Trainee();
        trainee.setUsername("Marko.Polo");

        assertThrows(IllegalArgumentException.class, () -> traineeService.updateTrainee(trainee));
    }

    @Test
    void updateTrainee_emptyUsername_throwException(){
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("");

        assertThrows(IllegalArgumentException.class, () -> traineeService.updateTrainee(trainee));
    }

    @Test
    void deleteTrainee_success(){
        traineeService.deleteTrainee(1L);

        verify(traineeDao).delete(1L);
    }

    @Test
    void deleteTrainee_nullId_throwException(){
        assertThrows(IllegalArgumentException.class, () ->  traineeService.deleteTrainee(null));
    }

    @Test
    void getById_found_returnsTrainee(){
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("John.Doe");

        when(traineeDao.findById(1L)).thenReturn(trainee);

        Trainee result = traineeService.getById(1L);

        assertEquals(trainee, result);
    }

    @Test
    void getById_notFound_returnsNull() {
        when(traineeDao.findById(1L)).thenReturn(null);

        assertNull(traineeService.getById(1L));
    }

    @Test
    void getById_nullId_throwException(){
        assertThrows(IllegalArgumentException.class, () -> traineeService.getById(null));
    }

    @Test
    void getByUsername_found_returnsTrainee(){
        Trainee trainee = new Trainee();
        trainee.setId(1L);
        trainee.setUsername("John.Doe");

        when(traineeDao.findByUsername("John.Doe")).thenReturn(trainee);

        Trainee result = traineeService.getByUsername("John.Doe");

        assertEquals(trainee, result);
    }

    @Test
    void getByUsername_notFound_returnsNull() {
        when(traineeDao.findByUsername("John.Doe")).thenReturn(null);
        assertNull(traineeService.getByUsername("John.Doe"));
    }

    @Test
    void getByUsername_emptyUsername_throwException(){
        assertThrows(IllegalArgumentException.class, () -> traineeService.getByUsername(""));
    }

}