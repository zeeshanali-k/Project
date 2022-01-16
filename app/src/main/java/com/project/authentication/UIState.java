package com.project.authentication;

public class UIState {
    private boolean isLoading;
    private boolean isAuthenticated;
    private String error = null;

    public boolean isLoading() {
        return isLoading;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public UIState() {
    }

    public UIState(boolean isLoading, boolean isAuthenticated) {
        this.isLoading = isLoading;
        this.isAuthenticated = isAuthenticated;
    }

    public UIState(boolean isLoading, boolean isAuthenticated, String error) {
        this.isLoading = isLoading;
        this.isAuthenticated = isAuthenticated;
        this.error = error;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
