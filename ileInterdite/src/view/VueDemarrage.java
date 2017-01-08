/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Commandes;

/**
 *
 * @author dandelr
 */
public class VueDemarrage extends Observable {

    private JFrame window;
    private JPanel panelGlobale, panelCentre;
    private JButton bCommencerPartie, bInscrireJ, bQuitter;
    private JLabel titre;
    private Font font = new Font("Arial", 0, 25);
    private GridLayout gl = new GridLayout(4, 1);

    public VueDemarrage() {
        window = new JFrame();
        window.setSize(340, 430);
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2 - 180, dim.height / 2 - 315);
        panelGlobale = new JPanel(new BorderLayout());
        //***haut***
        titre = new JLabel(new ImageIcon(getClass().getResource("/images/titre.png")));
        panelGlobale.add(titre, BorderLayout.NORTH);
        //***panel centre***
        gl.setVgap(15);
        panelCentre = new JPanel(gl);
        panelCentre.add(new JLabel(""));

        //boutton commencer partie
        bCommencerPartie = new JButton("Commencer partie");
        bCommencerPartie.setFont(font);
        bCommencerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Commandes.COMMENCER_PARTIE);
                    clearChanged();
            }
        });

        panelCentre.add(bCommencerPartie);

        //boutton inscrire joueur
        bInscrireJ = new JButton("Inscrire joueur");
        bInscrireJ.setFont(font);
        bInscrireJ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Commandes.INSCRIRE_JOUEUR);
                    clearChanged();
            }
        });
        panelCentre.add(bInscrireJ);
       

        //boutton quitter
        bQuitter = new JButton("Quitter");
        bQuitter.setFont(font);
        bQuitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    setChanged();
                    notifyObservers(Commandes.QUITTER);
                    clearChanged();
            }
        });
        panelCentre.add(bQuitter);
        

        panelGlobale.add(panelCentre, BorderLayout.CENTER);

        window.add(panelGlobale);
        window.setVisible(true);
    }

    public void quitterJeu() {
        System.exit(0);
    }

    public void fermerFenetre() {
        window.dispose();
    }
}
