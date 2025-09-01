package com.zhaoaq.task_manage_api.service;

import com.zhaoaq.task_manage_api.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

/**
 * 任务业务逻辑服务接口
 * 定义了任务管理的核心操作，是Controller层和数据访问层之间的桥梁。
 */
public interface TaskService {

    /**
     * 获取所有任务
     * @return 任务列表
     */
    List<TaskDTO> getAllTasks();

    /**
     * 根据ID查找任务
     * @param id 任务ID
     * @return 包含任务的Optional，如果找不到则为空
     */
    Optional<TaskDTO> getTaskById(Long id);

    /**
     * 创建一个新任务
     * @param TaskDto 要创建的任务对象 (不含ID)
     * @return 创建成功并带有新ID的任务对象
     */
    TaskDTO createTask(TaskDTO TaskDto);

    /**
     * 更新一个已存在的任务
     * @param id 要更新的任务ID
     * @param TaskDtoDetails 包含更新信息的任务对象
     * @return 更新后的任务对象的Optional，如果任务不存在则为空
     */
    Optional<TaskDTO> updateTask(Long id, TaskDTO TaskDtoDetails);

    /**
     * 根据ID删除任务
     * @param id 任务ID
     * @return 如果删除成功返回true，否则false
     */
    boolean deleteTask(Long id);

    /**
     * 根据关键词搜索任务
     * @param keyword 搜索关键词
     * @return 匹配的任务列表
     */
    List<TaskDTO> searchTasks(String keyword);
}
