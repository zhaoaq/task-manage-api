package com.zhaoaq.task_manage_api.exception;

// 继承运行时异常是为了不要到处catch try 只在运行的时候捕获
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
