package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Random;

public class Client {
    public static void main(String[] args) throws Exception {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);
        int M = Integer.parseInt(args[3]);
        int Q = Integer.parseInt(args[4]);

        try (Socket s = new Socket(ip, port);
                FileWriter fileWriter = new FileWriter("result.txt");) {
            s.setTcpNoDelay(true); // TCP_NO_DELAY = true
//            OutputStream writer = s.getOutputStream();
            PrintWriter writer = new PrintWriter(new DataOutputStream(s.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            for (int K = 0; K < M; K++) {
                int len = N * K + 8;
                double time = 0;
                for (int i = 0; i < Q; i++) {
                    byte[] array = new byte[len+1];
                    new Random().nextBytes(array);
                    array[len] = '\n';
                    long before = System.currentTimeMillis();
                    writer.println(Arrays.toString(array) + "\n");
//                    writer.write(array);
                    writer.flush();

                    reader.readLine();
                    long after = System.currentTimeMillis();
                    time += after - before;
                }
                time /= Q;

                System.out.printf("Итерация #%d\n", K);
                System.out.printf("Количество отправленных байт: %d\n", len);
                System.out.printf("Среднее время: %f\n", time);
                System.out.println("-------------");
                fileWriter.write(String.format("%d %f\n", len, time));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
