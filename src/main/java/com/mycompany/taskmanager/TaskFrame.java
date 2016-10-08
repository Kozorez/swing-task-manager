package com.mycompany.taskmanager;

import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;

public class TaskFrame extends javax.swing.JFrame {
    private static TaskDAO dao = new TaskDAO();
    
    public TaskFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) 
        { }
        
        initComponents();
        
        table.getTableHeader().setFont(new java.awt.Font("Ubuntu", 1, 24));
        
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        table.setDefaultRenderer(Object.class, renderer);
        
        table.removeColumn(table.getColumnModel().getColumn(0));
        
        generateTable();
    }
    
    private void generateTable() {
        while (table.getModel().getRowCount() > 0) {
            ((javax.swing.table.DefaultTableModel)table.getModel()).removeRow(0);
        }
        
        Map<Integer, Task> tasks = dao.selectTasks();
        int rowsCount = tasks.size();
        Object[][] modelData = new Object[rowsCount][5];
        int count = 0;
        for (int id : tasks.keySet()) {
            modelData[count][0] = id;
            modelData[count][1] = tasks.get(id).getName();
            modelData[count][2] = tasks.get(id).getPriority();
            modelData[count][3] = tasks.get(id).getArea();
            modelData[count][4] = tasks.get(id).isFinished();
            
            count++;
        }
        
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        for (int i = 0; i < rowsCount; i++) {
            model.addRow(new Object[] {modelData[i][0], 
                                       modelData[i][1], 
                                       modelData[i][2], 
                                       modelData[i][3],
                                       modelData[i][4]});
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        addRowMenu = new javax.swing.JMenu();
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
                java.lang.Integer.class, java.lang.String.class, java.lang.Byte.class, java.lang.String.class, java.lang.Boolean.class
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
            .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addRowMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addRowMenuMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        model.addRow(new Object[] {null, null, null, null, false});
        addRowMenu.setSelected(false);
        }
    }//GEN-LAST:event_addRowMenuMouseClicked

    private void saveChangesMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveChangesMenuMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON1) {
            saveChangesMenu.setSelected(false);
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            Task task = new Task();
            task.setName((String)table.getModel().getValueAt(i, 1));
            task.setPriority((byte)table.getModel().getValueAt(i, 2));
            task.setArea((String)table.getModel().getValueAt(i, 3));
            task.setFinished((boolean)table.getModel().getValueAt(i, 4));
            
            if (table.getModel().getValueAt(i, 0) != null) {
                dao.updateTask((int)table.getModel().getValueAt(i, 0), task);
            }
            else {
                dao.insertTask(task);
            }
        }
        
        generateTable();
        
        }
    }//GEN-LAST:event_saveChangesMenuMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TaskFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TaskFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu addRowMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu saveChangesMenu;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}