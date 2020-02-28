package com.project.server.rest.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class MethodArgumentException {
    private String timestamp;
    private int status;
    private String error;
    private List<ObjectError> errors;

    public MethodArgumentException(String timestamp, int status, String error, List<ObjectError> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.errors = errors;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }
}
