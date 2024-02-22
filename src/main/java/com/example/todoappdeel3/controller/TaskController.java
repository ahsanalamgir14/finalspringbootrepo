package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.TaskDAO;
import com.example.todoappdeel3.dto.TaskDTO;
import com.example.todoappdeel3.models.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskDAO taskDAO;

    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){

        return ResponseEntity.ok(this.taskDAO.getAllTasks());
    }

    @GetMapping(params = "categoryId")
    public ResponseEntity<List<Task>> getTasksByCategory(@RequestParam Long categoryId){

        return ResponseEntity.ok(this.taskDAO.getAllTasksByCategory(categoryId));
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskDTO taskDTO){
        this.taskDAO.createTask(taskDTO);
        return ResponseEntity.ok("Created a task");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        this.taskDAO.updateTask(taskDTO, id);

        return ResponseEntity.ok("Updated task with id" + id);
    }

    @PutMapping("check/{id}")
    public ResponseEntity<String> checkTask(@PathVariable Long id){
        this.taskDAO.checkTask(id);

        return ResponseEntity.ok("Task checked with id " + id);
    }

    @PutMapping("uncheck/{id}")
    public ResponseEntity<String> uncheckTask(@PathVariable Long id){
        this.taskDAO.uncheckTask(id);

        return ResponseEntity.ok("Task uncheck with id " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id){
        this.taskDAO.deleteById(id);

        return ResponseEntity.ok("Task deleted with id " + id);
    }
}
