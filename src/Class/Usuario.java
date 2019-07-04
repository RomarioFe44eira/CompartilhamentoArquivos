package Class;

import com.fe44eira.app.bean.FileMessage;
import com.fe44eira.app.cliente.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Usuario {

    private Socket socket;
    private ObjectOutputStream outputStream;

    private String username;
    private String password;
    private Date dtCriacao;

    public Usuario(){}
    public Usuario(String username, String password) throws IOException {
        this.username = username;
        this.password = password;
        this.dtCriacao = new Date();
    }

    // CRIA UM DIRETÓRIO LOCAL PARA QUE O USUÁRIO ARMAZENE SEUS ARQUIVOS
    public void cli_criarDiretorioLocalUsuario(FileMessage message) {
        System.out.println("Criando diretório local do Usuário");

        try {

            FileInputStream fileInputStream = new FileInputStream(message.getFile());
            String dirLocalCliente = "c:\\uBox\\Cliente\\";

            if (new File(dirLocalCliente + message.getNomeUsuario()).exists()) {
                System.out.println("Diretorio do Usuário já existe!");
            } else {
                System.out.println("Ops, o diretório ainda não existe, iremos criar-lo agora!");

                if (new File(dirLocalCliente + message.getNomeUsuario()).mkdirs()) {
                    System.out.println("Diretorio do usuário foi criado com sucesso!");
                } else {
                    System.out.println("Diretorio do usuário NÃO foi criado com sucesso!");
                }
            }

            FileOutputStream fileOutputStream = new FileOutputStream(dirLocalCliente + this.username + "\\" + message.getFile().getName()); // Aqui faz acontecer

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
    // IMPRIME NA TELA OS DADOS CONTIDOS NO ARQUIVO
    public void imprimeAquivoTerminal(FileMessage message) {
        try {
            FileReader fileReader = new FileReader(message.getFile());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //ENVIA O ARQUIVO DO CLIENTE PARA O SERVIDOR - FALTA TRATAR ESSE MÉTODO
    public void cli_sendFile() throws IOException {
        Socket socket1 = new Socket("localhost", 5555);
        this.outputStream = new ObjectOutputStream(socket1.getOutputStream());
        new Thread(new ListnerSocket(socket1)).start();
        
        Usuario user = new Usuario(this.username, this.password);
        
       JFileChooser fileChooser = new JFileChooser();
        int opt = fileChooser.showOpenDialog(null);
        if (opt == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            this.outputStream.writeObject(new FileMessage(this.username, file));
            
            

            JOptionPane.showMessageDialog(null, "O Arquivo foi enviado ao diretório do usuário " + this.username + " no servidor");
        } else {
            JOptionPane.showMessageDialog(null, this.username + ", o arquivo não foi enviado ao servidor!");
        }

    }
    public void cli_AddNewUser() throws IOException{
        Socket socket1 = new Socket("localhost", 5555);
        this.outputStream = new ObjectOutputStream(socket1.getOutputStream());
        new Thread(new ListnerSocket(socket1)).start();
        
        Usuario user = new Usuario(this.username,this.password);
        this.outputStream.writeObject(
            new FileMessage(user.getUsername(), user.getPassword())
        );        
        System.out.println("ClassUsuario: Usuário cadastrado..");        
    }
    
     
    
    
    // ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO ABAIXO 
    // SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR 
    // SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR SERVIDOR     
    public void srv_cadastrarUsuario() throws IOException {
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
    public void salvarMensage(FileMessage message, ArrayList<String> share) {
        String dirServidor = "c:\\uBox\\Servidor\\";

        try {
            share.add(message.getNomeUsuario());// Adiciona o cliente no arrayList                
            for (int i = 0; i < share.size(); i++) {
                //System.out.println(share.get(i));
                FileInputStream fileInputStream = new FileInputStream(message.getFile());
                new File(dirServidor + share.get(i)).mkdir();
                new File(dirServidor + share.get(i) + "\\.properties").createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(dirServidor + share.get(i) + "\\" + message.getFile().getName());
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

    
    // GETTERS AND SETTERS
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    
}
