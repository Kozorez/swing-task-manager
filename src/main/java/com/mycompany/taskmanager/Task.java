package com.mycompany.taskmanager;

public class Task {
    private String name;
    private byte priority;
    private String area;
    private boolean finished;
    
    public Task() { }
    
    public Task(String name, byte priority, String area, boolean finished) {
        this.name = name;
        this.priority = priority;
        this.area = area;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        if (priority >= 0 && priority <= 10) {
            this.priority = priority;
        }
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        if (area != null && !area.isEmpty()) {
            this.area = area;
        } 
    }
    
    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}