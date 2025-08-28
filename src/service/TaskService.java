package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TaskService {

    private final TaskRepository repository;

    // Demonstrating Data Structures
    private final List<Task> taskList = new ArrayList<>(); // List
    private final Queue<Task> taskQueue = new LinkedList<>(); // Queue
    private final Map<Long, Task> taskMap = new HashMap<>(); // Map
    private final String[] categories = {"Work", "Personal", "Urgent"}; // Array

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task addTask(Task task) {
        Task savedTask = repository.save(task);

        // Use Data Structures
        taskList.add(savedTask);             // List
        taskQueue.offer(savedTask);          // Queue
        taskMap.put(savedTask.getId(), savedTask); // Map

        return savedTask;
    }

    public Optional<Task> getTaskById(Long id) {
        return repository.findById(id);
    }

    public Task updateTask(Long id, Task newTask) {
        return repository.findById(id).map(task -> {
            task.setTitle(newTask.getTitle());
            task.setDescription(newTask.getDescription());
            task.setTags(newTask.getTags());
            return repository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public void deleteTask(Long id) {
        repository.deleteById(id);
        taskMap.remove(id); // Maintain map consistency
    }

    // Demo method: show categories from Array
    public String[] getCategories() {
        return categories;
    }

    // Demo method: process next task in queue
    public Task processNextTask() {
        return taskQueue.poll();
    }
}
