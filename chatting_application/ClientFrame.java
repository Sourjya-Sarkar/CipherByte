
package chatting_application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientFrame extends JFrame implements ActionListener, Runnable {
    private JPanel messagePanel;
    private JTextField messageField;
    private JButton sendButton;
    private Box verticalBox = Box.createVerticalBox();
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public ClientFrame() {
        setTitle("Client");
        setSize(500, 600);
        setLocation(700, 100);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(messagePanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        messagePanel.add(verticalBox, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        messageField = new JTextField();
        messageField.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        bottomPanel.add(messageField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        sendButton.addActionListener(this);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            socket = new Socket("localhost", 5000); // Connect to the server
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = messageField.getText();
        if (!message.trim().isEmpty()) {
            output.println("Client: " + message);
            addMessageToPanel("Client: " + message);
            messageField.setText("");
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = input.readLine()) != null) {
                addMessageToPanel(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMessageToPanel(String message) {
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());

        JLabel messageLabel = new JLabel(
                "<html><p style=\"width: 250px; text-align: left;\">" + message + "</p></html>");
        messageLabel.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        messageLabel.setBackground(new Color(173, 216, 230));
        messageLabel.setOpaque(true);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 50));

        messagePanel.add(messageLabel, BorderLayout.LINE_END);
        verticalBox.add(messagePanel);
        verticalBox.add(Box.createVerticalStrut(15));
        this.messagePanel.revalidate();
    }

    public static void main(String[] args) {
        new ClientFrame();
    }
}
