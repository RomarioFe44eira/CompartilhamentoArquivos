package com.fe44eira.app.servidor;

import Class.ListnerSocket;
import Class.ListnerSocketServer;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TermServidor_Test {
 private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<>();    
    private int port;
    private String dirServidor = "c:\\uBox\\Servidor\\"; 

    public TermServidor_Test() {
        try {
            serverSocket = new ServerSocket(5555);            
            System.out.println("Servidor ONLINE!");         
            
            if(!new File(dirServidor).exists()){
                System.out.println("O diretorio ainda não existe, tentaremos criar-lo!");
                if(new File(dirServidor).mkdirs())
                    System.out.println("Diretorio criado com sucesso no servidor!");                
                else
                    System.out.println("Diretorio não foi criado com sucesso no servidor!");
            }
            else{
                System.out.println("Aguardando conexão....");
            }

            while(true){
                socket = serverSocket.accept();
                System.out.println("Conexão aceita.");
                new Thread(new ListnerSocketServer(socket)).start();                
            }            
            
        } catch (IOException ex) {
            System.out.println("Conexão fechada..");
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args){
        new TermServidor_Test();              
    }
}
