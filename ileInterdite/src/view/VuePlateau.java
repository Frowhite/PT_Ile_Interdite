package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
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
    private int dernierCarteAppuye;
    private int dernierJoueurAppuye;
    /////////////////////////////////////////////////
    
    private VueGrille vueGrille;
    private ArrayList<VueAventurier> aventurier = new ArrayList<>();

    private JFrame window;
    private JPanel panelGlobale, panelGauche, panelDroite;
    private GridLayout gl = new GridLayout(2, 1);

    public VuePlateau(int nbJoueur) {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        

        panelGlobale = new JPanel(new BorderLayout());
        //***création de la grille de jeu***
        vueGrille = new VueGrille(this);
        panelGlobale.add(vueGrille, BorderLayout.CENTER);

        //***création des aventuriers de 2 à 4***
        panelGauche = new JPanel(gl);
        panelDroite = new JPanel(gl);
        int idAventurier=25;
        for (int i = 0; i < nbJoueur; i++) {
            VueAventurier a = new VueAventurier(idAventurier, this);
            aventurier.add(a);
            if ((idAventurier-24)%2==0) {
                panelDroite.add(a);
            }else{
                panelGauche.add(a);
            }
            idAventurier++;
        }
        
        panelGlobale.add(panelGauche, BorderLayout.WEST);
        panelGlobale.add(panelDroite, BorderLayout.EAST);

        window.setContentPane(panelGlobale);
        //mettre en plein écran
        //window.setAlwaysOnTop(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    /*public VueAventurier getAventurier(int idJoueur) {
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
    }*/

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

    public void choisirCarte(int idCarte) {
        dernierCarteAppuye = idCarte;
        setChanged();
        notifyObservers(Commandes.CHOISIR_CARTE);
        clearChanged();
    }

    void choisirJoueurDonnerCarte(int idAventurier) {
        dernierJoueurAppuye=idAventurier;
        setChanged();
        notifyObservers(Commandes.CHOISIR_JOUEUR);
        clearChanged();
    }    

    public void infoAventurier(int numAventurier) {
        dernierBouttonInfoAppuye=numAventurier;
        setChanged();
        notifyObservers(Commandes.INFO);
        clearChanged();
    }
    
    public void setVisible(boolean b){
        this.setVisible(b);
    }
    
    
/////////////////GETTEURS&SETTEURS//////////////////   
    
    public int getDerniereTuileAppuye() {
        return derniereTuileAppuye;
    }

    public int getDernierBouttonInfoAppuye() {
        return dernierBouttonInfoAppuye;
    }

    public int getDernierCarteAppuye() {
        return dernierCarteAppuye;
    }

    public int getDernierJoueurAppuye() {
        return dernierJoueurAppuye;
    }

    public ArrayList<VueAventurier> getAventurier() {
        return aventurier;
    }
    
    
    
}
