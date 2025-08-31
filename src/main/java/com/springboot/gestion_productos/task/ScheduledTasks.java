package com.springboot.gestion_productos.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@EnableScheduling //  programación de tareas
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    
    // Se ejecuta cada minuto
    @Scheduled(fixedRate = 60000) 
    public void reportCurrentTime() {
        log.info("La hora actual es {}", LocalDateTime.now());
    }
}