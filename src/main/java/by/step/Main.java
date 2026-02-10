package by.step;

import by.step.config.ApplicationConfig;
import by.step.controller.MainController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("Созданные бины:");
        for (String beanName : beanNames) {
            System.out.println(" " +beanName);
        }

       MainController mainController = context.getBean(MainController.class);
       mainController.start();
       context.close();
    }
}
