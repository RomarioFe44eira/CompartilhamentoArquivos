package com.fe44eira.app.servidor;

import com.fe44eira.app.bean.FileMessage;
import com.fe44eira.app.cliente.Cliente;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();    
    private int port;
    private String dirServidor = "c:\\uBox\\Servidor\\";  
    
    public Servidor() {
        try {
            //this.port = Integer.parseInt(JOptionPane.showInputDialog("Defina uma porta para o Servidor?"));
            //serverSocket = new ServerSocket(this.port);
            //JOptionPane.showMessageDialog(null, "Servidor está ativo!");            
            serverSocket = new ServerSocket(5555);            
            System.out.println("Servidor ON!");         
            
            if(new File(dirServidor).exists()){
                System.out.println("O diretorio ja existe! e está no Local: " + dirServidor);
            }
            else{
                System.out.println("O diretorio ainda não existe, tentaremos criar-lo!");
                if(new File(dirServidor).mkdirs()){
                    System.out.println("Oba!, criamos com sucesso o diretorio no servidor!");
                }
                else{
                    System.out.println("Epa, nos não conseguimos criar o diretório, tente novamente.");
                }
            }            
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
                        //salvar(message);                        
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!message.getCliente().equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                                //salvar(message); 
                                salvarMensage(message, message.getCompartilhamento());
                            }
                        }                        
                    }
                    else{                        
                        String dirCompleto = dirServidor + message.getCliente();// ESTOU CRIANDO A PASTA DO USUÁRIO ASSIM QUE ELE DIGITA SEU NOME NO CLIENTE                        
                        if(new File(dirCompleto).exists())
                            System.out.println("O diretorio " + dirCompleto + " já existe no servidor!");                        
                        else{
                            if(new File(dirCompleto).mkdirs())
                                System.out.println("Diretório "+ dirCompleto +" foi criado com sucesso");                            
                            else
                                System.out.println("Ocorreu um erro ao criar o diretório "+ dirCompleto +" !");
                        }
                        
                    }
                    
                }
            } catch (IOException | ClassNotFoundException ex) {
                streamMap.remove(message.getCliente());
                System.out.println("O cliente : -" + message.getCliente() + "- desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        
        private void salvarMensage(FileMessage message, ArrayList<String> share) {
            try {                
                share.add(message.getCliente());// Adiciona o cliente no arrayList                
                for(int i=0;i<share.size();i++){
                    //System.out.println(share.get(i));
                    FileInputStream fileInputStream = new FileInputStream(message.getFile());
                    new File(dirServidor + share.get(i)).mkdir();
                    new File(dirServidor + share.get(i) +"\\.properties").createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream( dirServidor + share.get(i) + "\\" + message.getFile().getName());                
                    FileChannel fin = fileInputStream.getChannel();
                    FileChannel fout = fileOutputStream.getChannel();                
                    long size = fin.size();                
                    fin.transferTo(0, size, fout);
                }               
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } 
        }
        
    }
    
    public static void main(String[] args){
        new Servidor();              
    }
}
