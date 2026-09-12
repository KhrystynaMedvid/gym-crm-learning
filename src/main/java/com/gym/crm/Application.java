package com.gym.crm;

import com.gym.crm.config.AppConfig;
import com.gym.crm.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        GymFacade facade = context.getBean(GymFacade.class);

        System.out.println(facade.getTrainerById(1L));
    }
}
