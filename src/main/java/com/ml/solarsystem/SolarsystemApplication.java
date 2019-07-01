package com.ml.solarsystem;

import java.util.concurrent.Executor;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
@EnableScheduling
public class SolarsystemApplication {

	public static void main(String[] args) {
         SpringApplication.run(SolarsystemApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("WeatherJob-");
        executor.initialize();
        return executor;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    
}
