package com.fe44eira.app.cliente;

import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class Cliente {
    private Socket socket;
    private ObjectOutputStream outputStream;    
    private String nome;

    public Cliente() throws IOException {
        this.socket = new Socket("localhost", 5555);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        
        new Thread(new ListnerSocket(socket)).start();
        menu();
    }

    
    private void menu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite seu nome: ");        
        this.nome = scanner.nextLine();        
        this.outputStream.writeObject(new FileMessage(nome));        
        int option = 0;        
        while(option != 1){
            System.out.println("1 - Sair | 2 - Enviar: ");
            option = scanner.nextInt();           
            if(option == 2){
                send(nome);
            }else if(option == 1){
                System.exit(0);
            }            
        }   
    }
   
    
    private void send(String nome) throws IOException {         
        JFileChooser fileChooser = new JFileChooser();       
        int opt = fileChooser.showOpenDialog(null);        
        if(opt == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Compartilhar para quantas pessoas: ");            
            ArrayList<String> share = new ArrayList<String>();
            int numPessoas = scanner.nextInt();            
            for (int i = 0; i < numPessoas; i++) {                
                System.out.println("Digite o nome da " + (i+1) +"º pessoa a compartilhar: ");
                share.add(scanner.next());    
            }            
            this.outputStream.writeObject(new FileMessage(nome, file, share));
            JOptionPane.showMessageDialog(null, "O Arquivo foi enviado ao diretório do usuário " + nome + " no servidor");
        }
        else{
            JOptionPane.showMessageDialog(null, nome + ", seu arquivo não foi enviado ao servidor!");
        }
    }

    private static class ListnerSocket implements Runnable {
        private ObjectInputStream inputStream;  
        private String nome;        
        
        public ListnerSocket(Socket socket) throws IOException {
            this.inputStream = new ObjectInputStream(socket.getInputStream());            
        }

        @Override
        public void run() {
            FileMessage message = null;           
            
            try {
                if ((message = (FileMessage) inputStream.readObject()) != null) {
                    System.out.println("Message: NULL");
                }
                while((message = (FileMessage) inputStream.readObject()) != null){
                    System.out.println("\nVocê recebeu um arquivo de " + message.getCliente()); 
                    System.out.println("O arquivo é " + message.getFile().getName());                    
                    //imprime(message);                    
                    salvar(message);                     
                    System.out.println("1 - Sair | 2 - Enviar: ");                           
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        private void imprime(FileMessage message) {
            try {                 
                FileReader fileReader = new FileReader(message.getFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String linha;
                while((linha = bufferedReader.readLine()) != null){
                    System.out.println(linha);
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void salvar(FileMessage message) {
            System.out.println("Entrou no metodo!!!!!!!!!!!!!!!");
                 
            try {               
                FileInputStream fileInputStream = new FileInputStream(message.getFile()); 
                String dirLocalCliente = "c:\\uBox\\Cliente\\";
                
                if(new File(dirLocalCliente + message.getCliente()).exists()){
                    System.out.println("Eba, o diretorio cliente já existe! Local: " + dirLocalCliente + nome);
                }
                else{
                    System.out.println("Ops, a pasta cliente ainda não existe, vamos tentar cria-la!");
                    
                    if(new File(dirLocalCliente + message.getCliente()).mkdirs()){
                        System.out.println("Eba, o diretorio cliente foi criado, Local: " + dirLocalCliente + nome);
                    }
                    else{
                        System.out.println("Ops, não foi possível criar o diretorio: " + dirLocalCliente + nome);
                    }
                }               
                FileOutputStream fileOutputStream = new FileOutputStream(dirLocalCliente + nome + "\\" + message.getFile().getName()); // Aqui faz acontecer
                
                FileChannel fin = fileInputStream.getChannel();
                FileChannel fout = fileOutputStream.getChannel();
                
                long size = fin.size();                
                fin.transferTo(0, size, fout);      
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            new Cliente();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
