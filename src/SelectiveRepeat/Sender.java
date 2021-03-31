package SelectiveRepeat;
//This the program for the client side that is making the request

import GoBackN.GoBackNThread; // using the same thread from the Go-Back-N package

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class Sender {
    public static void main(String []args) throws IOException{
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
        Socket s = new Socket("localhost", 6666);
        //out-stream
        DataOutputStream dout =new DataOutputStream(s.getOutputStream());
        DataInputStream din=new DataInputStream(s.getInputStream());

        // thread to send frame in current window
        GoBackNThread currThread = new GoBackNThread(din, winSize);
        currThread.thread.start();

        //sending the window size info to the server
        dout.writeInt(winSize);
        dout.flush();
        // queue to hold the indexes of the frames whose acknowledgement is awaited
        Queue<Integer> awaitingAck = new LinkedList<>();

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
                    awaitingAck.add(i);
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
                if(currThread.getAckFrame() == -1 || awaitingAck.isEmpty()) break;

                //checking the index of the acknowledgement received
                if(currThread.getAckFrame() != awaitingAck.peek()){
                    awaitingAck.remove(currThread.getAckFrame());
                    System.out.println("No acknowledgement received after 4 msec sending again.");
                    System.out.println("Sending frame " + awaitingAck.peek() + " again");
                    System.out.println();
                    dout.writeInt(awaitingAck.peek()); // sending the order of sequence
                    dout.writeInt(data[awaitingAck.peek()]); // sending the respective data
                    dout.flush();
                    awaitingAck.add(awaitingAck.poll());
                }else {
                    currFrame++;
                    awaitingAck.poll();
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
