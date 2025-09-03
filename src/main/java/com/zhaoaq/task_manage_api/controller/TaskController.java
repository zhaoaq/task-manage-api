package com.zhaoaq.task_manage_api.controller;

import com.zhaoaq.task_manage_api.dto.TaskDTO;
import com.zhaoaq.task_manage_api.exception.ResourceNotFoundException;
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

        // 模拟参数校验
        if (id < 0) {
            throw new IllegalArgumentException("任务ID不能为负数: " + id);
        }

        // 调用Service获取结果
        TaskDTO task = taskService.getTaskByIdSimplified(id);

        // 使用传统的if-else判断来处理Optional
        if (task != null) {
            // 如果找到了任务，获取任务对象并返回
            return ResponseEntity.ok(task);
        } else {
            // 如果没有找到任务，抛出自定义异常
            throw new ResourceNotFoundException("ID为 " + id + " 的任务未找到");
        }
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

