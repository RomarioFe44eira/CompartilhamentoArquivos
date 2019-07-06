package Class.Usuario;

import java.io.IOException;

public class teste {
    public static void main(String[] args) throws IOException {
//        UsuarioCliente uc = new UsuarioCliente();
//        uc.setName("Evandro");
//        uc.setPass("Evhgweoru");
//        
//        uc.autenticar();
        
        //uc.cadastar();
        
        //uc.enviarArquivo();
        
        UsuarioServidor us = new UsuarioServidor();
        us.setName("Bruna");
        us.setPass("123");
        
        us.cadastar();
        us.autenticar(us.getName(), us.getPass());
        
                
        
    }
}
