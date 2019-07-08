/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Class.Usuario.UsuarioCliente;
import Class.tree.CreateChildNodes;
import Class.tree.FileNode;
import com.fe44eira.app.bean.FileMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class ClientePrincipal extends javax.swing.JFrame {
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JTree tree;
    private UsuarioCliente user;
    public static ArrayList<String> listaUsers = new ArrayList();
    public DefaultMutableTreeNode selectedNo = null;
    
    public ClientePrincipal() {
        initComponents();
    }
    public ClientePrincipal(UsuarioCliente u) {
        initComponents();
        this.user = u;
        setTitle("ClientePrincipal - Usuário: " + u.getName());
        System.out.println("ClientePrincipal: nome=" + this.user.getName()+" pass="+this.user.getPass());
        
    }
    public void listarArvore(){
        jScrollPane1.setVisible(false);  
        //File fileRoot = new File("C://uBox//Cliente//"+this.user.getName()+"//");    
        File fileRoot = new File("C://uBox//Cliente//"+this.user.getName());       
        root = new DefaultMutableTreeNode(new FileNode(fileRoot));
        treeModel = new DefaultTreeModel(root);         
        jTree1.setModel(treeModel);        
        jTree1.setRootVisible(true);
        jTree1.setShowsRootHandles(true);
        add(jTree1);              
        CreateChildNodes ccn = new CreateChildNodes(fileRoot, root);        
        new Thread(ccn).start();
        jStatusCliente.setText("Conectado ao servidor!");
    }
    public static ArrayList<String> getListaUsers() {
        return listaUsers;
    }
    public static void setListaUsers(ArrayList<String> listaUsers) {
        ClientePrincipal.listaUsers = listaUsers;
    }

    
    //GERADO AUTOMATICAMENTE
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jbSincronizar = new javax.swing.JButton();
        jbEnviarArquivo = new javax.swing.JButton();
        jbCompartilhar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jbAtualizarListaUsuarios = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        jStatusCliente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Carregando Usuário...." };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionBackground(new java.awt.Color(255, 0, 102));
        jScrollPane3.setViewportView(jList1);

        jbSincronizar.setText("Sincronizar");
        jbSincronizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSincronizarActionPerformed(evt);
            }
        });

        jbEnviarArquivo.setText("Enviar Arquivo");
        jbEnviarArquivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEnviarArquivoActionPerformed(evt);
            }
        });

        jbCompartilhar.setText("Compartilhar");
        jbCompartilhar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCompartilharActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 0, 51));

        jScrollPane1.setEnabled(false);
        jScrollPane1.setWheelScrollingEnabled(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.setName(""); // NOI18N
        jTree1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jTree1ComponentShown(evt);
            }
        });
        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbAtualizarListaUsuarios.setText("Atualizar Lista Usuário");
        jbAtualizarListaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAtualizarListaUsuariosActionPerformed(evt);
            }
        });

        jToolBar1.setRollover(true);

        jStatusCliente.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jStatusCliente.setText("Aguardando resposta do servidor....");
        jToolBar1.add(jStatusCliente);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAtualizarListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jbEnviarArquivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                        .addComponent(jbCompartilhar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbSincronizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbCompartilhar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbSincronizar, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEnviarArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbAtualizarListaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // MÉTODOS COM EVENTOS DO FORM
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        jPanel1.setVisible(false);
        jStatusCliente.setText("Conectou-se ao servidor!");
        this.listarArvore();
        try {
            this.user.obterListaUsuarios();
        } catch (IOException ex) {
            Logger.getLogger(ClientePrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened
    private void jTree1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTree1ComponentShown

    }//GEN-LAST:event_jTree1ComponentShown
    private void jbSincronizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSincronizarActionPerformed
       
    }//GEN-LAST:event_jbSincronizarActionPerformed
    private void jbEnviarArquivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEnviarArquivoActionPerformed
        user.enviarArquivo();
        this.listarArvore();
        this.atualizarListaUsuario();
        jStatusCliente.setText("Arquivo foi enviado!");
    }//GEN-LAST:event_jbEnviarArquivoActionPerformed
    private void jbAtualizarListaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAtualizarListaUsuariosActionPerformed
        this.atualizarListaUsuario();
    }//GEN-LAST:event_jbAtualizarListaUsuariosActionPerformed
    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        //atualizarListaUsuario();
        //listarArvore();
        
    }//GEN-LAST:event_formMouseEntered
    private void jbCompartilharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCompartilharActionPerformed
        JOptionPane.showMessageDialog(rootPane, "Compartilhamento efetuado!");
        Object sel =null;
        ArrayList<String> compartilharComUsuarios = new ArrayList<>();
        int[] selectedIx = this.jList1.getSelectedIndices();      

        for (int i = 0; i < selectedIx.length; i++) {
            sel = jList1.getModel().getElementAt(selectedIx[i]);
            //System.out.println("Sel: "+sel);
            compartilharComUsuarios.add(sel.toString());
        }

        for (int i = 0; i < compartilharComUsuarios.size(); i++) {
            System.out.println("User: "+compartilharComUsuarios.get(i));
        }
        
        System.out.println("FileSelected: "+this.selectedNo);
        
        if(user.getName() != null){
            if(this.selectedNo == null){
                JOptionPane.showMessageDialog(rootPane, "Selecione um arquivo na Árvore de Arquivos!");
            }
            else{
                
                if(this.selectedNo.isRoot()){
                    JOptionPane.showMessageDialog(rootPane, "Não pode compartilhar o nó Raiz");
                }
                else{
                    if(!compartilharComUsuarios.isEmpty()){
                        File ShareFile = new File("C:\\uBox\\Cliente\\"+user.getName()+"\\"+this.selectedNo);
                        FileMessage fmCompartilhar = new FileMessage(user.getName(),ShareFile, compartilharComUsuarios);

                        try {
                            user.compartilharArquivo(fmCompartilhar);
                        } catch (IOException ex) {
                            Logger.getLogger(ClientePrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                    else{
                        JOptionPane.showMessageDialog(rootPane, "Selecione ao menos um usuário para compartilhar!");
                    }
                }
            }
        }
        else{
            System.out.println("&&&&&&&&&&&& USUARIO NULO - NÃO HÁ COMO REALIZAR COMPARTILHAMENTO &&&&&&&&&&&&&&&&&");
        }
        
        
        
        
    }//GEN-LAST:event_jbCompartilharActionPerformed
    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        this.selectedNo =  (DefaultMutableTreeNode)jTree1.getLastSelectedPathComponent(); 
    }//GEN-LAST:event_jTree1ValueChanged

    
    
    public void atualizarListaUsuario(){
        System.out.println("********************** ATUALIZAR LISTA DE USUARIO*******************************");
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if(listaUsers == null || listaUsers.isEmpty()){
            System.out.println("############### LISTA DE USUÁRIOS VAZIA OU NULA #######################");
        }
        else{
            for (String percorrer : listaUsers) {
                if(!this.user.getName().equals(percorrer) && this.user.getName() != null && percorrer != null){
                    listModel.addElement(percorrer);
                    System.out.println("percorrer: "+percorrer);
                    jList1.setModel(listModel);
                }
            }
        }
    }
    
    //  MAIN APP
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
            java.util.logging.Logger.getLogger(ClientePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientePrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientePrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel jStatusCliente;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton jbAtualizarListaUsuarios;
    private javax.swing.JButton jbCompartilhar;
    private javax.swing.JButton jbEnviarArquivo;
    private javax.swing.JButton jbSincronizar;
    // End of variables declaration//GEN-END:variables
}
