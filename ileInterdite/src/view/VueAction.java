/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Commandes;
import util.Utils.Pion;

/**
 *
 * @author asus
 */
public class VueAction extends Observable {

    private JFrame window;
    private JPanel panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(3, 2);
    private JLabel tNbDAction;
    private JButton bDeplace, bAssechee, bDonneCarte, bUtiliseCarte, bPrendreTresor, bPasserTour;

    public VueAction(String nomJCourant, int numAction, Pion pion) {
        window = new JFrame("Action");
        window.setSize(300, 100);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width*64/ 100, dim.height*82/100);
        panelGlobale = new JPanel(new BorderLayout());
        //***Haut***
        //text nb d'action 
        tNbDAction = new JLabel();

        switch (numAction) {
            case 1:
                tNbDAction = new JLabel(nomJCourant + ": Action restante : 1");
                break;
            case 2:
                tNbDAction = new JLabel(nomJCourant + ": Actions restantes : 2");
                break;
            case 3:
                tNbDAction = new JLabel(nomJCourant + ": Actions restantes : 3");
                break;
            case 4:
                tNbDAction = new JLabel(nomJCourant + ": Actions restantes : 4");
                break;
        }
        
        //la fenetre prend la couleur du pion
        switch (pion) {
            case BLEU:
                panelGlobale.setBackground(new Color(51, 153, 255));

                break;
            case JAUNE:
                panelGlobale.setBackground(new Color(255, 255, 102));
                break;
            case ORANGE:
                panelGlobale.setBackground(new Color(255, 178, 102));
                break;
            case ROUGE:
                panelGlobale.setBackground(new Color(255, 102, 102));
                break;
            case VERT:
                panelGlobale.setBackground(new Color(153, 255, 153));
                break;
            case VIOLET:
                panelGlobale.setBackground(new Color(178, 102, 255));
                break;
        }

        tNbDAction.setFont(new Font("Arial", 0, 17));//met une police au texte
        panelGlobale.add(tNbDAction, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel(gl);
        //boutton déplacer
        bDeplace = new JButton("Se déplacer");
        actionListener(bDeplace, Commandes.BOUGER);
        panelCentre.add(bDeplace);
        //boutton assecher
        bAssechee = new JButton("Assecher tuile");
        actionListener(bAssechee, Commandes.ASSECHER);
        panelCentre.add(bAssechee);
        //boutton donner carte
        bDonneCarte = new JButton("Donner carte");
        actionListener(bDonneCarte, Commandes.DONNER);
        panelCentre.add(bDonneCarte);
        //boutton utiliser carte
        bUtiliseCarte = new JButton("Utiliser carte");
        actionListener(bUtiliseCarte, Commandes.CHOISIR_CARTE);
        panelCentre.add(bUtiliseCarte);
        //boutton recup le tresor
        bPrendreTresor = new JButton("Recupérer le trésor");
        actionListener(bPrendreTresor, Commandes.RECUPERER_TRESOR);
        panelCentre.add(bPrendreTresor);
        //boutton passe tour       
        bPasserTour = new JButton("Passer le tour");
        actionListener(bPasserTour, Commandes.PASSER_TOUR);
        panelCentre.add(bPasserTour);

        panelGlobale.add(panelCentre, BorderLayout.CENTER);
        window.add(panelGlobale);
        window.setVisible(true);
    }
    
    //va renvoyer au controleur le bouton appuyer
    public void actionListener(JButton button, Commandes c) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(c);
                clearChanged();
            }
        });
    }

    //ferme la fenetre
    public void fermerFenetre() {
        window.dispose();
    }

    //rend la fenêtre visible et invisible
    public void apparaitreDisparaitre(boolean b) {
        if (b) {
            window.setAlwaysOnTop(true);//met en premier plan
        } else {
            window.setAlwaysOnTop(false);//met en arrière plan (pour des souci de beug graphique)
        }
        window.setVisible(b);

    }
}
