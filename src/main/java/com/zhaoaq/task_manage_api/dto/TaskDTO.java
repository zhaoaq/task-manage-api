package com.zhaoaq.task_manage_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    public Long id;
    private String title;
    private String description;
    private boolean completed;

//    // 无参构造方法
//    public TaskDTO(){}
//
//    // 有参构造方法
//    public TaskDTO(Long id, String title, String description, boolean completed) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.completed = completed;
//    }

//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getTitle() { return title; }
//    public void setTitle(String title) { this.title = title; }
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//    public boolean isCompleted() { return completed; }
//    public void setCompleted(boolean completed) { this.completed = completed; }



}
