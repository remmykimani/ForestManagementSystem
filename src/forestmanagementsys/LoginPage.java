package forestmanagementsys;
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.sql.*;



public class LoginPage extends javax.swing.JFrame {

    public LoginPage() 
    {
        initComponents();
        Driver driver = new Driver();
        Connection conn=driver.connection;

        if (driver.doConnection())
        {
            lblconnectionState.setText("CONNECTION ACTIVE");
            lblconnectionState.setForeground(new Color(12, 140, 1));
        } else
        {
            lblconnectionState.setText("OFFLINE");
            lblconnectionState.setForeground(new Color(109, 1, 1));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblInstruction = new javax.swing.JLabel();
        txtUserID = new javax.swing.JTextField();
        txtpassLogin = new javax.swing.JPasswordField();
        lblEnterID = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnLoginSubmit = new javax.swing.JButton();
        btnClearLogin = new javax.swing.JButton();
        btnCloseLogin = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblconnectionState = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LOGIN");
        setLocation(new java.awt.Point(600, 200));

        lblInstruction.setText("PLEASE ENTER CREDENTIALS TO LOGIN");

        txtpassLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpassLoginActionPerformed(evt);
            }
        });

        lblEnterID.setText("USER ID: ");

        jLabel2.setText("PASSWORD: ");

        btnLoginSubmit.setText("SUBMIT");
        btnLoginSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginSubmitActionPerformed(evt);
            }
        });

        btnClearLogin.setText("CLEAR");
        btnClearLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearLoginActionPerformed(evt);
            }
        });

        btnCloseLogin.setText("CANCEL");
        btnCloseLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCloseLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseLoginActionPerformed(evt);
            }
        });

        lblconnectionState.setText("Waiting Connection Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblInstruction)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(lblEnterID)
                                    .addComponent(btnClearLogin, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtpassLogin, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtUserID, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnLoginSubmit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                        .addComponent(btnCloseLogin))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblconnectionState, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInstruction, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEnterID))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtpassLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoginSubmit)
                    .addComponent(btnClearLogin)
                    .addComponent(btnCloseLogin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblconnectionState)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtpassLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpassLoginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassLoginActionPerformed

    private void btnLoginSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginSubmitActionPerformed
         ResultSet rs;
       try
        {
         QueryProcess queryproc=new QueryProcess();
        String userID=txtUserID.getText().trim();
        String pass=txtpassLogin.getText().trim();
        
        
        String query="SELECT RANGER_ID,USERPASS FROM RANGER WHERE RANGER_ID='"+userID+"'AND USERPASS='"+pass+"'";
        rs=queryproc.exQuery(query);
        int count=0;
        while(rs.next())
        {
            count=count+1;
        }
        if (count==1)
        {
            JOptionPane.showMessageDialog(null,"Access Granted!");
            MainPage mp=new MainPage();
           MainPage.main(null);
           setVisible(false);
        }
        else if(count>1)
        {
            JOptionPane.showMessageDialog(null,"Denied!");
        }
        else
        {
             JOptionPane.showMessageDialog(null,"USER NOT FOUND!");
        }
        }
        catch(Exception ex)
        {
            System.out.println("QUERY ERROR: "+ex.getMessage());
                    JOptionPane.showMessageDialog(null, "QUERY ERROR: "+ex.getMessage());
        }
        
        
    }//GEN-LAST:event_btnLoginSubmitActionPerformed

    private void btnCloseLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseLoginActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btnCloseLoginActionPerformed

    private void btnClearLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearLoginActionPerformed
        // TODO add your handling code here:
        txtUserID.setText("");
        txtpassLogin.setText("");
    }//GEN-LAST:event_btnClearLoginActionPerformed

    /**
     *
     * @param args
     */
    public static void main(String args[]) 
    {
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
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClearLogin;
    private javax.swing.JButton btnCloseLogin;
    private javax.swing.JButton btnLoginSubmit;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblEnterID;
    private javax.swing.JLabel lblInstruction;
    private javax.swing.JLabel lblconnectionState;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JPasswordField txtpassLogin;
    // End of variables declaration//GEN-END:variables
}
