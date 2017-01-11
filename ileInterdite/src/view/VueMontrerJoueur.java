/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author asus
 */
public class VueMontrerJoueur {

    private JFrame window;
    private JPanel panelGlobale;
    private JLabel j1, j2, j3, j4;
    private GridLayout gl = new GridLayout(5, 1);

    public VueMontrerJoueur() {
        window = new JFrame();
        window.setSize(200, 200);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit =  Toolkit.getDefaultToolkit(); 
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width*41 / 100 +360, dim.height*21 / 100 + 100);
        panelGlobale = new JPanel(gl);
        
        panelGlobale.add(new JLabel("Joueur :"));
        
        j1 = new JLabel();
        j1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelGlobale.add(j1);

        j2 = new JLabel();
        j2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelGlobale.add(j2);

        j3 = new JLabel();
        j3.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelGlobale.add(j3);

        j4 = new JLabel();
        j4.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        panelGlobale.add(j4);
        
        window.add(panelGlobale);
        
        window.setVisible(true);
    }

    public void ecrireNom(int numJoueur, String nom) {
        switch (numJoueur) {
            case 1:
                j1.setText(nom);
                break;
            case 2:
                j2.setText(nom);
                break;
            case 3:
                j3.setText(nom);
                break;
            case 4:
                j4.setText(nom);
                break;

        }
    }
    
    public void fermerFenetre() {
        window.dispose();
    }
}
