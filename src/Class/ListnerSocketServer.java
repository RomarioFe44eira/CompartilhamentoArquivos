package Class;

import Class.Usuario.UsuarioServidor;
import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
                    streamMap.put(us.getName(), outputStream); // ASSOCIANDO NOME DO USUÁRIO COM OUTPUTSTREAM
                    System.out.println("ListnerSocketServer: Mensagem recebida - Usuário: " + us.getName());
                    
//                    for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
//                          if(!us.getName().equals(kv.getKey()) && us.getName() != null){// VERIFICA SE USUARIO É IGUAL AO USUÁRIO NO ARRAY
//                              kv.getValue().writeObject(message);// ENVIANDO MESSAGEM AOS USUARIOS 
//                         }
//                    } 
                    
                    
                    if(message.getMsg() != null){
                        if(message.getMsg().equals("ListaUsuarios")){
                            System.out.println("************************ LISTA USUÁRIOS EXECUTADO ************************************");
                            if(!new File("C:\\uBox\\Servidor\\listaUsuarios.dat").exists()){
                                new File("C:\\uBox\\Servidor\\listaUsuarios.dat").createNewFile();
                            }
                            FileReader fr = new FileReader("C:\\uBox\\Servidor\\listaUsuarios.dat");
                            BufferedReader br = new BufferedReader(fr);
                            
                            String linha = br.readLine();
                            ArrayList<String> listaUsuarios = new ArrayList();

                            linha = br.readLine();
                            
                            while(linha != null){
                                listaUsuarios.add(linha); 
                                System.out.println("########### USUARIO: "+linha);
                                linha = br.readLine();
                            }
                            
                            FileMessage fm = new FileMessage(listaUsuarios);
                            fm.setAuth(true);
                            fm.setMsg("ReturnListaUsuarios");
                            
                            outputStream.writeObject(fm);
                             System.out.println("************************ FIM LISTA USUÁRIOS ************************************");
                        }
                       
                        // REALIZA O CADASTRO DE UM USUÁRIO
                        if(message.getMsg().equals("CadastroUsuario")){
                            System.out.println("*************************** CADASTRO USUÁRIO ************************");
                            if(message.getNomeUsuario() != null &&
                                message.getSenhaUsuario() != null &&
                                !message.getNomeUsuario().isEmpty() &&
                                !message.getSenhaUsuario().isEmpty())
                            {
                                UsuarioServidor uServer = new UsuarioServidor();
                                uServer.setName(message.getNomeUsuario());
                                uServer.setPass(message.getSenhaUsuario());
                                uServer.setDate();
                                uServer.cadastrar();// modificadoooo..
                                System.out.println("*********************MÉTODO EXECUTADO - CADASTRO USUÁRIO ************");
                            }
                        }
                        
                        if(message.getMsg().equals("DownFiles")){
                            File file = new File("c:\\uBox\\Servidor\\"+us.getName());
                            File afile[] = file.listFiles();
                            int i = 0;
                            for (int j = afile.length; i < j; i++) {
                                File arquivos = afile[i];
                                
                                FileMessage fmDown = new FileMessage(this.username, afile[i]);
                                fmDown.setMsg("ReceiverFiles");
                                        
                                for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                                    if(us.getName().equals(kv.getKey())){
                                        kv.getValue().writeObject(fmDown);
                                   }
                                } 
                                
                                
                                //System.out.println(arquivos.getName());
                               // outputStream.writeObject();
                            }
                            
                            
                        }
                        
                        
                    }
                    
                    
                    // TRABALHANDO COM ARQUIVO 
                    if(message.getFile() != null){
                        if(message.getFile().getName().equals("auth")){
                            message.setMsg("auth");
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
                                FileMessage fm = new FileMessage();
                                fm.setAuth(true);
                                fm.setNomeUsuario(array[0]);
                                fm.setSenhaUsuario(array[1]);
                                outputStream.writeObject(fm);
                            }
                            else{
                                System.out.println("ListnerSocketServer: Usuário não autenticado.");
                                FileMessage fms = new FileMessage(false, username+", você não conseguiu autenticar-se ao servidor!");
                                fms.setMsg("NoAuth");
                                outputStream.writeObject(fms);
                            }
                            fr.close();
                        }
                        
                        if(message.getMsg().equals("FileShare")){
                            if(message.getNomeUsuario() != null){
                               us.gravarArquivoCompartilhado(message); 
                            }
                        }
                        
                        
                        
//                        us.salvarMensagem(message);
                    }
                    else{
                        //us.cadastrar();
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                streamMap.remove(username);
                System.out.println("O usuário  : #" + username + "# desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }      
    }