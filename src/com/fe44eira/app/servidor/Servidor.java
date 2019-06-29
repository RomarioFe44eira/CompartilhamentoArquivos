package com.fe44eira.app.servidor;

import com.fe44eira.app.bean.FileMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();

    public Servidor() {
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Servidor ON!");
            
            while(true){
                socket = serverSocket.accept();
                new Thread(new ListnerSocket(socket)).start();
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class ListnerSocket implements Runnable {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;
        
        
        public ListnerSocket(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            FileMessage message = null;
            
            try {
                while((message = (FileMessage) inputStream.readObject()) != null){
                    
                    streamMap.put(message.getCliente(), outputStream);
                    System.out.println("Online: " + message.getCliente());
                    if(message.getFile() != null){
                        salvar(message);
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!message.getCliente().equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                            }
                        }
                    }
                    
                }
            } catch (IOException | ClassNotFoundException ex) {
                streamMap.remove(message.getCliente());
                System.out.println(message.getCliente() + " desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 

        private void salvar(FileMessage message) {
          try {             
                
                FileInputStream fileInputStream = new FileInputStream(message.getFile());
                new File("c:\\uBox\\servidor\\" + message.getCliente()).mkdir();
                new File("c:\\uBox\\servidor\\"+ message.getCliente() +"\\.properties").createNewFile();
                
                Thread.sleep(new Random().nextInt(1000));
                FileOutputStream fileOutputStream = new FileOutputStream("c:\\uBox\\servidor\\"+ message.getCliente() + "\\" + message.getFile().getName());
                
                FileChannel fin = fileInputStream.getChannel();
                FileChannel fout = fileOutputStream.getChannel();
                
                long size = fin.size();
                
                fin.transferTo(0, size, fout);
                
                
                
            } catch (FileNotFoundException ex) {
               ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
    }
    
    public static void main(String[] args) {
        new Servidor();
    }
    
    
}
