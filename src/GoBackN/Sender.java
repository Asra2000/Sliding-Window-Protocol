package GoBackN;

//This the program for the client side that is making the request

import java.io.*;
import java.net.*;

public class Sender {
    public static  void main(String []args) throws IOException {
        BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
        //taking input
        System.out.print("Enter the window size: ");
        int winSize = Integer.parseInt(br.readLine());
        System.out.print("Enter the size of frame: ");
        int frameSize = Integer.parseInt(br.readLine());
        int []data = new int[frameSize];

        // taking input for each frame
        for(int i =0; i< frameSize ; i++){
            data[i] = i+1;
        }

        //creating a client

        //localhost IP address = 127.0.0.1
        //port = 6666
        Socket s = new Socket("localhost",6666);

        //out-stream
        DataOutputStream dout =new DataOutputStream(s.getOutputStream());
        DataInputStream din=new DataInputStream(s.getInputStream());

        // thread to send frame in current window
        GoBackNThread currThread = new GoBackNThread(din, winSize);
        currThread.thread.start();

        dout.writeInt(winSize);
        dout.flush();

        int currFrame = 0; int index =0;
        boolean terminate = false;
        do{
            System.out.println("Frames in the current window");
            for(int i = currFrame; i< currFrame + winSize && i < frameSize; i++){
                System.out.print("| " + data[i] + " | ");
            }
            System.out.println();

            try {

                for (int i = index; i < currFrame + winSize && i < frameSize; i++) {
                    System.out.println("Sending frame " + i);
                    dout.writeInt(i); // sending the order of sequence
                    dout.writeInt(data[i]); // sending the respective data
                    dout.flush(); // clearing the buffer
                    index++;
                    Thread.sleep(100);
                }
                if(index == frameSize){
                    dout.writeInt(-1);
                    dout.flush();
                }else {
                    System.out.println();
                    System.out.println();
                }
                currThread.thread.join(4000); // time to receive acknowledgement is set to 4 milli seconds
                if(currThread.ackFrame == -1) break;
                if(currThread.ackFrame != currFrame){
                    System.out.println("No acknowledgement received after 4 msec sending again.");
                    System.out.println("starting from frame " + currFrame);
                    System.out.println();
                    index = currFrame;
                }else {
                    currFrame++;
                }

                if(currFrame >= frameSize) terminate = true;

            }catch (InterruptedException e){
                System.out.println("Exception occurred =>" + e);
            }

        }while(!terminate);


        System.out.println("\nThe connection is now closed.");

        // closing the connection
        dout.close();
        din.close();
        s.close();
    }
}
