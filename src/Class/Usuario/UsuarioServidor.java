package Class.Usuario;

import Class.interfaces.IUsuario;
import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioServidor implements IUsuario{
    private String username;
    private String password;
    private Date dtCreate;

    public UsuarioServidor() {
        this.dtCreate = new Date();
    }

    @Override
    public void cadastar(){
        this.criarDiretorio();
        
        String dir = "C:\\uBox\\Servidor\\dados.dat";

        if(new File(dir).exists()){            
            gravarArquivo(dir);
        }
        else{
            try {
                new File(dir).createNewFile();
                gravarArquivo(dir);
            } catch (IOException ex) {
                Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Os dados do usuário foram persistidos no arquivo!");
    }
    @Override
    public void remover(){}
    @Override
    public void editar(){}
    
    public void salvarMensagem(FileMessage message){
      String uriServer = "c:\\uBox\\Servidor\\"; 
        try {                
            FileInputStream fileInputStream = new FileInputStream(message.getFile());
            new File(uriServer + message.getNomeUsuario() +"\\.properties").createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream( uriServer + message.getNomeUsuario() + "\\" + message.getFile().getName());                
            
            FileChannel fin = fileInputStream.getChannel();
            FileChannel fout = fileOutputStream.getChannel();                
            long size = fin.size();                
            fin.transferTo(0, size, fout);
        
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }   
    }
    public void salvarMensagem(FileMessage message, ArrayList<String> share){
        String dirBaseServer = "c:\\uBox\\Servidor\\"; 
        try {                
            share.add(message.getNomeUsuario());// Adiciona o cliente no arrayList                
            for(int i=0;i<share.size();i++){
                FileInputStream fileInputStream = new FileInputStream(message.getFile());
                new File(dirBaseServer + share.get(i)).mkdir();
                new File(dirBaseServer + share.get(i) +"\\.properties").createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream( dirBaseServer + share.get(i) + "\\" + message.getFile().getName());                
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

    
    public boolean autenticar(String userAuth, String passAuth) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(new File("c:\\uBox\\Servidor\\dados.dat"));
        BufferedReader br = new BufferedReader(fr);
        String linha = "not empty";
        do{
          linha = br.readLine();
          if(linha == null){
            br.close();
            fr.close();
            return false;
          }
          if(linha.equals(userAuth+"#"+passAuth))
            return true;
        }while(linha != null);
        return false;
        //br.close();
        //fr.close();
        //return false;
    }
    
    
    
    @Override
    public void criarDiretorio(){
        if(!new File("c:\\uBox\\Servidor\\" + this.username).exists()){   
            if(new File("c:\\uBox\\Servidor\\" + this.username).mkdirs())
                System.out.println("Diretorio criado com sucesso!");                      
            else
                System.out.println("Erro ao criar o diretório pessoal do usuário " + this.username);
        }
    }; 
    public void gravarArquivo(String dir){
        try (FileWriter fw = new FileWriter(dir, true);
            BufferedWriter bw = new BufferedWriter(fw)) {
            String str = username + "#" + password;
            bw.write(str);
            bw.newLine();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
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
