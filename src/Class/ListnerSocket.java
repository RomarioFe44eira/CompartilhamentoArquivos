package Class;

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
    private String nome;

    
    
    public ListnerSocket(Socket socket) throws IOException {
        this.inputStream = new ObjectInputStream(socket.getInputStream());        
    }

    @Override
    public void run() {
        FileMessage message = null;
        System.out.println("RUNNN... CLIENTE");
        try {       
            while ((message = (FileMessage) inputStream.readObject()) != null) {
                
                if (message.isAuth()== true) {
                    new ClientePrincipal().setVisible(true);
                    JOptionPane.showMessageDialog(null, message.getMsg());
                    System.out.println("ListnerSocket: Mensagem="+message.getMsg());
                    System.out.println(ListnerSocket.class.getName()+": auth= "+message.isAuth());
                }
                else{
                    JOptionPane.showMessageDialog(null, message.getMsg());
                    System.out.println("ListnerSocket: Mensagem="+message.getMsg());
                    new Login().setVisible(true);                     
                }
                
                
                if(message.getFile() != null){
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
        System.out.println("Entrou no metodo!!!!!!!!!!!!!!!");

        try {
            FileInputStream fileInputStream = new FileInputStream(message.getFile());
            String dirLocalCliente = "c:\\uBox\\Cliente\\";

            if (new File(dirLocalCliente + message.getNomeUsuario()).exists()) {
                System.out.println("Eba, o diretorio cliente já existe! Local: " + dirLocalCliente + nome);
            } else {
                System.out.println("Ops, a pasta cliente ainda não existe, vamos tentar cria-la!");

                if (new File(dirLocalCliente + message.getNomeUsuario()).mkdirs()) {
                    System.out.println("Eba, o diretorio cliente foi criado, Local: " + dirLocalCliente + nome);
                } else {
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
