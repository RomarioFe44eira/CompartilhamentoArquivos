package Class.Usuario;
import Class.ListnerSocket;
import Class.interfaces.IUsuario;
import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class UsuarioCliente implements IUsuario{
    private String username;
    private String password;
    private Date dtCreate;

    private boolean isAuth;
    
    public void autenticar(){
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream outputStream;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            new Thread(new ListnerSocket(socket)).start();
            
            if(!new File("c:\\uBox\\Cliente\\temp\\").exists()){
                new File("c:\\uBox\\Cliente\\temp\\").mkdir();
            }
            
            File auth = new File("c:\\uBox\\Cliente\\temp\\auth");
            
            FileWriter fw = new FileWriter("c:\\uBox\\Cliente\\temp\\auth");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.username +";"+ this.password);
            bw.newLine();
            bw.close();
            fw.close();
            
            FileMessage a;
            
            outputStream.writeObject(
                a = new FileMessage(this.getName(), auth)
            ); 
            System.out.println("hey "+this.getName()+", sua autenticação foi transmitida ao servidor!");
            
        } catch (IOException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void cadastar(){
        this.criarDiretorio();
        
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream outputStream;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            new Thread(new ListnerSocket(socket)).start();
            outputStream.writeObject(
                new FileMessage(this.getName(), this.getPass())
            ); 
            System.out.println("Parabéns "+this.getName()+" seu cadastrado foi concluido!");
            
        } catch (IOException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    };
    @Override
    public void remover(){};
    @Override
    public void editar(){};
    
    public void enviarArquivo(){
    
        try {
            Socket socket = new Socket("localhost", 5555);
            ObjectOutputStream outputStream;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            new Thread(new ListnerSocket(socket)).start();
            
            JFileChooser fileChooser = new JFileChooser();
            int opt = fileChooser.showOpenDialog(null);
            if (opt == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                outputStream.writeObject(
                        new FileMessage(this.username, file)
                );
                
                copiarArquivo(file, new File("c:\\uBox\\Cliente\\"+ this.username+"\\"+file.getName()));
                
                JOptionPane.showMessageDialog(null, this.username + ", seu arquivo foi enviado para o servidor");
            }
            else {
                JOptionPane.showMessageDialog(null, this.username + ", o arquivo não foi enviado ao servidor!");
            }                        
        } catch (IOException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    };
    public void compartilharArquivo(){};
    
    @Override
    public void criarDiretorio(){
        if(!new File("c:\\uBox\\Cliente\\" + this.username).exists()){        
            if(new File("c:\\uBox\\Cliente\\" + this.username).mkdirs())
                System.out.println("Diretorio criado com sucesso! - Local:c:\\uBox\\Cliente\\" + this.username );                      
            else
                System.out.println("Erro ao criar o diretório pessoal!");
        }
    };    
    public void copiarArquivo(File source, File destination) throws IOException {
        if (destination.exists())
        destination.delete();

        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;

        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
       }
    }
    
    // GETTERS AND SETTERS
    @Override
    public void setName(String name) {
        this.username = name;
    }
    @Override
    public void setPass(String pass) {
        this.password = pass;
    }
    @Override
    public void setDate() {
        this.dtCreate = new Date();
    }
    @Override
    public String getName() {
        return this.username;
    }
    @Override
    public String getPass() {
        return this.password;
    }
    @Override
    public Date getDate() {
        return this.dtCreate;
    }
}
