/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author pierreju
 */
public class VueGagner{
    
    private JFrame window;
    private JPanel panel,panelSud,panelCentre;
    private JLabel message;
    private JButton Fin = new JButton("OK");
    
    public VueGagner(){
         window = new JFrame("Gagner");

        JOptionPane.showMessageDialog(window, "Vous avez gagné", "Bravo!!", JOptionPane.INFORMATION_MESSAGE);
        
    }
    
     public void actionListener(JButton button) {
        Fin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
}
