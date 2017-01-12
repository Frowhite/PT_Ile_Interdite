package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
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
        window = new JFrame("Plateau");
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        panelGlobale = new JPanel(new BorderLayout());
        //***création de la grille de jeu***
        vueGrille = new VueGrille(this);
        panelGlobale.add(vueGrille, BorderLayout.CENTER);

        //***création des aventuriers de 2 à 4***
        panelGauche = new JPanel(gl);
        panelDroite = new JPanel(gl);
        int idAventurier = 25;
        for (int i = 0; i < nbJoueur; i++) {
            VueAventurier a = new VueAventurier(idAventurier, this);
            aventurier.add(a);
            //met les joueurs a la droite et la gauche de l'écran
            if ((idAventurier - 24) % 2 == 0) {
                panelDroite.add(a);
            } else {
                panelGauche.add(a);
            }
            idAventurier++;
        }

        panelGlobale.add(panelGauche, BorderLayout.WEST);
        panelGlobale.add(panelDroite, BorderLayout.EAST);

        window.setContentPane(panelGlobale);

        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

/////////////////ENVOIE AU CONTROLEUR//////////////////
    //met les derniers éléments appuyer pour que le controleur puisse les prendres
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
        dernierJoueurAppuye = idAventurier;
        setChanged();
        notifyObservers(Commandes.CHOISIR_JOUEUR);
        clearChanged();
    }

    public void infoAventurier(int numAventurier) {
        dernierBouttonInfoAppuye = numAventurier;
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

    public VueGrille getVueGrille() {
        return vueGrille;
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
