package controler;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import model.aventuriers.Aventurier;
import model.cartes.*;
import model.cases.*;
import util.Utils.*;
import view.*;
import util.Utils.EtatTuile.*;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {

    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    private Tuile tuile[];
    private CarteTresor carteTresor;
    private Grille grille;
    private ArrayList<CarteTirage> défausseTirage;
    private ArrayList<CarteTirage> piocheTirage;
    private ArrayList<CarteInondation> defausseInondation;
    private ArrayList<CarteInondation> piocheInondation;
    private int niveauEau;

    private VueNiveau vueNiveau;
    private VueInterface vueInterface;
    private VueMontrerJoueur vueMontrerJoueur;
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;

    public Controleur() {
        vueMontrerJoueur = new VueMontrerJoueur();
        ouvrirFenetreInterface();
        initialiserPartie();
    }

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////UPDATE///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
    @Override
    public void update(Observable o, Object arg) {
        if (o == vueInterface) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case COMMENCER_PARTIE:
                        vueInterface.fermerFenetre();
                        vueMontrerJoueur.fermerFenetre();//pour vueMontrerJoueur:met en setVisible(false)
                        ouvrirPlateauDeJeu();
                        break;
                    case INSCRIRE_JOUEUR:
                        vueInterface.fermerFenetre();
                        vueMontrerJoueur.fermerFenetre();//pour vueMontrerJoueur:met en setVisible(false)
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
//////////////////////////////CREATION & MISE EN PLACE DE LA PARTIE ////////////
////////////////////////////////////////////////////////////////////////////////
    public void initialiserPartie() {
        créerGrille();
        setNiveauEau(getVueNiveau().getNiveau());
        initialiserCartesTirages();
        initialiserCartesInondation();
    }

    ////////////////////////////////GRILLE//////////////////////////////////////
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
///////////////////////////////////////CARTES TIRAGE////////////////////////////

    public void initialiserCartesTirages() {
        addPiocheTirage(new CarteTresor("Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor("Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor("Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor("Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor("Calice", Tresor.CALICE));

        addPiocheTirage(new CarteTresor("Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor("Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor("Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor("Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor("Pierre", Tresor.PIERRE));

        addPiocheTirage(new CarteTresor("Zephir", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor("Zephir", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor("Zephir", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor("Zephir", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor("Zephir", Tresor.ZEPHYR));

        addPiocheTirage(new CarteTresor("Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor("Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor("Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor("Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor("Cristal", Tresor.CRISTAL));

        addPiocheTirage(new CarteSacsDeSable());
        addPiocheTirage(new CarteSacsDeSable());

        addPiocheTirage(new CarteHelicoptere());
        addPiocheTirage(new CarteHelicoptere());
        addPiocheTirage(new CarteHelicoptere());

        addPiocheTirage(new CarteMonteeDesEaux());
        addPiocheTirage(new CarteMonteeDesEaux());

        setPiocheTirage(melangerTirage(getPiocheTirage()));
    }

    public ArrayList melangerTirage(ArrayList<CarteTirage> listeDepart) {

        ArrayList<Carte> nouvelle = new ArrayList(listeDepart);
        Collections.shuffle(nouvelle);
        return nouvelle;
    }

    ///////////////////////////////////CARTES INONDATION////////////////////////
    public void initialiserCartesInondation() {
        for (int i = 0; i < tuile.length; i++) {
            addPiocheInondation(new CarteInondation(tuile[i].getNomTuile()));
        }

        setPiocheInondation(melangerInondation(getPiocheInondation()));
    }

    public ArrayList melangerInondation(ArrayList<CarteInondation> listeDepart) {

        ArrayList<Carte> nouvelle = new ArrayList(listeDepart);
        Collections.shuffle(nouvelle);
        return nouvelle;

    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////ACTION POSSIBLE/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////ASSECHEMENT////////////////////////////////
    public void Assecher(Tuile tuile) {
        tuile.setEtat(EtatTuile.ASSECHEE);
    }

    //////////////////////////////////OBTENIR TRESOR////////////////////////////
    public void obtenirTresor(Aventurier av, Tuile tuile) {
        if (av.getPositionCourante().getTresor() != null) {
            if (PeutPrendreTresor(av.getMain(), av.getPositionCourante())) {
                av.addTresor(av.getPositionCourante().getTresor());
            }
            Tuile secondeTuile = rechercherTresor(av);
            defausseCarteTresor(av, av.getPositionCourante().getTresor());
            av.getPositionCourante().setTresor(null);
            secondeTuile.setTresor(null);
        }

    }

    public Boolean PeutPrendreTresor(ArrayList<CarteTirage> main, Tuile tuile) {

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
        return (tuile.getTresor() == Tresor.CALICE && calice >= 4)
                || (tuile.getTresor() == Tresor.CRISTAL && cristal >= 4)
                || (tuile.getTresor() == Tresor.PIERRE && pierre >= 4)
                || (tuile.getTresor() == Tresor.ZEPHYR && zephyr >= 4);

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

    public void defausseCarteTresor(Aventurier av, Tresor tresor) {
        ArrayList<CarteTresor> cartesTresor = new ArrayList();
        for (int i = 0; i < av.getMain().size(); i++) {
            if (av.getMain().get(i).estTresor()) {
                cartesTresor.add((CarteTresor) av.getMain().get(i));

            }
        }
        for (int j = 0; j < cartesTresor.size(); j++) {
            if (cartesTresor.get(j).getTresor() == tresor) {
                av.removeCarte(cartesTresor.get(j));
            }
        }
    }

    ///////////////////////////////////////DEPLACEMENT//////////////////////////
    ////////////////////////////////DONNER CARTE////////////////////////////////
    ///////////////////////////////PIOCHER CARTE TIRAGE/////////////////////////
    ///////////////////////////////PIOCHER CARTE INONDATION/////////////////////
    public void PiocherCarteInondation() {
        CarteInondation tuileInonde = getPiocheInondation().get(0);
        remPiocheInondation(tuileInonde);
        for (int i = 0; i < getTuile().length; i++) {
            if (tuileInonde.getNom() == tuile[i].getNomTuile() && tuile[i].getEtat() == EtatTuile.ASSECHEE) {
                tuile[i].setEtat(EtatTuile.INONDEE);
                addDefausseInondation(tuileInonde);
            } else if (tuileInonde.getNom() == tuile[i].getNomTuile() && tuile[i].getEtat() == EtatTuile.INONDEE) {
                tuile[i].setEtat(EtatTuile.COULEE);
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////Partie IHM /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public void ouvrirPlateauDeJeu() {
        vuePlateau = new VuePlateau(aventuriers.size());
        vuePlateau.addObserver(this);

        for (int i = 0; i < aventuriers.size(); i++) {
            switch (i) {
                case 0:
                    vuePlateau.getAventurier1().setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 1:
                    vuePlateau.getAventurier2().setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 2:
                    vuePlateau.getAventurier3().setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 3:
                    vuePlateau.getAventurier4().setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
            }

        }
        vuePlateau.getVueGrille().assecheeInondeeOuCouleeTuile(5, EtatTuile.INONDEE);
        vuePlateau.getVueGrille().assecheeInondeeOuCouleeTuile(15, EtatTuile.COULEE);

    }

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
        boolean personneNACePion;
        Pion p = Pion.BLEU;
        Random rand = new Random();

        do {
            personneNACePion = true;
            //donne une couleur
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

            //vérifie que personne n'a déjà ce pion
            for (int i = 0; i < aventuriers.size(); i++) {
                if (aventuriers.get(i).getCapacite() == p) {
                    personneNACePion = false;
                }
            }
        } while (!personneNACePion);
        return p;
    }

////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////ADD & REMOVE//////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
    public void addAventurier(Aventurier av) {
        getAventuriers().add(av);
    }

    public void remAventurier(Aventurier av) {
        getAventuriers().remove(av);
    }

    public void addPiocheTirage(CarteTirage ct) {
        getPiocheTirage().add(ct);
    }

    public void remPiocheTirage(CarteTirage ct) {
        getPiocheTirage().remove(ct);
    }

    public void addPiocheInondation(CarteInondation ci) {
        getPiocheInondation().add(ci);
    }

    public void remPiocheInondation(CarteInondation ci) {
        getPiocheInondation().remove(ci);
    }

    public void addDefausseTirage(CarteTirage ct) {
        getDéfausseTirage().add(ct);
    }

    public void remDefausseTirage(CarteTirage ct) {
        getDéfausseTirage().remove(ct);
    }

    public void addDefausseInondation(CarteInondation ci) {
        getDefausseInondation().add(ci);
    }

    public void remDefausseInondation(CarteInondation ci) {
        getDefausseInondation().remove(ci);
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

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void setAventuriers(ArrayList<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
    }

    public VueInterface getVueInterface() {
        return vueInterface;
    }

    public void setVueInterface(VueInterface vueInterface) {
        this.vueInterface = vueInterface;
    }

    public VueMontrerJoueur getVueMontrerJoueur() {
        return vueMontrerJoueur;
    }

    public void setVueMontrerJoueur(VueMontrerJoueur vueMontrerJoueur) {
        this.vueMontrerJoueur = vueMontrerJoueur;
    }

    public ArrayList<CarteTirage> getPiocheTirage() {
        return piocheTirage;
    }

    public void setPiocheTirage(ArrayList<CarteTirage> piocheTirage) {
        this.piocheTirage = piocheTirage;
    }

    public ArrayList<CarteInondation> getPiocheInondation() {
        return piocheInondation;
    }

    public void setPiocheInondation(ArrayList<CarteInondation> piocheInondation) {
        this.piocheInondation = piocheInondation;
    }

    public int getNiveauEau() {
        return niveauEau;
    }

    public void setNiveauEau(int niveauEau) {
        this.niveauEau = niveauEau;
    }

    public VueNiveau getVueNiveau() {
        return vueNiveau;
    }

    public void setVueNiveau(VueNiveau vueNiveau) {
        this.vueNiveau = vueNiveau;
    }

}
