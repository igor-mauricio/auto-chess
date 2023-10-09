package infra.ui;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

import application.Automation;
import infra.persistance.Database;
import infra.persistance.Uci;

/**
 *
 * @author Godofga
 */
public class AutoChess extends javax.swing.JFrame {
    Automation obs;
    boolean running = false;
    /**
     * Creates new form GUI
     */
    public AutoChess() {
        initComponents();
        try {
            obs= new Automation();
            Database.resetBoard();
        } catch (AWTException ex) {
            Logger.getLogger(AutoChess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {

        jButtonBot = new javax.swing.JButton();
        jButtonOn = new javax.swing.JButton();
        jLabelSide = new javax.swing.JLabel();
        jButtonResolucao = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AutoChess");
        setBackground(new java.awt.Color(51, 0, 153));
        setForeground(new java.awt.Color(102, 0, 153));
        setIconImage(new ImageIcon("/src/images/icon.ico").getImage()
        );

        jButtonBot.setText("Bot");
        jButtonBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBotActionPerformed(evt);
            }
        });

        jButtonOn.setText("Turn on");
        jButtonOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOnActionPerformed(evt);
            }
        });

        jLabelSide.setText("You're playing with *** pieces!");

        jButtonResolucao.setText("Resolution:1050p");
        jButtonResolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResolucaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButtonOn, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSide)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonResolucao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBot)))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonOn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelSide)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonResolucao)
                            .addComponent(jButtonBot))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBotActionPerformed
        // TODO add your handling code here:
        Database.setCompetitive(!Database.getCompetitive());
        if(Database.getCompetitive())
            jButtonBot.setText("Comp");
        else
            jButtonBot.setText("Bot");
    }//GEN-LAST:event_jButtonBotActionPerformed

    private void jButtonOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOnActionPerformed
        // TODO add your handling code here:
        if(!running){
                obs.start();
                obs.checkSide();
                jLabelSide.setText(Database.getWhite()?"You are playing with white pieces":"You are playing with black pieces");
                
            
        }
        running = true;
    }//GEN-LAST:event_jButtonOnActionPerformed

    private void jButtonResolucaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResolucaoActionPerformed
        // TODO add your handling code here:
        Database.setResolucao(Database.getResolucao()+1);
        if(Database.getResolucao()>2)
            Database.setResolucao(0);
        switch(Database.getResolucao()){
            //1680x1050
            case 0:
                jButtonResolucao.setText("Resolution:1050p");
                break;
            //1920x1080
            case 1:
                jButtonResolucao.setText("Resolution:1080p");
                break;
            //1600x900
            case 2:
                jButtonResolucao.setText("Resolution:900p");
                break;
        }
        
        
    }//GEN-LAST:event_jButtonResolucaoActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AutoChess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoChess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoChess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoChess.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AutoChess().setVisible(true);
            Uci channel = new Uci();
            channel.start();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBot;
    private javax.swing.JButton jButtonOn;
    private javax.swing.JButton jButtonResolucao;
    private javax.swing.JLabel jLabelSide;
    // End of variables declaration//GEN-END:variables
}
