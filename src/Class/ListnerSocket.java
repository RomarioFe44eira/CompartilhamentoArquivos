package Class;

import Class.Usuario.UsuarioCliente;
import Views.CadastroUsuario;
import Views.ClientePrincipal;
import Views.Login;
import com.fe44eira.app.bean.FileMessage;
import com.fe44eira.app.cliente.Cliente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ListnerSocket implements Runnable {
    private ObjectInputStream inputStream;
    private static String nome;

    public ListnerSocket(Socket socket) throws IOException {
        this.inputStream = new ObjectInputStream(socket.getInputStream());        
    }
    @Override
    public void run() {
        FileMessage message = null;
        System.out.println("RUNNN... CLIENTE");
        try {       
            while ((message = (FileMessage) inputStream.readObject()) != null) {
                
                if(message.getMsg() != null){
                    if(message.getMsg().equals("ReturnListaUsuarios")){
                        System.out.println("Acessou método listnersocket cliente......");
                        for(int i=0; i<message.getListaUsuarios().size(); i++){
                            ClientePrincipal.listaUsers.add(message.getListaUsuarios().get(i));
                        }
                        
                       for(int i=0; i<ClientePrincipal.listaUsers.size(); i++){
                           System.out.println("ListaUsuarios: "+ClientePrincipal.listaUsers.get(i));
                       }
                    }
                }
                else{
                    if (message.isAuth()== true) {
                        new ClientePrincipal(
                            new UsuarioCliente(message.getNomeUsuario(), message.getSenhaUsuario(), message.isAuth())
                        ).setVisible(true);
                        JOptionPane.showMessageDialog(null, message.getNomeUsuario()+" seu acesso foi permitido!", "Autenticação aceita", JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println(ListnerSocket.class.getName()+": auth= "+message.isAuth());
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Autenticação falhou, tente novamente!", "Autenticação Recusada", JOptionPane.ERROR_MESSAGE);
                        new Login().setVisible(true);                     
                    }
                }
                
                
                if(message.getFile() != null){
                    JOptionPane.showMessageDialog(null, "Você recebeu uma nova mensagem!");
                    System.out.println("\nVocê recebeu um arquivo de " + message.getNomeUsuario());
                    System.out.println("O arquivo é " + message.getFile().getName());                                  
                    salvar(message);
                }
                
            }

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Servidor ficou OFFLINE");
            //Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
   
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void imprime(FileMessage message) {
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
    public void salvar(FileMessage message) {
        System.out.println("ListnerSocket: @@@@@@@@@@@@@@@ SALVAR MENSAGEM - EXECUTADO @@@@@@@@@@@@@@@@@");

        try {
            FileInputStream fileInputStream = new FileInputStream(message.getFile());
            String dirLocalCliente = "c:\\uBox\\Cliente\\";

            if (new File(dirLocalCliente + nome).exists()) {
                System.out.println("Eba, o diretorio cliente já existe! Local: " + dirLocalCliente + nome);
            } else {
                System.out.println("Ops, a pasta cliente ainda não existe, vamos tentar cria-la!");

                if (new File(dirLocalCliente + nome).mkdirs()) {
                    System.out.println("Eba, o diretorio cliente foi criado, Local: " + dirLocalCliente + nome);
                } else {
                    System.out.println("Ops, não foi possível criar o diretorio: " + dirLocalCliente + nome);
                }
            }
           
            
            //FileOutputStream fileOutputStream = new FileOutputStream(dirLocalCliente + nome + "\\" + message.getFile().getName()); // Aqui faz acontecer
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
