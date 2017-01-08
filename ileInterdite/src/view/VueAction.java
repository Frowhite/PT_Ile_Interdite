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
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author asus
 */
public class VueAction extends Observable {
    private JFrame window;
    private JPanel panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(2, 2);
    private JLabel tNbDAction;
    private JButton bDeplace, bAssechee, bDonneCarte, bUtiliseCarte, bPrendreTresor;

    public VueAction() {
        window = new JFrame();
        window.setSize(300, 100);
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2 + 250, dim.height / 2 + 300);
        panelGlobale = new JPanel(new BorderLayout());
        //***Haut***
        //text nb d'action
        tNbDAction = new JLabel("Nombre d'actions restantes : 3");
        panelGlobale.add(tNbDAction, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel(gl);
        //boutton déplacer
        bDeplace = new JButton("Ce déplacer");
        panelCentre.add(bDeplace);
        //boutton assecher
        bAssechee = new JButton("Assecher tuile");
        panelCentre.add(bAssechee);
        //boutton donner carte
        bDonneCarte = new JButton("Donner carte");
        panelCentre.add(bDonneCarte);
        //boutton utiliser carte
        bUtiliseCarte = new JButton("Utiliser carte");
        panelCentre.add(bUtiliseCarte);
        
        panelGlobale.add(panelCentre, BorderLayout.CENTER);
        
        //***Bas***
        //boutton prendre tresor
        bPrendreTresor = new JButton("Prendre le trésor");
        panelGlobale.add(bPrendreTresor, BorderLayout.SOUTH);
        
        window.add(panelGlobale);
        window.setVisible(true);
    }
}
