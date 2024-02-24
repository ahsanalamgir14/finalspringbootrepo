package com.example.todoappdeel3.utils;

import com.example.todoappdeel3.dao.TaskDAO;
import com.example.todoappdeel3.dao.UserRepository;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.Task;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Seeder {
    private TaskDAO taskDAO;
    private UserRepository userRepository;

    public Seeder(TaskDAO taskDAO, UserRepository userRepository) {
        this.taskDAO = taskDAO;
        this.userRepository = userRepository;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        this.seedTasks();
        this.seedUser();
    }

    private void seedTasks(){
        Category category = new Category("Study tasks");
        Task task1 = new Task("Angular video's kijken", "Week 1 en week 2 video's van angular kijken", category);
        Task task2 = new Task("Angular opdrachten maken", "Week 1 en week 2 opdrachten van angular maken", category);
        this.taskDAO.createTask(task1);
        this.taskDAO.createTask(task2);
    }

    private void seedUser(){
        CustomUser customUser = new CustomUser();
        customUser.setEmail("test@mail.com");
        customUser.setPassword(new BCryptPasswordEncoder().encode("Test123!"));
        userRepository.save(customUser);
    }
}
