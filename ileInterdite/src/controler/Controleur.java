package controler;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import model.aventuriers.Aventurier;
import model.cartes.Carte;
import model.cartes.CarteInondation;
import model.cartes.CarteTirage;
import model.cartes.CarteTresor;
import model.cases.Grille;
import model.cases.Tuile;
import util.Utils;
import util.Utils.Commandes;
import util.Utils.EtatTuile;
import static util.Utils.EtatTuile.ASSECHEE;
import util.Utils.Pion;
import util.Utils.Tresor;
import view.*;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {

    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    private Tuile tuile[];
    private ArrayList<Tresor> tresors;
    private CarteTresor carteTresor;
    private Grille grille;
    private ArrayList<CarteTirage> défausseTirage;
    private ArrayList<CarteInondation> defausseInondation;

    private VueInterface vueInterface;
    private VueMontrerJoueur vueMontrerJoueur;
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;

    public Controleur() {
        vueMontrerJoueur = new VueMontrerJoueur();
        ouvrirFenetreInterface();
    }

    public Boolean PeutPrendreTresor(ArrayList<Carte> main, Tuile tuile) {

        int calice = 0;
        int cristal = 0;
        int pierre = 0;
        int zephyr = 0;

        for (int i = 0; i < main.size(); i++) {
            if (main.get(i) == carteTresor) {
                if (carteTresor.getTresor() == Tresor.CALICE) {
                    calice = calice + 1;
                }

                if (carteTresor.getTresor() == Tresor.CRISTAL) {
                    cristal = cristal + 1;
                }

                if (carteTresor.getTresor() == Tresor.PIERRE) {
                    pierre = pierre + 1;
                }

                if (carteTresor.getTresor() == Tresor.ZEPHYR) {
                    zephyr = zephyr + 1;
                }

            }
        }
        return (tuile.getTresor() == Tresor.CALICE && calice >= 4) || (tuile.getTresor() == Tresor.CRISTAL && cristal >= 4) || (tuile.getTresor() == Tresor.PIERRE && pierre >= 4) || (tuile.getTresor() == Tresor.ZEPHYR && zephyr >= 4);

    }

    public void Assecher(Tuile tuile) {
        tuile.setEtat(ASSECHEE);
    }

    public void obtenirTresor(Aventurier av, Tuile tuile) {
        if (av.getPositionCourante().getTresor() != null) {
            if (PeutPrendreTresor(av.getMain(), av.getPositionCourante())) {
                av.addTresor(av.getPositionCourante().getTresor());
            }
            Tuile secondeTuile = rechercherTresor(av);
            //    defausseCarteTresor(av,av.getPositionCourante().getTresor());
            av.getPositionCourante().setTresor(null);
            secondeTuile.setTresor(null);
        }

    }

    public Tuile rechercherTresor(Aventurier av) {
        Tuile t = null;
        for (int i = 0; i < 24; i++) {
            if (tuile[i].getTresor() == av.getPositionCourante().getTresor()) {
                if (tuile[i] != av.getPositionCourante()) {
                    t = tuile[i];
                }
            }
        }
        return t;
    }

//    public void defausseCarteTresor(Aventurier av,Tresor tresor){
//        for(int i = 0 ;i<4;i++){
//            av.removeMain();
//        }
//    }
    public void initialiserPartie() {
        créerGrille();
    }

    public void créerGrille() {
        tuile[0] = new Tuile("Heliport", null);
        tuile[1] = new Tuile("La Caverne des Ombres", Tresor.CRISTAL);
        tuile[2] = new Tuile("La Caverne du Brasier", Tresor.CRISTAL);
        tuile[3] = new Tuile("La Foret Pourpre", null);
        tuile[4] = new Tuile("La Porte de Bronze", null);
        tuile[5] = new Tuile("La Porte de Cuivre", null);
        tuile[6] = new Tuile("La Porte de fer", null);
        tuile[7] = new Tuile("La Porte d'Argent", null);
        tuile[8] = new Tuile("La Porte d'Or ", null);
        tuile[9] = new Tuile("La Tour du Guet", null);
        tuile[10] = new Tuile("Le Jardin du Hurlement", Tresor.ZEPHYR);
        tuile[11] = new Tuile("Le Jadin des Murmures", Tresor.ZEPHYR);
        tuile[12] = new Tuile("Le Lagon Perdu", null);
        tuile[13] = new Tuile("Le Marais Brumeux", null);
        tuile[14] = new Tuile("Le Palais de Corail", Tresor.CALICE);
        tuile[15] = new Tuile("Le Palais des Marees", Tresor.CALICE);
        tuile[16] = new Tuile("Le Pont des Abimes", null);
        tuile[17] = new Tuile("Le Rocher Fantome", null);
        tuile[18] = new Tuile("Le Temple de Lune", Tresor.PIERRE);
        tuile[19] = new Tuile("Le Temple du Soleil", Tresor.PIERRE);
        tuile[20] = new Tuile("Le Val du Crépuscule", null);
        tuile[21] = new Tuile("Les Dunes de L'illusion", null);
        tuile[22] = new Tuile("Les Falaises de l'Oubli", null);
        tuile[23] = new Tuile("Observatoire", null);

        melangerTuile(tuile);

        this.grille = new Grille(tuile);

    }

    public void melangerTuile(Tuile[] ar) {

        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {

            int index = rnd.nextInt(i + 1);
            Tuile a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

////////////////////Partie IHM ///////////////////////////
    public void ouvrirFenetreInterface() {
        vueInterface = new VueInterface();
        vueInterface.addObserver(this);
        vueMontrerJoueur.ouvrirFenetre();
    }

    public void ouvrirFenetreInscription() {
        vueInscription = new VueInscription();
        vueInscription.addObserver(this);
        if (aventuriers != null) {
            for (int i = 0; i < aventuriers.size(); i++) {
                switch (i) {
                    case 0:
                        vueInscription.donnerNomJoueur1(aventuriers.get(i).getNom());
                        break;
                    case 1:
                        vueInscription.donnerNomJoueur2(aventuriers.get(i).getNom());
                        break;
                    case 2:
                        vueInscription.donnerNomJoueur3(aventuriers.get(i).getNom());
                        break;
                    case 3:
                        vueInscription.donnerNomJoueur4(aventuriers.get(i).getNom());
                        break;
                }

            }

        }

    }

    public void ajouteJoueur() {
        aventuriers.clear();

        if (!"".equals(vueInscription.nomJoueur1()) && !"Nom joueur".equals(vueInscription.nomJoueur1())) {
            aventuriers.add(new Aventurier(vueInscription.nomJoueur1(), couleurPion(), null, this));
        }
        if (!"".equals(vueInscription.nomJoueur2()) && !"Nom joueur".equals(vueInscription.nomJoueur2())) {
            aventuriers.add(new Aventurier(vueInscription.nomJoueur2(), couleurPion(), null, this));
        }
        if (!"".equals(vueInscription.nomJoueur3()) && !"Nom joueur".equals(vueInscription.nomJoueur3())) {
            aventuriers.add(new Aventurier(vueInscription.nomJoueur3(), couleurPion(), null, this));
        }
        if (!"".equals(vueInscription.nomJoueur4()) && !"Nom joueur".equals(vueInscription.nomJoueur4())) {
            aventuriers.add(new Aventurier(vueInscription.nomJoueur4(), couleurPion(), null, this));
        }
        for (int i = 0; i < aventuriers.size(); i++) {
            vueMontrerJoueur.ecrireNom(i + 1, aventuriers.get(i).getNom());
        }
    }

    public Pion couleurPion() {
        Pion p = Pion.BLEU;
        Random rand = new Random();
        int nombreAleatoire = rand.nextInt(6 - 1 + 1) + 1;//variable aléatoire entre 1 et 6
        switch (nombreAleatoire) {
            case 1:
                p = Pion.BLEU;
                break;
            case 2:
                p = Pion.JAUNE;
                break;
            case 3:
                p = Pion.ORANGE;
                break;
            case 4:
                p = Pion.ROUGE;
                break;
            case 5:
                p = Pion.VERT;
                break;
            case 6:
                p = Pion.VIOLET;
                break;
        }
        return p;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == vueInterface) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case COMMENCER_PARTIE:

                        break;
                    case INSCRIRE_JOUEUR:
                        vueInterface.fermerFenetre();
                        vueMontrerJoueur.fermerFenetre();
                        ouvrirFenetreInscription();
                        break;
                    case QUITTER:
                        vueInterface.quitterJeu();//arrète le programme
                        break;
                }

            }

        }
        if (o == vueInscription) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case ANNULER:
                        vueInscription.fermerFenetre();
                        ouvrirFenetreInterface();
                        break;
                    case VALIDER:
                        vueInscription.fermerFenetre();
                        ajouteJoueur();
                        ouvrirFenetreInterface();
                        break;

                }
            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////
