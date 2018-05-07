package com.asa.taskscheduler;

/**
 * Created by Phant on 1/6/2018.
 */

public class Task {

    String name;
    String startTime;
    long duration;
    String key;
    int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Task() {
    }

    public Task(String name, String startTime, long duration, int i) {
        this.name = name;
        this.startTime = startTime;
        this.duration = duration;
        this.i = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return getName();
    }
}