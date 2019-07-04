package com.fe44eira.app.cliente;

import Class.ListnerSocket;
import Class.Usuario;
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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private  Socket socket;
    private ObjectOutputStream outputStream;    
    
    private String nome;
    private String pass;

    public Cliente() throws IOException {
        Usuario u = new Usuario("Nash", "Scrambler");
        u.cli_AddNewUser();
        //u.cli_criarDiretorioLocalUsuario();
        u.cli_sendFile();
    }
    
    public static void main(String[] args) {
        try {
            new Cliente();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
