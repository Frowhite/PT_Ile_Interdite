package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import util.Utils.Commandes;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    ///////////////info a récup//////////////////////
    private int derniereTuileAppuye;
    private int dernierBouttonInfoAppuye;
    /////////////////////////////////////////////////
    private ImageIcon plateau;
    private VueGrille vueGrille;
    private VueAventurier aventurier1, aventurier2, aventurier3, aventurier4;

    private JFrame window;
    private JPanel panelGlobale, panelGauche, panelDroite;
    private GridLayout gl = new GridLayout(2, 1);

    public VuePlateau(int nbJoueur) {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/eau.jpg"))
                .getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_DEFAULT));

        panelGlobale = new JPanel(new BorderLayout());
        //***création de la grille de jeu***
        vueGrille = new VueGrille(plateau.getImage(), this);
        panelGlobale.add(vueGrille, BorderLayout.CENTER);

        //***création des aventuriers de 2 à 4***
        panelGauche = new JPanel(gl);
        panelDroite = new JPanel(gl);
        aventurier1 = new VueAventurier(0,this);
        panelGauche.add(aventurier1);
        aventurier2 = new VueAventurier(1,this);
        panelDroite.add(aventurier2);
        if (nbJoueur > 2) {
            aventurier3 = new VueAventurier(2,this);
            panelGauche.add(aventurier3);
        }
        if (nbJoueur == 4) {
            aventurier4 = new VueAventurier(3,this);
            panelDroite.add(aventurier4);
        }
        panelGlobale.add(panelGauche, BorderLayout.WEST);
        panelGlobale.add(panelDroite, BorderLayout.EAST);

        window.setContentPane(panelGlobale);
        //mettre en plein écran
        //window.setAlwaysOnTop(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    public VueAventurier getAventurier(int idJoueur) {
        VueAventurier aventurier = aventurier1;
        switch (idJoueur) {
            case 25:
                aventurier = aventurier1;
                break;
            case 26:
                aventurier = aventurier2;
                break;
            case 27:
                aventurier = aventurier3;
                break;
            case 28:
                aventurier = aventurier4;
                break;
        }
        return aventurier;
    }

    public VueGrille getVueGrille() {
        return vueGrille;
    }

    public void choisirTuileDeplacement(int idTuile) {
        derniereTuileAppuye = idTuile;
        getVueGrille().remiseAZeroDesTuiles();
        setChanged();
        notifyObservers(Commandes.CHOISIR_TUILE_DEPLACEMENT);
        clearChanged();
    }
    
    public void choisirTuileAssechement(int idTuile) {
        derniereTuileAppuye = idTuile;
        getVueGrille().remiseAZeroDesTuiles();
        setChanged();
        notifyObservers(Commandes.CHOISIR_TUILE_ASSECHEMENT);
        clearChanged();
    }

    public void choisirCarte() {
        setChanged();
        notifyObservers(Commandes.CHOISIR_CARTE);
        clearChanged();
    }

    

    public void infoAventurier(int numAventurier) {
        dernierBouttonInfoAppuye=numAventurier;
        setChanged();
        notifyObservers(Commandes.INFO);
        clearChanged();
    }
    
/////////////////GETTEURS&SETTEURS//////////////////   
    
    public int getDerniereTuileAppuye() {
        return derniereTuileAppuye;
    }

    public int getDernierBouttonInfoAppuye() {
        return dernierBouttonInfoAppuye;
    }
    
    
    
}
