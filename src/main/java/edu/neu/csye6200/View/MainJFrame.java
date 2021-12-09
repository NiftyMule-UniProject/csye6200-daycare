package edu.neu.csye6200.View;

import java.awt.CardLayout;
import javax.swing.JPanel;
import edu.neu.csye6200.Model.DayCare;

/**
 *
 * @author duzhang
 */
public class MainJFrame extends javax.swing.JFrame {

    public MainJFrame(DayCare daycare) {
        initComponents();

        setSize(400,330);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel loginView = new LoginView(daycare);

        mainWorkArea.add("LoginView", loginView);
        CardLayout layout = (CardLayout) mainWorkArea.getLayout();
        layout.next(mainWorkArea);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainWorkArea = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainWorkArea.setLayout(new java.awt.CardLayout());
        getContentPane().add(mainWorkArea, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainWorkArea;
    // End of variables declaration//GEN-END:variables
}
