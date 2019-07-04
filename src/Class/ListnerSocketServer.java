package Class;

import com.fe44eira.app.bean.FileMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListnerSocketServer implements Runnable {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;   
        private Map<String, ObjectOutputStream> streamMap = new HashMap<>(); 
        private String dirBaseServer = "c:\\uBox\\Servidor\\"; 
        
        String username;
        String password;
        
        public ListnerSocketServer(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public void run() {
            FileMessage message = null;            
            try {
                while((message = (FileMessage) inputStream.readObject()) != null){ 
                    username = message.getNomeUsuario();
                    password = message.getSenhaUsuario();
                   
                    streamMap.put(username, outputStream);                    
                    System.out.println("Mensagem recebida: " + username);
                    
                    if(message.getFile() != null){
                        //salvar(message);                        
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!username.equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                                //salvar(message); 
                                salvarMensage(message, message.getUserShare());
                            }
                        }                        
                    }
                    else{
                        System.out.println("Não há nada compartilhado!");
                            System.out.println("Cadastrar usuario.....");
                            
                            String dir = "C:\\uBox\\Servidor\\dados.dat";

                            if(!new File(dir).exists()){            
                                System.out.println("Vamos criar um arquivo de dados, pois ainda não existe!");
                                if(new File(dir).createNewFile()){
                                    System.out.println("Arquivo criado com sucesso!"); 
                                    gravaUsuarioArquivo(username, password);
                                }
                                else
                                    System.out.println("Arquivo não foi criado com sucesso!");
                            }
                            else{
                                gravaUsuarioArquivo(username, password);
                            }

                        
                        
                        String dirCompleto = dirBaseServer + username;// ESTOU CRIANDO A PASTA DO USUÁRIO ASSIM QUE ELE DIGITA SEU NOME NO CLIENTE                        
                        if(new File(dirCompleto).exists())
                            System.out.println("O diretorio " + dirCompleto + " já existe no servidor!");                        
                        else{
                            if(new File(dirCompleto).mkdirs())
                                System.out.println("Diretório "+ dirCompleto +" foi criado com sucesso");                            
                            else
                                System.out.println("Ocorreu um erro ao criar o diretório "+ dirCompleto +" !");
                        }
                        
                    }
                    
                }
            } catch (IOException | ClassNotFoundException ex) {
                streamMap.remove(username);
                System.out.println("O usuário  : #" + username + "# desconectou-se!");
                //Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } 
        
        public void gravaUsuarioArquivo(String username, String password) throws IOException{
            String dir = "C:\\uBox\\Servidor\\dados.dat";
            try (FileWriter fw = new FileWriter(dir, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
                String str = username + "#" + password;
                bw.write(str);                                    
                bw.newLine();
                bw.close();
                fw.close();
            }
            System.out.println("Buffer e Arquivo foram fechados!");
        }
        private void salvarMensage(FileMessage message, ArrayList<String> share) {
            try {                
                share.add(message.getNomeUsuario());// Adiciona o cliente no arrayList                
                for(int i=0;i<share.size();i++){
                    //System.out.println(share.get(i));
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
        
    }