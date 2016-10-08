package com.mycompany.taskmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

public class TaskDAO {

    private Connection connection;
    private Statement statement;

    private void getConnection() {
        try {
            if (connection == null) {
                String password = JOptionPane.showInputDialog("Input your password");
                
                if (password == null) {
                    System.exit(0);
                }
                
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/task_manager",
                        "viktor",
                        password);
            }
        } catch (SQLException ex) {
        }

        if (connection == null) {
            getConnection();
        }
    }

    public void insertTask(Task task) {
        try {
            getConnection();

            statement = connection.createStatement();
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
            getConnection();

            statement = connection.createStatement();
            String query = "update tasks set name = "
                    + "'" + task.getName() + "', priority = "
                    + task.getPriority() + ", area = "
                    + "'" + task.getArea() + "', is_finished = "
                    + task.isFinished()
                    + " where id = " + id;
            statement.executeUpdate(query);
        } catch (SQLException ex) {
        }
    }

    public Map<Integer, Task> selectTasks() {
        Map<Integer, Task> tasks = new HashMap<>();

        try {
            getConnection();

            statement = connection.createStatement();
            String query = "select * from tasks";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                byte priority = rs.getByte("priority");
                String area = rs.getString("area");
                boolean finished = rs.getBoolean("is_finished");

                Task task = new Task(name, priority, area, finished);
                tasks.put(id, task);
            }
        } catch (SQLException ex) {
        }
        return tasks;
    }
}
