package by.step.config;

import by.step.repository.GradeRepository;
import by.step.repository.StudentRepository;
import by.step.repository.impl.GradeRepositoryJSON;
import by.step.repository.impl.StudentRepositoryJSON;
import by.step.service.AcademicService;
import by.step.service.impl.AcademicServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.text.SimpleDateFormat;

@Configuration
@ComponentScan(basePackages = "by.step")
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public SimpleDateFormat dateFormat(@Value("${app.date.format}") String pattern) {
        return new SimpleDateFormat(pattern);
    }


}

