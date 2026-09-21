package com.gym.crm;

import com.gym.crm.config.AppConfig;
import com.gym.crm.config.DataLoader;
import com.gym.crm.entity.Trainee;
import com.gym.crm.entity.User;
import com.gym.crm.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.TimeZone;

public class Application {
    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kyiv"));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade facade = context.getBean(GymFacade.class);
        DataLoader dataLoader = context.getBean(DataLoader.class);
        dataLoader.load();

        System.out.println(facade.getTraineeById(1L));

    }
}
