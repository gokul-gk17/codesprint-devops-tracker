package com.codesprint.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.codesprint.model.Task;
import com.codesprint.service.TaskService;

/**
 * Controller class for Task management.
 * Handles HTTP requests and returns Thymeleaf templates.
 */
@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Display the home page.
     * 
     * @param model the model to add attributes for Thymeleaf
     * @return the view name "index"
     */
    @GetMapping("/")
    public String home(Model model) {

        var tasks = taskService.getAllTasks();

        long completedTasks = tasks.stream()
                .filter(task -> "COMPLETED".equals(task.getStatus()))
                .count();

        long pendingTasks = tasks.size() - completedTasks;

        int completionPercentage = tasks.size() > 0
                ? (int) ((completedTasks * 100) / tasks.size())
                : 0;

        model.addAttribute("tasks", tasks);
        model.addAttribute("totalTasks", tasks.size());
        model.addAttribute("completedTasks", completedTasks);
        model.addAttribute("pendingTasks", pendingTasks);
        model.addAttribute("completionPercentage", completionPercentage);

        return "index";
    }

    /**
     * Display all tasks.
     * 
     * @param model the model to add attributes for Thymeleaf
     * @return the view name "tasks"
     */
    @GetMapping("/tasks")
    public String getAllTasks(Model model) {
        // Add all tasks to the model
        model.addAttribute("tasks", taskService.getAllTasks());
        // Add empty task object for the form
        model.addAttribute("task", new Task());
        return "tasks";
    }

    /**
     * Handle task creation from form submission.
     * 
     * @param task the task object populated from form data
     * @return redirect to tasks page
     */
    @PostMapping("/tasks")
    public String addTask(Task task) {
        // Save the task
        if (task.getTitle() != null && !task.getTitle().isEmpty()) {
            task.setStatus("PENDING"); // Default status
            task.setCreatedDate(LocalDate.now());
            taskService.addTask(task);
        }
        // Redirect to tasks page to see the new task
        return "redirect:/tasks";
    }

    /**
     * Delete a task by ID.
     * 
     * @param id the ID of the task to delete
     * @return redirect to tasks page
     */
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        // Delete the task
        taskService.deleteTask(id);
        // Redirect to tasks page
        return "redirect:/tasks";
    }

    /**
     * Mark a task as completed.
     * 
     * @param id the ID of the task to complete
     * @return redirect to tasks page
     */
    @GetMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id) {
        // Mark the task as completed
        taskService.completeTask(id);
        // Redirect to tasks page
        return "redirect:/tasks";
    }

}
