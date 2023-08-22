package com.coding.Java.Coding.Challenge2.service;

import com.coding.Java.Coding.Challenge2.dto.Statistics;
import com.coding.Java.Coding.Challenge2.entity.Task;
import com.coding.Java.Coding.Challenge2.entity.UserData;
import com.coding.Java.Coding.Challenge2.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserService userService;

    public void createTask(@RequestBody Task task) {
        taskRepository.save(task);
    }


    public void updateTask(Task task) {
        if ("COMPLETED".equalsIgnoreCase(task.getStatus()))
            task.setCompletedDate(new Date());
        taskRepository.save(task);
    }


    public void deleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
    }


    public List<Task> fetchTasks() {
        return taskRepository.findAll();
    }


    public void assignTask(Long taskId, Long userId) {
        UserData userData = userService.fetchUserById(userId);
        if (userData != null) {
            Task task = fetchTaskById(taskId);
            if (task != null) {
                userData.getTasks().add(task);
                userService.updateUser(userData);
            }
        }
    }

    public void setTaskProgress(Long taskId, Double progress) {
        Task task = fetchTaskById(taskId);
        if (task != null) {
            task.setProgress(progress);
            taskRepository.save(task);
        }
    }


    public List<Task> fetchOverdueTasks() {
        return taskRepository.findAllByDueDateLessThan(new Date());
    }


    public List<Task> fetchTaskByStatus(String status) {
        return taskRepository.findByStatus(status);
    }


    public List<Task> fetchTaskByDateRange(Date startDate, Date endDate) {
       return taskRepository.findAllByCompletedDateLessThanEqualAndCompletedDateGreaterThanEqual(startDate, endDate);
    }

    public Statistics fetchTaskStats() {
        List<Task> taskList = fetchTasks();
        long totCount = taskList.size();
        long completedTaskCount = taskList.stream().filter(task -> "COMPLETED".equalsIgnoreCase(task.getStatus())).count();
        Statistics statistics = new Statistics();
        statistics.setCompletedTasksCount(completedTaskCount);
        statistics.setTotalCount(totCount);
        statistics.setPercentCompletedTasks((completedTaskCount*1.0 / totCount) * 100);
        return statistics;
    }

    private Task fetchTaskById(Long taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        return optionalTask.orElse(null);
    }

}
