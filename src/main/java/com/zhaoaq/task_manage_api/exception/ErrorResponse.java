package com.zhaoaq.task_manage_api.exception;

public class ErrorResponse {
    private int status;        // HTTP状态码
    private String message;    // 用户友好的错误信息
    private String details;    // (可选) 更详细的错误描述或异常类型
    private long timestamp;    // 时间戳

    public ErrorResponse(int status, String message, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public String getDetails() { return details; }
    public long getTimestamp() { return timestamp; }
}
