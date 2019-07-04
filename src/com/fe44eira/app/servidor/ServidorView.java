package com.fe44eira.app.servidor;

import com.fe44eira.app.bean.FileMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorView extends javax.swing.JFrame {
    private ServerSocket serverSocket;
    private Socket socket;
    private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();
   
    private int port;
    private String dirServidor = "c:\\uBox\\Servidor\\";
    private boolean status;

 
    
    public ServidorView() {
        initComponents();
        
        try {
           // this.port = Integer.parseInt(JOptionPane.showInputDialog("Defina uma porta para o Servidor?"));   
           //serverSocket = new ServerSocket(this.port);  
            serverSocket = new ServerSocket(5555);  
            
            //JOptionPane.showMessageDialog(null, "Servidor está ativo!");            
            
            System.out.println("Servidor ON!");          
            
            if(new File(dirServidor).exists()){
                System.out.println("O diretorio ja existe!");
            }
            else{
                System.out.println("O diretorio ainda não existe, tentaremos criar-lo!");
                if(new File(dirServidor).mkdirs()){
                    System.out.println("Oba!, criamos com sucesso o diretorio no servidor!");
                }
                else{
                    System.out.println("Epa, nos não conseguimos criar o diretório, tente novamente.");
                }
            }
            
            while(true){
                socket = serverSocket.accept();
                new Thread(new ServidorView.ListnerSocket(socket)).start();                
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaEventos = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Porta de Operação");

        jButton1.setText("Run Server");

        jTextAreaEventos.setEditable(false);
        jTextAreaEventos.setColumns(20);
        jTextAreaEventos.setRows(5);
        jScrollPane1.setViewportView(jTextAreaEventos);

        jLabel2.setText("Eventos");

        jPanel1.setBackground(new java.awt.Color(255, 0, 102));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("Status: Inativo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1)
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(26, 26, 26)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                            .addComponent(jButton1))))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public class ListnerSocket implements Runnable {
        private ObjectOutputStream outputStream;
        private ObjectInputStream inputStream;        
        
        public ListnerSocket(Socket socket) throws IOException {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }
        
        public void run() {
            FileMessage message = null;
            
            try {
                while((message = (FileMessage) inputStream.readObject()) != null){                    
                    streamMap.put(message.getNomeUsuario(), outputStream);
                    System.out.println("Online: " + message.getNomeUsuario());
                    if(message.getFile() != null){
                        //salvar(message);                        
                        for(Map.Entry<String, ObjectOutputStream> kv : streamMap.entrySet()){
                            if(!message.getNomeUsuario().equals(kv.getKey())){
                                kv.getValue().writeObject(message);
                                //salvar(message); 
                                salvarMensage(message, message.getUserShare());
                            }
                        }                        
                    }
                    else{                        
                        String dirCompleto = dirServidor + message.getNomeUsuario();// ESTOU CRIANDO A PASTA DO USUÁRIO ASSIM QUE ELE DIGITA SEU NOME NO CLIENTE                        
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
                streamMap.remove(message.getNomeUsuario());
                System.out.println("O cliente : -" + message.getNomeUsuario() + "- desconectou-se!");
            }            
        }         
        private void salvarMensage(FileMessage message, ArrayList<String> share) {
            try {
                
                share.add(message.getNomeUsuario());// Adiciona o cliente no arrayList
                
                for(int i=0;i<share.size();i++){
                    //System.out.println(share.get(i));
                    FileInputStream fileInputStream = new FileInputStream(message.getFile());
                    new File(dirServidor + share.get(i)).mkdir();
                    new File(dirServidor + share.get(i) +"\\.properties").createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream( dirServidor + share.get(i) + "\\" + message.getFile().getName());                
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
        
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (status == true)
            jLabel3.setText("Ativo");
        else
            jLabel3.setText("Inativo"); 
    }
    
    
    
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServidorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServidorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServidorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServidorView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServidorView().setVisible(true);;
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextAreaEventos;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}


