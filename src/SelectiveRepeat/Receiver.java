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

        //acknowledgement queue that holds all the acknowledgement to be send
        Queue<Integer> ackQueue = new LinkedList<>();

        int k, data, index = 0;
        boolean flag = false; // this is to ensure that on each reception an acknowledgement is send

        // the main loop
        while (true) {
            // reading the data send over the server
            k = din.readInt();
            data = din.readInt();

            // k == -1 means that the client has sent all the data therefore terminate
            if (k == -1) {
                // sending all the remaining acknowledgements
                while (!ackQueue.isEmpty()) {
                    System.out.println("Sending acknowledgement for frame " + ackQueue.peek());
                    dout.writeInt(ackQueue.poll());
                    dout.flush();
                }
                break;
            }
            // this intentionalBreak has been added for the sake of explanation
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

        System.out.println("Client has sent all the data, closing the server");
        dout.writeInt(-1); // sending the exit response
        dout.flush();

        // closing the connection
        din.close();
        dout.close();
        s.close();
        ss.close();

    }
}
