package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientFormController {
    public TextField txtMsg;
    public TextArea chatArea;
    private Socket socket;

    public void initialize() {
        new Thread(() -> {
            boolean running = true;
            try {
                socket = new Socket("localhost", 3000);
                chatArea.appendText("Client Connected\n");
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                while (running) {
                    String msg = dataInputStream.readUTF();
                    chatArea.appendText(msg + "\n");
                    txtMsg.clear();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
    }).start();
    }
    public void btnSendOnAction(ActionEvent actionEvent) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            String msg = txtMsg.getText();
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
            chatArea.appendText(msg + "\n");
            txtMsg.clear();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
