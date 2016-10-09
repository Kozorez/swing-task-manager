package com.mycompany.taskmanager;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TaskFrame extends JFrame {

    private static TaskDAO dao = new TaskDAO();

    public TaskFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }

        initComponents();

        table.getTableHeader().setFont(new java.awt.Font("Ubuntu", 1, 24));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        table.setDefaultRenderer(Object.class, renderer);

        table.removeColumn(table.getColumnModel().getColumn(0));

        dao.startSession();
        generateTable();
        dao.endSession();
    }

    private void generateTable() {
        while (table.getModel().getRowCount() > 0) {
            ((DefaultTableModel) table.getModel()).removeRow(0);
        }

        Map<Integer, Task> tasks = dao.selectTasks();
        
        Object[][] modelData = new Object[tasks.size()][5];
        int count = 0;
        for (int id : tasks.keySet()) {
            modelData[count][0] = id;
            modelData[count][1] = tasks.get(id).getName();
            modelData[count][2] = tasks.get(id).getPriority();
            modelData[count][3] = tasks.get(id).getArea();
            modelData[count][4] = tasks.get(id).isFinished();

            count++;
        }

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (Object[] task : modelData) {
            model.addRow(new Object[]{task[0], task[1], task[2], task[3], task[4]});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        addRowMenu = new javax.swing.JMenu();
        deleteTaskMenu = new javax.swing.JMenu();
        saveChangesMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Task Manager");
        setExtendedState(6);

        table.setAutoCreateRowSorter(true);
        table.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Priority", "Area", "Is Finished"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        scrollPane.setViewportView(table);

        addRowMenu.setText("Add Row");
        addRowMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRowMenuMouseClicked(evt);
            }
        });
        menuBar.add(addRowMenu);

        deleteTaskMenu.setText("Delete Task");
        deleteTaskMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteTaskMenuMouseClicked(evt);
            }
        });
        menuBar.add(deleteTaskMenu);

        saveChangesMenu.setText("Save Changes");
        saveChangesMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveChangesMenuMouseClicked(evt);
            }
        });
        menuBar.add(saveChangesMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRowMenuMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            addRowMenu.setSelected(false);
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{null, null, null, null, false});
        }
    }//GEN-LAST:event_addRowMenuMouseClicked

    private void saveChangesMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveChangesMenuMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            saveChangesMenu.setSelected(false);
            
            dao.startSession();
            
            Map<Integer, Task> tasks = dao.selectTasks();
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                Task task = new Task();
                task.setName((String) table.getModel().getValueAt(i, 1));
                task.setPriority((int) table.getModel().getValueAt(i, 2));
                task.setArea((String) table.getModel().getValueAt(i, 3));
                task.setFinished((boolean) table.getModel().getValueAt(i, 4));

                if (table.getModel().getValueAt(i, 0) != null) {
                    dao.updateTask((int) table.getModel().getValueAt(i, 0), task);
                    tasks.remove(table.getModel().getValueAt(i, 0));
                } else {
                    dao.insertTask(task);
                }
            }
            
            tasks.keySet().stream().forEach((id) -> {
                dao.deleteTask(id);
            });

            generateTable();
            
            dao.endSession();
        }
    }//GEN-LAST:event_saveChangesMenuMouseClicked

    private void deleteTaskMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteTaskMenuMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            addRowMenu.setSelected(false);
            
            int[] selectedRows = table.getSelectedRows();
            int count = selectedRows.length - 1;
            
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (count >= 0) {
                model.removeRow(selectedRows[count]);
                count--;
            }
        }
    }//GEN-LAST:event_deleteTaskMenuMouseClicked

    public static void main(String args[]) {
        EventQueue.invokeLater(() -> {
            new TaskFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu addRowMenu;
    private javax.swing.JMenu deleteTaskMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu saveChangesMenu;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
