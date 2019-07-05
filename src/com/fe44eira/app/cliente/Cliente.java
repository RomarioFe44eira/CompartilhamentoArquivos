package com.fe44eira.app.cliente;

import Class.Usuario.Usuario;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    private  Socket socket;
    private ObjectOutputStream outputStream;    
    
    private String nome;
    private String pass;

    public Cliente() throws IOException {
        Usuario u = new Usuario("Nash", "Scrambler");
        //u.cli_AddNewUser();
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
