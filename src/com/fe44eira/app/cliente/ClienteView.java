package com.fe44eira.app.cliente;

import com.fe44eira.app.bean.FileMessage;
import Class.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class ClienteView extends javax.swing.JFrame {   
    private Socket socket;
    private ObjectOutputStream outputStream;       
    private String nomeCliente;  
    @SuppressWarnings("FieldMayBeFinal")
    private String host;
    @SuppressWarnings("FieldMayBeFinal")
    private int port;
    
    public ClienteView() throws ParseException, IOException {
        initComponents();
        
        //this.nomeCliente = JOptionPane.showInputDialog("Qual é o seu nome?");
        
        this.host = JOptionPane.showInputDialog(this.nomeCliente + ", Qual é o IP do servidor?");
        this.port = Integer.parseInt(JOptionPane.showInputDialog("Qual é a porta do servidor?"));        
        
        this.socket = new Socket(this.host, this.port);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());        
        new Thread(new ListnerSocket(socket)).start();
        
        //JOptionPane.showMessageDialog(null, "Seja bem-vindo " + this.nomeCliente , "Nome definido",  WIDTH);               
        this.outputStream.writeObject(new FileMessage(this.nomeCliente)); // Enviando nome para servidor
        
        setTitle("Aplicação cliente - Usuário: " + this.nomeCliente);
        jLabelNomeCliente.setText("Nome: " + this.nomeCliente);
    }
   
    private void send(String nome) throws IOException {              
        JFileChooser fileChooser = new JFileChooser();       
        int opt = fileChooser.showOpenDialog(null);        
        if(opt == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            int numPessoas = Integer.parseInt(JOptionPane.showInputDialog("Compartilhar para quantas pessoas: "));        
            ArrayList<String> share = new ArrayList<>();            
            for (int i = 0; i < numPessoas; i++) {    
                share.add( JOptionPane.showInputDialog("Digite o nome da " + (i+1) +"º pessoa a compartilhar"));    
            }            
            this.outputStream.writeObject(new FileMessage(nome, file, share));
            JOptionPane.showMessageDialog(null, "O Arquivo foi enviado ao diretório do usuário " + nome + " no servidor");
        }
        else{
            JOptionPane.showMessageDialog(null, nome + ", seu arquivo não foi enviado ao servidor!");
        }
    }
    
    private static class ListnerSocket implements Runnable {
        private ObjectInputStream inputStream;  
        private String nome;        
        
        public ListnerSocket(Socket socket) throws IOException {
            this.inputStream = new ObjectInputStream(socket.getInputStream());            
        }

        @Override
        public void run() {
            FileMessage message = null;
            try {               
                while((message = (FileMessage) inputStream.readObject()) != null){
                    salvar(message); 
                    JOptionPane.showMessageDialog(null, message.getCliente() + " Enviou um arquivo para você, Nome do arquivo: " + message.getFile().getName(), "Arquivo Recebido......",  WIDTH);                                                                                                                                     
                }                
            } catch (IOException | ClassNotFoundException ex) {Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);}            
        }
        
     
        
        private void salvar(FileMessage message) { 
            try {               
                FileInputStream fileInputStream = new FileInputStream(message.getFile()); 
                String dirBase = "c:\\uBox\\Cliente\\";
                
                //this.nome = message.getCliente();
                
                if(new File(dirBase + message.getCliente()).exists()){
                    System.out.println("Diretorio do cliente já existe no Local: " + dirBase + message.getCliente());
                }
                else{
                    System.out.println("Ops, o diretorio do cliente não existe ainda, vamos tentar criar-lo!");
                    
                    if(new File(dirBase + message.getCliente()).mkdirs()){
                        System.out.println("Eba, o diretorio cliente foi criado, Local: " + dirBase + message.getCliente());
                    }
                    else{
                        System.out.println("Ops, não foi possível criar o diretorio: " + dirBase + message.getCliente());
                    }
                }               
                FileOutputStream fileOutputStream = new FileOutputStream(dirBase + message.getCliente() + "\\" + message.getFile().getName()); // Aqui faz acontecer
                
                FileChannel fin = fileInputStream.getChannel();
                FileChannel fout = fileOutputStream.getChannel();
                
                long size = fin.size();                
                fin.transferTo(0, size, fout);      
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jLabelNomeCliente = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButtonDefinir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIP = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelNomeCliente.setText("Nome: Pafuncio");

        jLabel1.setText("Arquivos do diretorio Local");

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList2);

        jButtonDefinir.setText("Definir");

        jLabel2.setText("IP Servidor destino:");

        jTextFieldIP.setText("localhost");

        jTextField2.setText("5555");

        jLabel3.setText("Porta do serviço:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jButton1.setText("Upload Arquivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabelNomeCliente))
                                .addGap(229, 229, 229))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 105, 105)))))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldIP)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDefinir, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelNomeCliente)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(102, 102, 102))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDefinir)))
                .addGap(0, 46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        try {        
            this.send(this.nomeCliente);
        } catch (IOException ex) {
            Logger.getLogger(ClienteView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ClienteView().setVisible(true);
                } catch (ParseException ex) {
                    Logger.getLogger(ClienteView.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ClienteView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonDefinir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelNomeCliente;
    private javax.swing.JList jList2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldIP;
    // End of variables declaration//GEN-END:variables
}
