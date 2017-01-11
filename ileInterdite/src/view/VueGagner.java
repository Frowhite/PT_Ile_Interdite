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
public class VueGagner extends JPanel {
    
    private JFrame window;
    private JPanel panel,panelSud,panelCentre;
    private JLabel message;
    private JButton Fin = new JButton("OK");
    
    public VueGagner(){
     window = new JFrame("Gagner");
        window.setSize(500,500);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2, dim.height / 2 );
       
        panel = new JPanel(new BorderLayout());
        panelSud = new JPanel(new GridLayout(1,3));
        message = new JLabel("BRAVO !!!! Vous Vous etes enfuis de l'ile avec les trésors");
        panelCentre = new JPanel((LayoutManager) message);
        
        for(int i = 0; i<3;i++){
            if(i == 2){
                panelSud.add(Fin);
            }
        }
        
        setVisible(true);
        
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
