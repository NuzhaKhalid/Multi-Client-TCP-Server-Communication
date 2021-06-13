/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;
/**
 *
 * @author Nuzha
 */
public class Client {
    public static void main(String[] args) throws Exception {
        try{
            Socket socket=new Socket("127.0.0.1",8888);
            DataInputStream inStream=new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String clientMessage = "",serverMessage = "";
            
            while(!clientMessage.equals("E")){

                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);

                clientMessage=br.readLine();
                outStream.writeUTF(clientMessage);
                outStream.flush();
                
                serverMessage = inStream.readUTF();
                System.out.println(serverMessage);
                
                if(clientMessage.equals("S")){
                    
                    clientMessage = br.readLine();
                    outStream.writeUTF(clientMessage);
                    outStream.flush();
                    
                    serverMessage = inStream.readUTF();
                    System.out.println(serverMessage);
                    
                    
                }
                else if(clientMessage.equals("R")){
                    
                    //System.out.println(serverMessage);
                    if(serverMessage.contains("No Information found for client")){
                        System.out.println(serverMessage);
                    }
                    else{
                        String str = socket.getRemoteSocketAddress().toString();
                        String address = str.substring(1, str.length());
                        //remove port 
                        int i = address.indexOf(":");
                        address = address.substring(0, i);
                        //System.out.println(address);
                        String filename = address + "_127.0.0.1.txt";
                        //System.out.println(filename);
                        try{
                            File save = new File(filename);
                            if(!save.exists())
                                save.createNewFile();

                            FileWriter fw = new FileWriter(save);
                            fw.write(serverMessage);
                            fw.close();
                        } catch(IOException ex){
                            System.out.println(ex);
                        }
                    }
                }

            }
            outStream.close();
            outStream.close();
            socket.close();
            
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
