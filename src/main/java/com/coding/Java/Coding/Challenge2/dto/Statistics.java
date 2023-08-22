package com.coding.Java.Coding.Challenge2.dto;

public class Statistics {

    long totalCount;
    long completedTasksCount;
    double percentCompletedTasks;

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCompletedTasksCount() {
        return completedTasksCount;
    }

    public void setCompletedTasksCount(long completedTasksCount) {
        this.completedTasksCount = completedTasksCount;
    }

    public double getPercentCompletedTasks() {
        return percentCompletedTasks;
    }

    public void setPercentCompletedTasks(double percentCompletedTasks) {
        this.percentCompletedTasks = percentCompletedTasks;
    }
}
