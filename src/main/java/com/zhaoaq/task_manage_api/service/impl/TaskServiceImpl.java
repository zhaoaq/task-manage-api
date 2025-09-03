package com.zhaoaq.task_manage_api.service.impl;

import com.zhaoaq.task_manage_api.dto.TaskDTO;
import com.zhaoaq.task_manage_api.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    // 1. 从Controller迁移过来的数据存储和ID生成器
    private final List<TaskDTO> tasks = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    // 2. 从Controller迁移过来的构造函数，用于初始化数据
    public TaskServiceImpl() {
        tasks.add(new TaskDTO(counter.incrementAndGet(), "学习Spring Boot", "完成Spring Boot入门章节", false));
        tasks.add(new TaskDTO(counter.incrementAndGet(), "编写API文档", "为任务管理API编写初始文档", false));
        tasks.add(new TaskDTO(counter.incrementAndGet(), "晨跑", "早上7点进行3公里晨跑", true));
    }

    // 3. 逐个实现接口方法，将Controller的逻辑搬运过来
    @Override
    public List<TaskDTO> getAllTasks() {
        System.out.println("Executing Service: getAllTasks. Current tasks count: " + tasks.size());
        return tasks;
    }

    @Override
    public Optional<TaskDTO> getTaskById(Long id) {
        System.out.println("Executing Service: getTaskById for ID: " + id);

        // 模拟一个异常 if(true) throw new NullPointerException();

        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    /**
     * 简化版本
     */
    @Override
    public TaskDTO getTaskByIdSimplified(Long id) {
        System.out.println("Executing Service: getTaskById for ID: " + id);

        for (TaskDTO task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    @Override
    public List<TaskDTO> searchTasks(String keyword) {
        System.out.println("Executing Service: searchTasks with keyword: " + keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return tasks;
        }
        String lowerCaseKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(task -> task.getTitle().toLowerCase().contains(lowerCaseKeyword))
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO createTask(TaskDTO newTaskDto) {
        System.out.println("Executing Service: createTask. Received DTO title: " + newTaskDto.getTitle());

        // 完全照搬Controller的逻辑
        Long newId = counter.incrementAndGet();
        TaskDTO taskToCreate = new TaskDTO();
        taskToCreate.setId(newId);
        taskToCreate.setTitle(newTaskDto.getTitle());
        taskToCreate.setDescription(newTaskDto.getDescription());
        taskToCreate.setCompleted(newTaskDto.isCompleted());
        tasks.add(taskToCreate);
        System.out.println("Service: Task created with ID: " + newId);

        // 返回创建好的对象
        return taskToCreate;
    }

    @Override
    public Optional<TaskDTO> updateTask(Long id, TaskDTO updatedTaskDto) {
        System.out.println("Executing Service: updateTask for ID: " + id);

        // 完全照搬Controller的 for 循环查找逻辑
        TaskDTO existingTask = null;
        for (TaskDTO task : tasks) {
            if (task.getId().equals(id)) {
                existingTask = task;
                break;
            }
        }

        if (existingTask != null) {
            existingTask.setTitle(updatedTaskDto.getTitle());
            existingTask.setDescription(updatedTaskDto.getDescription());
            existingTask.setCompleted(updatedTaskDto.isCompleted());
            System.out.println("Service: Task with ID " + id + " updated.");
            // 找到并更新后，用Optional包装返回
            return Optional.of(existingTask);
        } else {
            System.out.println("Service: Task with ID " + id + " not found for update.");
            // 没找到，返回空的Optional
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteTask(Long id) {
        System.out.println("Executing Service: deleteTask for ID: " + id);

        // 完全照搬Controller的 for 循环删除逻辑
        boolean removed = false;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(id)) {
                tasks.remove(i);
                removed = true;
                break;
            }
        }

        if (removed) {
            System.out.println("Service: Task with ID " + id + " deleted. Remaining tasks count: " + tasks.size());
        } else {
            System.out.println("Service: Task with ID " + id + " not found for deletion.");
        }

        // 返回删除结果
        return removed;
    }
}
