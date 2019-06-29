package com.fe44eira.app.bean;

import java.io.File;
import java.io.Serializable;


public class FileMessage implements Serializable {
    private String cliente;
    private File file;
    
    public FileMessage() {
    }
    public FileMessage(String cliente, File file) {
        this.cliente = cliente;
        this.file = file;
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
    
}
