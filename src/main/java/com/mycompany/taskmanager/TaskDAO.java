package com.mycompany.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection = null;
    
    private void getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/task_manager",
                        "viktor",
                        "v1i2t3y4a5!");
            }
        } catch (SQLException ex) { }
    }
    
    public void insertTask(Task task) {
        try {
            getConnection();
            
            Statement stmt = connection.createStatement();
            String query = "insert into tasks(name, priority, area, is_finished) values(" + 
                    "'" + task.getName() + "', " +
                    task.getPriority() + ", " +
                    "'" + task.getArea() + "', " +
                    task.isFinished() +
                    ")";
            stmt.executeUpdate(query);
        } catch (SQLException ex) { }
    }
    
    public void updateTask(int id, Task task) {
        try {
            getConnection();
            
            Statement stmt = connection.createStatement();
            String query = "update tasks set name = " + 
                    "'" + task.getName() + "', priority = " +
                    task.getPriority() + ", area = " +
                    "'" + task.getArea() + "', is_finished = " +
                    task.isFinished() +
                    " where id = " + id;
            stmt.executeUpdate(query);
        } catch (SQLException ex) { }
    }
    
    public void deleteTask(int id) {
        try {
            getConnection();
            
            Statement stmt = connection.createStatement();
            String query = "delete from tasks where id = " + id;
            stmt.executeUpdate(query);
        } catch (SQLException ex) { }
    }
    
    public List<Task> selectTasks() {
        List<Task> tasks = new ArrayList<>();
        try {
            getConnection();
            
            Statement stmt = connection.createStatement();
            
            String query = "select * from tasks";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte priority = rs.getByte("priority");
                String area = rs.getString("area");
                boolean finished = rs.getBoolean("is_finished");
                        
                Task task = new Task(id, name, priority, area, finished);
                tasks.add(task);
            }
        } catch (SQLException ex) { }
        return tasks;
    }
    
}
