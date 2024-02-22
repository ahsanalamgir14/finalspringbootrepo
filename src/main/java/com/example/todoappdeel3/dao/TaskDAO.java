package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.TaskDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Task;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class TaskDAO {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskDAO(TaskRepository repository, CategoryRepository category) {
        this.taskRepository = repository;
        this.categoryRepository = category;
    }

    public List<Task> getAllTasks(){
        return this.taskRepository.findAll();
    }

    public List<Task> getAllTasksByCategory(long id){
        Optional<List<Task>> tasks =this.taskRepository.findByCategoryId(id);

        if (tasks.get().isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No tasks found with that category id"
            );
        }

        return tasks.get();
    }

    @Transactional
    public void createTask(TaskDTO taskDTO){
        Optional<Category> category = this.categoryRepository.findById(taskDTO.categoryId);
        if (category.isPresent()){
            Task task = new Task(taskDTO.name, taskDTO.description, category.get());
            this.taskRepository.save(task);
            return;
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Category not found"
        );
    }

    @Transactional
    public void createTask(Task task){
        this.categoryRepository.save(task.getCategory());
        this.taskRepository.save(task);
    }

    public void updateTask(TaskDTO taskDTO, Long id){
        Optional<Task> task = this.taskRepository.findById(id);

        if (task.isPresent()){
            task.get().setDescription(taskDTO.description);
            task.get().setName(taskDTO.name);

            this.taskRepository.save(task.get());
        }
    }

    public void checkTask(Long id) {
        this.toggleTask(id, true);
    }

    public void uncheckTask(Long id) {
        this.toggleTask(id, false);
    }

    private void toggleTask(Long id, boolean value){
        Optional<Task> task = this.taskRepository.findById(id);

        if (task.isPresent()){
            task.get().setFinished(value);
            this.taskRepository.save(task.get());
        }
    }

    public void deleteById(Long id) {
        this.taskRepository.deleteById(id);
    }
}
