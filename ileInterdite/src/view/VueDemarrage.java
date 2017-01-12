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
import javax.swing.JOptionPane;
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
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();

    public VueDemarrage(int nbJoueur) {
        window = new JFrame();
        window.setSize(340, 370);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        window.setLocation(dim.width * 41 / 100, dim.height * 21 / 100);
        panelGlobale = new JPanel(new BorderLayout());
        //***haut***
        //met l'image du qui affiche le titre
        titre = new JLabel(new ImageIcon(getClass().getResource("/images/titre.png")));
        panelGlobale.add(titre, BorderLayout.NORTH);
        //***panel centre***
        gl.setVgap(5);
        panelCentre = new JPanel(gl);
        panelCentre.add(new JLabel(""));//ajoute un espace

        //boutton commencer partie
        bCommencerPartie = new JButton("Commencer partie");
        bCommencerPartie.setFont(font);
        bCommencerPartie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //affiche un message d'erreur si il n'y a pas minimum 2 joueurs
                if (nbJoueur < 2) {
                    JOptionPane.showMessageDialog(window, "Il n'y a pas assez de joueurs (de 2 à 4 joueurs)", "Attention!!", JOptionPane.ERROR_MESSAGE);
                } else {
                    setChanged();
                    notifyObservers(Commandes.COMMENCER_PARTIE);
                    clearChanged();
                }
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

    //quitte le logicel
    public void quitterJeu() {
        System.exit(0);
    }

    //ferme la fenêtre
    public void fermerFenetre() {
        window.dispose();
    }
}
