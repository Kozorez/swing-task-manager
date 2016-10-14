package com.mycompany.taskmanager;

import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class SwingAuthenticator implements Authenticator {

    private static final SwingAuthenticator instance = new SwingAuthenticator();

    public static SwingAuthenticator getInstance() {
        return instance;
    }

    private SwingAuthenticator() {
    }

    @Override
    public String authenticate() {
        Box box = Box.createHorizontalBox();
        JPasswordField passwordField = new JPasswordField(16);
        box.add(passwordField);

        int button = JOptionPane.showConfirmDialog(null, box, "Enter your password", JOptionPane.OK_CANCEL_OPTION);

        String password = "";

        if (button == JOptionPane.OK_OPTION) {
            char[] passwordArray = passwordField.getPassword();

            if (passwordArray.length == 0) {
                authenticate();
            }

            password = new String(passwordArray);
        } else if (button == JOptionPane.CANCEL_OPTION
                || button == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }

        return password;
    }
}
