package com.codesprint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.codesprint.model.Task;
import com.codesprint.repository.TaskRepository;
import java.util.List;
import java.util.Optional;

/**
 * Service class for Task business logic.
 * Handles CRUD operations and business rules for tasks.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Add a new task to the database.
     * 
     * @param task the task to be added
     * @return the saved task with generated ID
     */
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieve all tasks from the database.
     * 
     * @return list of all tasks
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Retrieve a specific task by ID.
     * 
     * @param taskId the ID of the task to retrieve
     * @return Optional containing the task if found, empty otherwise
     */
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    /**
     * Update an existing task.
     * 
     * @param taskId the ID of the task to update
     * @param updatedTask the updated task data
     * @return the updated task, or null if task not found
     */
    public Task updateTask(Long taskId, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            
            // Update fields if provided
            if (updatedTask.getTitle() != null) {
                task.setTitle(updatedTask.getTitle());
            }
            if (updatedTask.getDescription() != null) {
                task.setDescription(updatedTask.getDescription());
            }
            if (updatedTask.getStatus() != null) {
                task.setStatus(updatedTask.getStatus());
            }
            
            return taskRepository.save(task);
        }
        
        return null;
    }

    /**
     * Delete a task by ID.
     * 
     * @param taskId the ID of the task to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
            return true;
        }
        return false;
    }

    /**
     * Mark a task as completed.
     * 
     * @param taskId the ID of the task to complete
     * @return the completed task, or null if task not found
     */
    public Task completeTask(Long taskId) {
        Optional<Task> existingTask = taskRepository.findById(taskId);
        
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setStatus("COMPLETED");
            return taskRepository.save(task);
        }
        
        return null;
    }

}
