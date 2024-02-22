package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//maps the task class to the database using the Long type as default of ID's
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<List<Task>> findByCategoryId(long id);
}
