package SelectiveRepeat;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class Receiver {
    public static void main(String[] args) throws IOException {
        // creating the server listening at port 6666

        ServerSocket ss = new ServerSocket(6666);
        Socket s = ss.accept();//establishes connection

        //in-stream
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        int winSize = din.readInt();
        int intentionalBreak = 2;// for the purpose of testing only
        Queue<Integer> ackQueue = new LinkedList<>();
        int k, data, index = 0;
        boolean flag = false;
        while (true) {
            k = din.readInt();
            data = din.readInt();
            if (k == -1) {
                while (!ackQueue.isEmpty()) {
                    System.out.println("Sending acknowledgement for frame " + ackQueue.peek());
                    dout.writeInt(ackQueue.poll());
                    dout.flush();
                }
                break;
            }
            if (k == intentionalBreak) {
                System.out.println("The frame received is wrong. Not sending acknowledgement for frame " + k);
                intentionalBreak = -1;
            } else {
                ackQueue.add(k);
                System.out.println("Frame= " + k);
                System.out.println("Data received= " + data);

            }
            if (flag || index != 0 && index % winSize - 1 == 0) {
                flag = true;
                System.out.println("Sending acknowledgement for frame " + ackQueue.peek());
                dout.writeInt(ackQueue.poll());
                dout.flush();
            }
            index++;
        }

        System.out.println("Client has send all the data closing the server");
        dout.writeInt(-1);
        // closing the connection
        din.close();
        dout.close();
        s.close();
        ss.close();

    }
}
