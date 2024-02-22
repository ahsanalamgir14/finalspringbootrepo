package com.example.todoappdeel3.utils;

import com.example.todoappdeel3.dao.TaskDAO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Task;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Seeder {
    private TaskDAO taskDAO;

    public Seeder(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event){
        this.seedTasks();
    }

    private void seedTasks(){
        Category category = new Category("Study tasks");
        Task task1 = new Task("Angular video's kijken", "Week 1 en week 2 video's van angular kijken", category);
        Task task2 = new Task("Angular opdrachten maken", "Week 1 en week 2 opdrachten van angular maken", category);
        this.taskDAO.createTask(task1);
        this.taskDAO.createTask(task2);
    }
}
