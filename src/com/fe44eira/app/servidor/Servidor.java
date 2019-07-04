package com.fe44eira.app.servidor;

import Class.Usuario;
import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Servidor {
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<>();    
    private int port;
    private String dirServidor = "c:\\uBox\\Servidor\\"; 
    
    private Usuario user;
    
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
                    String username = message.getNomeUsuario();
                    String password = message.getSenhaUsuario();
                   
                    streamMap.put(username, outputStream);                    
                    System.out.println("Mensagem recebida: " + username);
                    
                    if(message.getFile() != null){
                        //salvar(message);                        
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!username.equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                                //salvar(message); 
                                salvarMensage(message, message.getUserShare());
                            }
                        }                        
                    }
                    else{
                        System.out.println("Não há nada compartilhado!");
                            System.out.println("Cadastro-Usuario-aquiiiii");
                            
                            String dir = "C:\\uBox\\Servidor\\dados.dat";

                            if(!new File(dir).exists()){            
                                System.out.println("Vamos criar um arquivo de dados, pois ainda não existe!");
                                if(new File(dir).createNewFile()){
                                    System.out.println("Arquivo criado com sucesso!"); 
                                    gravaUsuarioArquivo(username, password);
                                }
                                else
                                    System.out.println("Arquivo não foi criado com sucesso!");
                            }
                            else{
                                gravaUsuarioArquivo(username, password);
                            }

                        
                        
                        String dirCompleto = dirServidor + username;// ESTOU CRIANDO A PASTA DO USUÁRIO ASSIM QUE ELE DIGITA SEU NOME NO CLIENTE                        
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
                streamMap.remove(user.getUsername());
                System.out.println("O cliente : -" + message.getNomeUsuario() + "- desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        
        public void gravaUsuarioArquivo(String username, String password) throws IOException{
            String dir = "C:\\uBox\\Servidor\\dados.dat";
            try (FileWriter fw = new FileWriter(dir, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
                String str = username + "#" + password;
                bw.write(str);                                    
                bw.newLine();
                bw.close();
                fw.close();
            }
            System.out.println("Buffer e Arquivo foram fechados!");
        }
        
        
        private void salvarMensage(FileMessage message, ArrayList<String> share) {
            try {                
                share.add(message.getNomeUsuario());// Adiciona o cliente no arrayList                
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
