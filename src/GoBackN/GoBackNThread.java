package GoBackN;

import java.io.DataInputStream;
import java.lang.Runnable;
import java.io.*;
public class GoBackNThread implements Runnable{
    Thread thread;
    DataInputStream dataInputStream;
    int ackFrame; int windowSize;

    GoBackNThread(DataInputStream din, int size){
        thread = new Thread(this);
        dataInputStream = din;
        windowSize =size;
        ackFrame = -2;
    }

    @Override
    public void run() {
        try{
            while(ackFrame != -1){
                ackFrame = dataInputStream.readInt();
                if(ackFrame != -1){
                    System.out.println("Acknowledgement of frame no. " + (ackFrame) + " received.");
                }
            }
        }catch (IOException e){
            System.out.println(ackFrame);
            System.out.println("Exception => " + e);
        }
    }

}
