package com.zhaoaq.task_manage_api.controller;

import com.zhaoaq.task_manage_api.dto.TaskDTO;
import com.zhaoaq.task_manage_api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {
    // 构造器注入service
    private final TaskService taskService;

    // 构造函数
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)// .map方法 “如果有值，就对它做某事”。
                .orElseGet(() -> ResponseEntity.notFound().build()); // 如果没值，就做某事并返回结果
    }

    @GetMapping("/search")
    public List<TaskDTO> searchTasks(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        return taskService.searchTasks(keyword);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO newTaskDTO) {
        // 调用service创建任务
        TaskDTO createdTask = taskService.createTask(newTaskDTO);
        // 返回201 Created状态码和创建的对象
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO updatedTaskDTO) {
        return taskService.updateTask(id, updatedTaskDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        // service返回boolean，controller根据结果返回不同状态码
        if (taskService.deleteTask(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}

