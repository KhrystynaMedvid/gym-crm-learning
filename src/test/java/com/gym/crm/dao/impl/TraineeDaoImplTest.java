package com.gym.crm.dao.impl;

import com.gym.crm.model.Trainee;
import com.gym.crm.storage.TraineeStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TraineeDaoImplTest {
    private TraineeDaoImpl traineeDao;
    private TraineeStorage traineeStorage;

    @BeforeEach
    void setUp(){
        traineeStorage = new TraineeStorage();
        traineeDao = new TraineeDaoImpl();
        traineeDao.setTraineeStorage(traineeStorage);
    }

    private Trainee createTrainee(Long id, String usernsme){
        Trainee trainee = new Trainee();
        trainee.setId(id);
        trainee.setFirstName("John");
        trainee.setLastName("Smith");
        trainee.setUsername(usernsme);
        return trainee;
    }

    @Test
    public void save_validTrainee_addsTraineeToStorage(){
        Trainee trainee = createTrainee(1L, "John.Smith");
        traineeDao.save(trainee);

        Assertions.assertEquals(1, traineeStorage.getStorage().size());
        Assertions.assertEquals(trainee, traineeStorage.findById(1L));
    }

    @Test
    public void save_multipleTrainees_storesAllTrainees(){
        Trainee trainee1 = createTrainee(1L, "John.Smith");
        Trainee trainee2 = createTrainee(2L, "Marry.Smith");

        traineeDao.save(trainee1);
        traineeDao.save(trainee2);

        Assertions.assertEquals(2, traineeStorage.getStorage().size());
    }

    @Test
    public void findById_existingId_returnsTrainee(){
        Trainee trainee = createTrainee(1L, "John.Smith");

        traineeStorage.put(trainee.getId(), trainee);

        Assertions.assertEquals(trainee, traineeDao.findById(1L));
    }

    @Test
    public void findById_unknownId_returnsNull(){
        Assertions.assertNull(traineeDao.findById(2L));
    }

    @Test
    public void findByUsername_existingUsername_returnsTrainee(){
        Trainee trainee = createTrainee(1L, "John.Smith");
        traineeStorage.put(trainee.getId(), trainee);

        Trainee actual = traineeDao.findByUsername("John.Smith");

        Assertions.assertEquals(trainee, actual);
    }

    @Test
    public void findByUsername_unknownUsername_returnsNull(){
        Assertions.assertNull(traineeDao.findByUsername("Marry.Smith"));
    }

    @Test
    public void delete_existingId_removesTrainee(){
        Trainee trainee = createTrainee(1L, "John.Smith");
        traineeStorage.put(trainee.getId(), trainee);

        traineeDao.delete(1L);

        Assertions.assertFalse(traineeStorage.getStorage().containsKey(trainee.getId()));
    }

    @Test
    public void update_existingTrainee_updatesData(){
        Trainee trainee = createTrainee(1L, "John.Smith");
        traineeStorage.put(trainee.getId(), trainee);

        trainee.setFirstName("Marry");
        traineeDao.update(trainee);

        Assertions.assertEquals("Marry", traineeStorage.findById(1L).getFirstName());
    }

    @Test
    public void update_nonExistingTrainee_throwsException(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> traineeDao.update( createTrainee(1L, "Marry.Smith")));
    }

    @Test
    public void exists_existingUsername_returnsTrue(){
        Trainee trainee = createTrainee(1L, "John.Smith");
        traineeStorage.put(trainee.getId(), trainee);

        Assertions.assertTrue(traineeDao.exists("John.Smith"));
    }

    @Test
    public void exists_unknownUsername_returnsFalse(){
        Assertions.assertFalse(traineeDao.exists("John.Smith"));
    }


}