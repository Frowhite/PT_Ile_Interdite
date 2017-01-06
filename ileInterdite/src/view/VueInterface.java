/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author dandelr
 */
public class VueInterface extends Observable{
      private JFrame window;
      private JPanel panelGlobale,panelCentre;
      private JButton bCommencerPartie,bInscrireJ,bQuitter;
      private JLabel titre;
      private Font font = new Font("Arial", 0, 25);
      private GridLayout gl = new GridLayout(4,1);
      public VueInterface(){
          window = new JFrame();
          window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
          window.setSize(360, 430);
          window.setLocation(470, 100);
          panelGlobale = new JPanel(new BorderLayout());
          //***haut***
          titre= new JLabel(new ImageIcon(getClass().getResource("/images/titre.png")));
          panelGlobale.add(titre, BorderLayout.NORTH);
          //***panel centre***
          gl.setVgap(15);
          panelCentre = new JPanel(gl);
          panelCentre.add(new JLabel(""));
          
          //boutton commencer partie
          bCommencerPartie = new JButton("Commencer partie");
          panelCentre.add(bCommencerPartie);
          bCommencerPartie.setFont(font);
          
          //boutton inscrire joueur
          bInscrireJ = new JButton("Inscrire joueur");
          panelCentre.add(bInscrireJ);
          bInscrireJ.setFont(font);
          
          //boutton quitter
          bQuitter = new JButton("Quitter");
          panelCentre.add(bQuitter);
          bQuitter.setFont(font);
          
          
          panelGlobale.add(panelCentre, BorderLayout.CENTER);
          
          
          
          
          window.add(panelGlobale);
          window.setVisible(true);
      }
}
