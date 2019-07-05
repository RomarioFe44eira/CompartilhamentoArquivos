package com.fe44eira.app.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FileMessage implements Serializable {
    private String username;
    private String password;
    private File file;
    
    private ArrayList<String> share = new ArrayList();
    
    public FileMessage() {}
    public FileMessage(String username, String password){
        this.username = username;
        this.password = password; 
        System.out.println("Class FileMessage: Usuário="+username+" Senha="+password);        
    }
    public FileMessage(String username, File file) {
        this.username = username;
        this.file = file;
    }
    public FileMessage(String username, File file, ArrayList<String> share ) {
        this.username = username;
        this.file = file;
        this.share = share;
        
        System.out.println("FileMessage: Recebido");
         
        for(int i=0;i<share.size();i++){
            System.out.println("COMPARTILHADO COM USUARIO: " + share.get(i));
        }
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    
    public void shareAddUser(String nome){
        this.share.add(nome);       
    }
    public ArrayList<String> getUserShare() {
        return share;
    }
    public void setShareUsers(ArrayList<String> share) {
        this.share = share;
    }
    
    // GETTERS AND SETTERS
    public String getNomeUsuario() {
        return username;
    }
    public void setNomeUsuario(String username) {
        this.username = username;
    }
    public String getSenhaUsuario() {
        return this.password;
    }
    public void setSenhaUsuario(String password) {
        this.password = password;
    }
    
    
}
