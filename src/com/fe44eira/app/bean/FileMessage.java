package com.fe44eira.app.bean;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;



public class FileMessage implements Serializable {
    private String cliente;
    private File file;
    
    private ArrayList<String> compartilhamento = new ArrayList();
    
    public FileMessage() {
    }
    public FileMessage(String cliente, File file) {
        this.cliente = cliente;
        this.file = file;
    }
    
     public FileMessage(String cliente, File file, ArrayList<String> share ) {
        this.cliente = cliente;
        this.file = file;
        this.compartilhamento = share;
        
        System.out.println("FileMessage: Recebido");
         
        for(int i=0;i<share.size();i++){
            System.out.println("COMPARTILHADO COM USUARIO: " + share.get(i));
        }
         
        
    }

    public FileMessage(String cliente) {
        this.cliente = cliente;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public void addUserShare(String nome){
        this.compartilhamento.add(nome);       
    }    
    
    public ArrayList<String> getCompartilhamento() {
        return compartilhamento;
    }

    public void setCompartilhamento(ArrayList<String> compartilhamento) {
        this.compartilhamento = compartilhamento;
    }
    
    
}
