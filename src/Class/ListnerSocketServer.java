package Class;

import Class.Usuario.UsuarioServidor;
import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListnerSocketServer implements Runnable {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;   
        private Map<String, ObjectOutputStream> streamMap = new HashMap<>(); 
        String username;

        public ListnerSocketServer(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            FileMessage message = null;            
            try {
                while((message = (FileMessage) inputStream.readObject()) != null){ 
                    
                    
                    UsuarioServidor us = new UsuarioServidor();
                    us.setName(message.getNomeUsuario());
                    username = us.getName();
                    us.setPass(message.getSenhaUsuario());
                    streamMap.put(us.getName(), outputStream);                    

                    System.out.println("ListnerSocketServer: Mensagem recebida - Usuário: " + us.getName());
                    
                    if(message.getFile() != null){
                        if(message.getFile().getName().equals("auth")){
                            // Realizar autenticação do usuário e dar um retorno.......
                            System.out.println("ListnerSocketServer: Arquivo de autenticação recebido...");
                            us.gravarArquivo(username);
                            
                            FileReader fr = new FileReader(message.getFile());
                            BufferedReader br = new BufferedReader(fr);
                            
                            String linha = br.readLine(); 
                            String array[] = new String[2];
                            array = linha.split("@");

                            System.out.println("ListnerSocketServer: nome=" +array[0]+" senha="+array[1]);

                            if(us.autenticar(array[0], array[1])){
                                System.out.println("ListnerSocketServer: Usuário autenticado.");
                                //outputStream.writeObject(new FileMessage(username+", você está autenticado ao servidor!"));
                                outputStream.writeObject(new FileMessage(true, username+", você está autenticado ao servidor!"));
                            }
                            else{
                                System.out.println("ListnerSocketServer: Usuário não está autenticado.");
                                //outputStream.writeObject(new FileMessage(username+", você não conseguiu autenticar-se ao servidor!"));
                                outputStream.writeObject(new FileMessage(false, username+", você NÃO está autenticado ao servidor!"));
                            }
                            
                            fr.close();
                        }
                        
                        
                        
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!us.getName().equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                            }
                        }   
                        us.salvarMensagem(message);
                    }
                    else{
                        us.cadastar();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                streamMap.remove(username);
                System.out.println("O usuário  : #" + username + "# desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }       
    }