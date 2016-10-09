package com.mycompany.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

// TODO cache of tasks
// TODO password hiding

public class TaskDAO {

    private Connection connection;
    private Statement statement;

    public void startSession() {
        try {
            String password = JOptionPane.showInputDialog("Enter your password");

            if (password == null) {
                System.exit(0);
            }

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/task_manager",
                    "viktor",
                    password);

            if (connection == null) {
                startSession();
            }
            
            statement = connection.createStatement();
        } catch (SQLException ex) {
        }
    }
    
    public void endSession() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
        }
    }
    
    public Map<Integer, Task> selectTasks() {
        Map<Integer, Task> tasks = new HashMap<>();

        try {
            String query = "select * from tasks";
            
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int priority = resultSet.getInt("priority");
                String area = resultSet.getString("area");
                boolean finished = resultSet.getBoolean("is_finished");

                Task task = new Task(name, priority, area, finished);
                tasks.put(id, task);
            }
        } catch (SQLException ex) {
        }
        
        return tasks;
    }
    
    public void insertTask(Task task) {
        try {
            String query = "insert into tasks(name, priority, area, is_finished) values("
                    + "'" + task.getName() + "', "
                    + task.getPriority() + ", "
                    + "'" + task.getArea() + "', "
                    + task.isFinished()
                    + ")";
            
            statement.executeUpdate(query);
        } catch (SQLException ex) {
        }
    }

    public void updateTask(int id, Task task) {
        try {
            String query = "update tasks set "
                    + "name = " + "'" + task.getName() + "', "
                    + "priority = " + task.getPriority() + ", "
                    + "area = " + "'" + task.getArea() + "', "
                    + "is_finished = " + task.isFinished() + " "
                    + "where id = " + id;
            
            statement.executeUpdate(query);
        } catch (SQLException ex) {
        }
    }

    public void deleteTask(int id) {
        try {
            String query = "delete from tasks where id = " + id;
            
            statement.executeUpdate(query);
        } catch (SQLException ex) {
        }
    }
}
