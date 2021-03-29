package GoBackN;

// the server side

import java.io.*;
import java.net.*;

public class Receiver {
    public static void main(String[] args) throws IOException {
            // creating the server listening at port 6666

            ServerSocket ss=new ServerSocket(6666);
            Socket s = ss.accept();//establishes connection

            //in-stream
            DataInputStream din=new DataInputStream(s.getInputStream());
            DataOutputStream dout =new DataOutputStream(s.getOutputStream());

            int winSize = din.readInt();
            int intentionalBreak = -1;// for the purpose of testing only
            int i = 0, ackToSend = 0; boolean flag = false;
             int k, data;
             while(true){
                     k = din.readInt();
                     data = din.readInt();

                     if(k == -1) {
                         while(ackToSend != i) {
                             System.out.println("Sending acknowledgement for frame " + ackToSend);
                             dout.writeInt(ackToSend);
                             ackToSend++;
                         }
                         break;
                     }
                     if(k != i || k == intentionalBreak){
                         System.out.println("Frame expected= " + i + " Received= " + k);
                         System.out.println("Not sending acknowledgement");
                         i=ackToSend;
                         intentionalBreak = -1;
                     }else
                     {
                         System.out.println("Frame= " + k);
                         System.out.println("Data received= "+ data);
                         if(k == winSize-1 || flag) {
                             System.out.println("Sending acknowledgement for frame " + ackToSend);
                             dout.writeInt(ackToSend);
                             dout.flush();
                             ackToSend++;
                             flag = true;
                         }
                     }


                     i++;
                 }
//            dout.writeInt(ackToSend);
//             dout.flush();
            System.out.println("Client has send all the data closing the server");
             dout.writeInt(-1);
            // closing the connection
            din.close();
            dout.close();
            s.close();
            ss.close();
    }
} 