package com.mycompany.taskmanager;

public class Task {
    private int id;
    private String name;
    private byte priority;
    private Area area;
    
    public Task(String name, byte priority, Area area) {
        this.name = name;
        this.priority = priority;
        this.area = area;
    }
    
    public Task(int id, String name, byte priority, Area area) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.area = area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}