///////////////////////////GETTEURS&SETTEURS////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

    public void setVuePlateau(VuePlateau vuePlateau) {
        this.vuePlateau = vuePlateau;
    }

    public VueInscription getVueInscription() {
        return vueInscription;
    }

    public void setVueInscription(VueInscription vueInscription) {
        this.vueInscription = vueInscription;
    }

    public Tuile[] getTuile() {
        return tuile;
    }

    public void setTuile(Tuile[] tuile) {
        this.tuile = tuile;
    }

    public ArrayList<Tresor> getTresors() {
        return tresors;
    }

    public void setTresors(ArrayList<Tresor> tresors) {
        this.tresors = tresors;
    }

    public CarteTresor getCarteTresor() {
        return carteTresor;
    }

    public void setCarteTresor(CarteTresor carteTresor) {
        this.carteTresor = carteTresor;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public ArrayList<CarteTirage> getDéfausseTirage() {
        return défausseTirage;
    }

    public void setDéfausseTirage(ArrayList<CarteTirage> défausseTirage) {
        this.défausseTirage = défausseTirage;
    }

    public ArrayList<CarteInondation> getDefausseInondation() {
        return defausseInondation;
    }

    public void setDefausseInondation(ArrayList<CarteInondation> defausseInondation) {
        this.defausseInondation = defausseInondation;
    }

}
