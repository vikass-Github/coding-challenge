package com.coding.Java.Coding.Challenge2.controller;

import com.coding.Java.Coding.Challenge2.dto.Statistics;
import com.coding.Java.Coding.Challenge2.entity.Task;
import com.coding.Java.Coding.Challenge2.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public void createTask(@RequestBody Task task) {
        taskService.createTask(task);
    }

    @PutMapping("/{taskId}")
    public void updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping
    public List<Task> fetchTasks() {
        return taskService.fetchTasks();
    }

    @PostMapping("/{taskId}/assign")
    public void assignTask(@PathVariable Long taskId, @RequestBody Long userId) {
        taskService.assignTask(taskId, userId);
    }

    @PutMapping("/{taskId}/progress")
    public void setTaskProgress(@PathVariable Long taskId, Double progress) {
        taskService.setTaskProgress(taskId, progress);
    }

    @GetMapping("/overdue")
    public List<Task> fetchOverdueTasks() {
        return taskService.fetchOverdueTasks();
    }

    @GetMapping("/status/{status}")
    public List<Task> fetchTaskByStatus(@PathVariable String status) {
        return taskService.fetchTaskByStatus(status);
    }

    @GetMapping("/completed")
    public List<Task> fetchTasksCompletedByDateRange(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        return taskService.fetchTaskByDateRange(startDate, endDate);
    }

    @GetMapping("/statistics")
    public Statistics fetchTaskStats() {
        return taskService.fetchTaskStats();
    }

}
