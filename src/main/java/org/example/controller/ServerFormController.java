package org.example.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    private Socket socket;
    public TextArea chatArea;
    public TextField txtMsg;

   public void initialize() {

       new Thread(() -> {
           boolean running = true;
         try {
             ServerSocket serverSocket = new ServerSocket(3000);
             chatArea.appendText("Server Started\n");
              socket = serverSocket.accept();
             chatArea.appendText("Client Connected\n");

             while (running) {
                 DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
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
