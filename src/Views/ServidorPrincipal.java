package Views;

import Class.ListnerSocketServer;
import com.fe44eira.app.servidor.Servidor;
import com.fe44eira.app.servidor.ServidorView;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ServidorPrincipal extends javax.swing.JFrame {
    private ServerSocket serverSocket;
    private Socket socket;
    
    private Map<String, ObjectOutputStream> streamMap = new HashMap<String, ObjectOutputStream>();
   
    private int port;    
    private String dirServidor = "c:\\uBox\\Servidor\\";
    private boolean status;
    
    private String logLong;

    public ServidorPrincipal() {
        initComponents();
        setTitle("Servidor Principal");
        this.port = Integer.parseInt(JOptionPane.showInputDialog("Defina uma porta para o Servidor?","5555"));  
        this.status = true;
    }

    public void runServer(){
        try {
            System.out.println("############################    SERVIDOR INICIALIZADO    ############################################");  
            serverSocket = new ServerSocket(this.port);  
            
            //JOptionPane.showMessageDialog(null, "Servidor está ativo!");    
            
            
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
            this.textAreaLog.append("Aguardando conexão.....");
            while(true){
                System.out.println("Aguardando conexão.....");
                this.textAreaLog.append("Aguardando conexão.....");
                socket = serverSocket.accept();
                this.textAreaLog.append("Conexão aceita pelo servidor");
                System.out.println("Conexão aceita pelo servidor");
                new Thread(new ListnerSocketServer(socket)).start(); 
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbRunServer = new javax.swing.JButton();
        jbStopServer = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaLog = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jbRunServer.setText("Run Server");
        jbRunServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRunServerActionPerformed(evt);
            }
        });

        jbStopServer.setText("Stop Server");

        jLabel1.setText("Log do sistema");

        textAreaLog.setColumns(20);
        textAreaLog.setRows(5);
        jScrollPane2.setViewportView(textAreaLog);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbRunServer, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbStopServer, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(178, 178, 178))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbStopServer, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jbRunServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbRunServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRunServerActionPerformed
        runServer();
    }//GEN-LAST:event_jbRunServerActionPerformed

    
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
            java.util.logging.Logger.getLogger(ServidorPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServidorPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServidorPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServidorPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServidorPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbRunServer;
    private javax.swing.JButton jbStopServer;
    private javax.swing.JTextArea textAreaLog;
    // End of variables declaration//GEN-END:variables
}
