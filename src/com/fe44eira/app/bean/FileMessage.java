package com.fe44eira.app.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class FileMessage implements Serializable {
    private String username;
    private String password;

    private boolean auth;
    private String msg = null;
    private File file;
    
    private ArrayList<String> share = new ArrayList();
    private ArrayList<String> listaUsuarios = new ArrayList();
    
    public FileMessage() {}
    public FileMessage(String msg) {
        this.msg = msg;
    }
    public FileMessage(boolean auth, String msg) {
        this.auth = auth;
        this.msg = msg;
    }
    public FileMessage(String username, String password, boolean auth) {
        this.username = username;
        this.password = password;
        this.auth = auth;
    }
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
        
        System.out.println("FileMessage: Recebido arquivo compatilhado aos usuarios");
         
        for(int i=0;i<share.size();i++){
            System.out.println("COMPARTILHADO COM USUARIO: " + share.get(i));
        }
    }
     
    public FileMessage(ArrayList<String> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public ArrayList<String> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<String> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
    
    
    
}
