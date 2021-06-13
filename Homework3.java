/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//netstat -ano | findstr :8888
//taskkill -pid 16480 /f
import java.net.*;
import java.io.*;
/**
 *
 * @author Nuzha
 */
public class Homework3 {

    static int active_count = 0;
    
    public static void main(String[] args) throws Exception {
        try{
            ServerSocket server=new ServerSocket(8888);
            int counter=0;
            System.out.println("Server Started....");
            System.out.println("Waiting for clients on port 8888");
            
            while(true){
                
                counter++;
                //server accept the client connection request
                Socket serverClient=server.accept();
                active_count++;
                //get client address ip:port
                String address = serverClient.getRemoteSocketAddress().toString();
                System.out.println("Got Connection from " + address);
                System.out.println("Active Connections: " + active_count);
                
                //send  the request to a separate thread
                ServerClient sct = new ServerClient(serverClient,counter); 
                sct.start();
          }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    void setActiveCount()
    {
        active_count--;
        if(active_count == 0)
        {
            System.out.println("Waiting for clients on port 8888");
        }
    }
}
