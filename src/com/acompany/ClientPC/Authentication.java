package com.acompany.ClientPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Authentication extends JFrame implements ActionListener {
    private Socket cSocket = null;
    private DataOutputStream psswrchk = null;
    private DataInputStream verification = null;
    private String verify = "";
    private JButton SUBMIT;
    private JPanel panel;
    private JLabel label, label1;
    private String width = "", height = "";
    private final JTextField text1;

    Authentication(Socket cSocket) {
        label1 = new JLabel();
        label1.setText("Password");
        text1 = new JTextField(15);
        this.cSocket = cSocket;

        label = new JLabel();
        label.setText("");
        this.setLayout(new BorderLayout());

        SUBMIT = new JButton("SUBMIT");

        panel = new JPanel(new GridLayout(2, 1));
        panel.add(label1);
        panel.add(text1);
        panel.add(label);
        panel.add(SUBMIT);
        add(panel, BorderLayout.CENTER);
        SUBMIT.addActionListener(this);
        setTitle("LOGIN FORM");
    }


    public void actionPerformed(ActionEvent ae) {


        String value1 = text1.getText();

        try {
            psswrchk = new DataOutputStream(cSocket.getOutputStream());
            verification = new DataInputStream(cSocket.getInputStream());
            psswrchk.writeUTF(value1);
            verify = verification.readUTF();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (verify.equals("valid")) {
            try {
                width = verification.readUTF();
                height = verification.readUTF();

            } catch (IOException e) {
                e.printStackTrace();
            }
            Frame abc = new Frame(cSocket, width, height);
            dispose();
        } else {
            System.out.println("enter the valid password");
            JOptionPane.showMessageDialog(this, "Incorrect  password", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }

    }

}

