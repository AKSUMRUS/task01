package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(10000)) {
            while (true) {
                Socket s = ss.accept();

                new Thread(() -> handle(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void handle(Socket s) {
        try {
//            InputStream reader = s.getInputStream();
//            byte[] buffer = new byte[4096];
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter writer = new PrintWriter(s.getOutputStream(), true);
            while (s.isConnected()) {
                int n = 0;

//                while (reader.available() > 0 && (n = reader.read(buffer)) != -1) {}
                reader.readLine();


                String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
                writer.println(timeStamp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}