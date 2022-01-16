package com.project;

import com.project.models.User;

import java.util.List;

class UsersState{
    private String error=null;
    private boolean success;
    private List<User> users;
    private boolean isLoading = false;

    public UsersState(boolean success, List<User> users) {
        this.success = success;
        this.users = users;
    }

    public UsersState(String error, boolean success, List<User> users, boolean isLoading) {
        this.error = error;
        this.success = success;
        this.users = users;
        this.isLoading = isLoading;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public UsersState(String error, boolean success) {
        this.error = error;
        this.success = success;
    }

    public UsersState(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public UsersState(String error, boolean success, List<User> users) {
        this.error = error;
        this.success = success;
        this.users = users;
    }
}