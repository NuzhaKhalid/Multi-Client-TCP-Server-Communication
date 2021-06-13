/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Nuzha
 */
public class ServerClient extends Thread{
    
    Socket serverClient;
    int clientNo;
    
    ServerClient(Socket inSocket,int counter){
      serverClient = inSocket;
      clientNo=counter;
    }
    
    public void run(){
        try{
          DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
          DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
          String clientMessage="", serverMessage="";

          while(true){

              serverMessage = "Welcome to the server 127.0.0.1:8888\nEnter S to save the data\nEnter R to read the data\nEnter E to exit\n";
              outStream.writeUTF(serverMessage);

              clientMessage = inStream.readUTF();

              if(clientMessage.equals("S")){
                  //System.out.println("Test");
                  serverMessage = "Please Enter data to be saved\n";
                  outStream.writeUTF(serverMessage);
                  outStream.flush();
                  clientMessage = inStream.readUTF();
                  String str = serverClient.getRemoteSocketAddress().toString();
                  //remove / from the start
                  String address = str.substring(1, str.length());
                  //remove port 
                  int i = address.indexOf(":");
                  address = address.substring(0, i);
                  String filename = address + ".txt";
                  //System.out.println(filename);
                  try{
                      File save = new File(filename);
                      if(!save.exists()){
                          save.createNewFile();
                        FileWriter fw = new FileWriter(save);
                        fw.write(clientMessage + "\n");
                        fw.close();
                      }
                      else{
                          FileWriter fw = new FileWriter(save, true);
                            fw.write(clientMessage + "\n");
                            fw.close();
                      }
                  } catch(IOException ex){
                      System.out.println(ex);
                  }
                  System.out.println(clientMessage);
                  serverMessage = "Information saved for client " + address + "\n";
                  
                  outStream.writeUTF(serverMessage);
                  outStream.flush();
              }
              else if(clientMessage.equals("R")){

                  String str = serverClient.getRemoteSocketAddress().toString();
                  //remove / from the start
                  String address = str.substring(1, str.length());
                  //remove port 
                  int i = address.indexOf(":");
                  address = address.substring(0, i);
                  String filename = address + ".txt";
                  //System.out.println(filename);
                  try{
                      File read = new File(filename);
                      if(read.exists()){
                          Scanner readfile = new Scanner(read);
                          String data = "";
                          while(readfile.hasNextLine())
                          {
                              String readline = readfile.nextLine();
                              //System.out.println(readline);
                              if(data != "")
                                data = data + "\n" + readline;
                              else
                                  data = readline;
                          }
                          readfile.close();
                          outStream.writeUTF(data);
                          outStream.flush();
                      }
                      else{
                          serverMessage = "No Information found for client " + address + "\n"; 
                          outStream.writeUTF(serverMessage);
                          outStream.flush();
                      }
                      
                  } catch(IOException ex){
                      System.out.println(ex);
                  }
              }
              else if(clientMessage.equals("E")){
                    System.out.println(clientMessage);
                    serverMessage = "Connection closed";
                    outStream.writeUTF(serverMessage);
                    outStream.flush();
                    break;
              }
          }
          inStream.close();
          outStream.close();
          serverClient.close();

        } catch(Exception ex){
            System.out.println(ex);
        } finally{
            System.out.println(serverClient.getRemoteSocketAddress() + " exit ");
            Homework3 obj = new Homework3();
            obj.setActiveCount();
        }
    }

}
