package controler;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import model.aventuriers.Aventurier;
import model.cartes.*;
import model.cases.*;
import util.Utils.*;
import view.*;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {

    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    private Tuile tuile[];
    private ArrayList<Tresor> tresors = new ArrayList<>();
    private CarteTresor carteTresor;
    private Grille grille;
    private ArrayList<CarteTirage> défausseTirage = new ArrayList<>();
    private ArrayList<CarteTirage> piocheTirage = new ArrayList<>();
    private ArrayList<CarteInondation> defausseInondation = new ArrayList<>();
    private ArrayList<CarteInondation> piocheInondation = new ArrayList<>();
    private Integer niveauEau = 0;
    private Aventurier jCourant;

    private VueNiveau vueNiveau;
    private VueDemarrage vueDemarrage;
    private VueMontrerJoueur vueMontrerJoueur;
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;
    private VueAction vueAction;

    public Controleur() {
        ouvrirFenetreInterface();
        créerGrille();
    }

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////UPDATE///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
    @Override
    public void update(Observable o, Object arg) {
        if (o == vueDemarrage) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case COMMENCER_PARTIE:
                        vueMontrerJoueur.fermerFenetre();
                        vueDemarrage.fermerFenetre();
                        ouvrirPlateauDeJeu();
                        break;
                    case INSCRIRE_JOUEUR:
                        vueMontrerJoueur.fermerFenetre();
                        vueDemarrage.fermerFenetre();
                        ouvrirFenetreInscription();
                        break;
                    case QUITTER:
                        vueDemarrage.quitterJeu();//arrète le programme
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
                    case VALIDER_JOUEURS:
                        vueInscription.fermerFenetre();
                        ajouteJoueur();
                        ouvrirFenetreInterface();
                        break;
                }
            }
        }
        if (o == vuePlateau) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case CHOISIR_TUILE:
                        System.out.println("Tuile!!!");
                        break;
                    case CHOISIR_CARTE:
                        System.out.println("Carte");
                        break;
                }
            }
        }
        if (o == vueAction) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case BOUGER:
                        vueAction.fermerFenetre();
                        seDeplacer(getjCourant());
                        break;
                    case ASSECHER:
                        vueAction.fermerFenetre();
                        System.out.println("2");
                        break;
                    case DONNER:
                        vueAction.fermerFenetre();
                        System.out.println("3");
                        break;
                    case CHOISIR_CARTE:
                        vueAction.fermerFenetre();
                        System.out.println("4");
                        break;
                    case RECUPERER_TRESOR:
                        vueAction.fermerFenetre();
                        System.out.println("5");
                        break;
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////
//////////////////////////////CREATION & MISE EN PLACE DE LA PARTIE ////////////
////////////////////////////////////////////////////////////////////////////////
    public void initialiserPartie() {
        vueNiveau = new VueNiveau(niveauEau);
        setNiveauEau(getVueNiveau().getNiveau());
        initialiserCartesTirages();
        initialiserCartesInondation();
        for (int i = 0; i < 6; i++) {
            PiocherCarteInondation();
            System.out.println();
            if (i < 2) {
                for (Aventurier jCourant : aventuriers) {
                    PiocherCarteTresorDepart(jCourant);
                }
            }

            LancementPartie();
        }
    }

    ////////////////////////////////GRILLE//////////////////////////////////////
    public void créerGrille() {
        tuile = new Tuile[24];
        tuile[0] = new Tuile(0, "Heliport", null);
        tuile[1] = new Tuile(1, "La Caverne des Ombres", Tresor.CRISTAL);
        tuile[2] = new Tuile(2, "La Caverne du Brasier", Tresor.CRISTAL);
        tuile[3] = new Tuile(3, "La Foret Pourpre", null);
        tuile[4] = new Tuile(4, "La Porte de Bronze", null);
        tuile[5] = new Tuile(5, "La Porte de Cuivre", null);
        tuile[6] = new Tuile(6, "La Porte de fer", null);
        tuile[7] = new Tuile(7, "La Porte d'Argent", null);
        tuile[8] = new Tuile(8, "La Porte d'Or ", null);
        tuile[9] = new Tuile(9, "La Tour du Guet", null);
        tuile[10] = new Tuile(10, "Le Jardin du Hurlement", Tresor.ZEPHYR);
        tuile[11] = new Tuile(11, "Le Jadin des Murmures", Tresor.ZEPHYR);
        tuile[12] = new Tuile(12, "Le Lagon Perdu", null);
        tuile[13] = new Tuile(13, "Le Marais Brumeux", null);
        tuile[14] = new Tuile(14, "Le Palais de Corail", Tresor.CALICE);
        tuile[15] = new Tuile(15, "Le Palais des Marees", Tresor.CALICE);
        tuile[16] = new Tuile(16, "Le Pont des Abimes", null);
        tuile[17] = new Tuile(17, "Le Rocher Fantome", null);
        tuile[18] = new Tuile(18, "Le Temple de Lune", Tresor.PIERRE);
        tuile[19] = new Tuile(19, "Le Temple du Soleil", Tresor.PIERRE);
        tuile[20] = new Tuile(20, "Le Val du Crépuscule", null);
        tuile[21] = new Tuile(21, "Les Dunes de L'illusion", null);
        tuile[22] = new Tuile(22, "Les Falaises de l'Oubli", null);
        tuile[23] = new Tuile(23, "Observatoire", null);

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
        addPiocheTirage(new CarteTresor(30, "Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor(31, "Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor(32, "Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor(33, "Calice", Tresor.CALICE));
        addPiocheTirage(new CarteTresor(34, "Calice", Tresor.CALICE));

        addPiocheTirage(new CarteTresor(35, "Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor(36, "Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor(37, "Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor(38, "Pierre", Tresor.PIERRE));
        addPiocheTirage(new CarteTresor(39, "Pierre", Tresor.PIERRE));

        addPiocheTirage(new CarteTresor(40, "Zephyr", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor(41, "Zephyr", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor(42, "Zephyr", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor(43, "Zephyr", Tresor.ZEPHYR));
        addPiocheTirage(new CarteTresor(44, "Zephyr", Tresor.ZEPHYR));

        addPiocheTirage(new CarteTresor(45, "Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor(46, "Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor(47, "Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor(48, "Cristal", Tresor.CRISTAL));
        addPiocheTirage(new CarteTresor(49, "Cristal", Tresor.CRISTAL));

        addPiocheTirage(new CarteSacsDeSable(50));
        addPiocheTirage(new CarteSacsDeSable(51));

        addPiocheTirage(new CarteHelicoptere(52));
        addPiocheTirage(new CarteHelicoptere(53));
        addPiocheTirage(new CarteHelicoptere(54));

        addPiocheTirage(new CarteMonteeDesEaux(55));
        addPiocheTirage(new CarteMonteeDesEaux(56));

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
    /////////////////////////////LANCEMENT//////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void LancementPartie() {
        //Boucle Partie Continue?
        for (Aventurier av : aventuriers) {
            
            setjCourant(av);
            
            vueAction = new VueAction();
            vueAction.addObserver(this);
            
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////ACTION POSSIBLE/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////ASSECHEMENT////////////////////////////////
    public void Assecher(Aventurier av,Tuile tuile) {
        grille.TuilesPossibles(av);
        av.addTuilesPossibles(av.getPositionCourante());
        
        
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
                av.removeCarteMain(cartesTresor.get(j));
            }
        }
    }

    ///////////////////////////////////////DEPLACEMENT//////////////////////////
    public void seDeplacer(Aventurier av) {
        grille.TuilesPossibles(av);
        for(Tuile t : av.getTuilesPossibles()){
            vuePlateau.getVueGrille().idTuileDeplacement(t.getId());
        }
        
    }

    ////////////////////////////////DONNER CARTE////////////////////////////////
    public void donnerCarte(Aventurier av){
        
    }
    
    
    
    ///////////////////////////////PIOCHER CARTE TIRAGE/////////////////////////
    public void PiocherCarteTresorDepart(Aventurier jCourant) {

        CarteTirage cartePioche = getPiocheTirage().get(0);
        remPiocheTirage(cartePioche);
        if (!cartePioche.estMontee()) {
            jCourant.addCarteMain(cartePioche);
            vuePlateau.getAventurier(jCourant.getId()).ajouterCarte(cartePioche.getId());
        }
        if (cartePioche.estMontee()) {
            addPiocheTirage(cartePioche);
            setPiocheTirage(melangerTirage(getPiocheTirage())); //Remélanger les cartes
            PiocherCarteTresorDepart(jCourant);
        }

    }

    public void PiocherCarteTresor(Aventurier av) {
        CarteTirage cartePioche = getPiocheTirage().get(0);
        remPiocheTirage(cartePioche);
        if (!cartePioche.estMontee()) {
            av.addCarteMain(cartePioche);
            vuePlateau.getAventurier(av.getId()).ajouterCarte(cartePioche.getId());
        }
        if (cartePioche.estMontee()) {
            setNiveauEau(getNiveauEau() + 1);
            setDefausseInondation(melangerInondation(getDefausseInondation()));
            for (CarteInondation c : piocheInondation) {
                addDefausseInondation(c);
            }
            setPiocheInondation(getDefausseInondation());
            getDefausseInondation().clear();

        }
    }

    ///////////////////////////////PIOCHER CARTE INONDATION/////////////////////
    public void PiocherCarteInondation() {
        CarteInondation tuileInonde = getPiocheInondation().get(0);
        System.out.println(tuileInonde.getNom());
        remPiocheInondation(tuileInonde);
        for (int i = 0; i < getTuile().length; i++) {
            if (tuileInonde.getNom().equals(tuile[i].getNomTuile()) && tuile[i].getEtat() == EtatTuile.ASSECHEE) {
                tuile[i].setEtat(EtatTuile.INONDEE);
                vuePlateau.getVueGrille().etatTuile(tuile[i].getId(), EtatTuile.INONDEE);
                addDefausseInondation(tuileInonde);
            } else if (tuileInonde.getNom().equals(tuile[i].getNomTuile()) && tuile[i].getEtat() == EtatTuile.INONDEE) {
                tuile[i].setEtat(EtatTuile.COULEE);
                vuePlateau.getVueGrille().etatTuile(tuile[i].getId(), EtatTuile.COULEE);
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
                    vuePlateau.getAventurier(25).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 1:
                    vuePlateau.getAventurier(26).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 2:
                    vuePlateau.getAventurier(27).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
                case 3:
                    vuePlateau.getAventurier(28).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());
                    break;
            }
            System.out.println("id case = " + aventuriers.get(i).getNom() + ":" + aventuriers.get(i).getPositionCourante().getId());
        }
        vuePlateau.getVueGrille().initialiserPlateau(tuile);//met les tuiles sur le plateau
        //vuePlateau.getVueGrille().etatTuile(5, EtatTuile.INONDEE);

        //place les pions sur le plateau
        for (int i = 0; i < aventuriers.size(); i++) {
            vuePlateau.getVueGrille().deplacePion(aventuriers.get(i).getCapacite(),
                    aventuriers.get(i).getPositionCourante().getId());
        }

        initialiserPartie();
        //vuePlateau.getVueGrille().deplacePion(aventuriers.get(0).getCapacite(), 20);
    }

    public void ouvrirFenetreInterface() {
        vueMontrerJoueur= new VueMontrerJoueur();
        //écrire les noms dans vueMontrerJoueur
        int x = 0;
        for (int i = 0; i < aventuriers.size(); i++) {
            vueMontrerJoueur.ecrireNom(i + 1, aventuriers.get(i).getNom());
            x++;
        }
        for (int i = 0; i < 4 - x; i++) {
            vueMontrerJoueur.ecrireNom(i + x + 1, "");
        }
        
        vueDemarrage = new VueDemarrage(aventuriers.size());
        vueDemarrage.addObserver(this);
        
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
        Pion p;
        if (!"".equals(vueInscription.nomJoueur1()) && !"Nom joueur".equals(vueInscription.nomJoueur1())) {
            p = couleurPion();
            aventuriers.add(new Aventurier(vueInscription.nomJoueur1(), p, positionJoueurDebut(p), this));
        }
        if (!"".equals(vueInscription.nomJoueur2()) && !"Nom joueur".equals(vueInscription.nomJoueur2())) {
            p = couleurPion();
            aventuriers.add(new Aventurier(vueInscription.nomJoueur2(), p, positionJoueurDebut(p), this));
        }
        if (!"".equals(vueInscription.nomJoueur3()) && !"Nom joueur".equals(vueInscription.nomJoueur3())) {
            p = couleurPion();
            aventuriers.add(new Aventurier(vueInscription.nomJoueur3(), p, positionJoueurDebut(p), this));
        }
        if (!"".equals(vueInscription.nomJoueur4()) && !"Nom joueur".equals(vueInscription.nomJoueur4())) {
            p = couleurPion();
            aventuriers.add(new Aventurier(vueInscription.nomJoueur4(), p, positionJoueurDebut(p), this));
        }

        //donner un id aux aventuriers
        for (int i = 0; i < aventuriers.size(); i++) {
            switch (i) {
                case 0:
                    aventuriers.get(i).setId(25);
                    break;
                case 1:
                    aventuriers.get(i).setId(26);
                    break;
                case 2:
                    aventuriers.get(i).setId(27);
                    break;
                case 3:
                    aventuriers.get(i).setId(28);
                    break;
            }
        }

        

    }

    public Tuile positionJoueurDebut(Pion pion) {
        Tuile t = tuile[0];
        int idTuile = 0;

        //met l'id de la tuile où le joueur commence
        switch (pion) {
            case BLEU:
                idTuile = 0;
                break;
            case JAUNE:
                idTuile = 8;
                break;
            case ORANGE:
                idTuile = 7;
                break;
            case ROUGE:
                idTuile = 4;
                break;
            case VERT:
                idTuile = 5;
                break;
            case VIOLET:
                idTuile = 6;
                break;
        }
        for (int i = 0; i < 24; i++) {
            if (tuile[i].getId() == idTuile) {
                t = tuile[i];
            }
        }
        return t;
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

    public VueDemarrage getVueDemarrage() {
        return vueDemarrage;
    }

    public void setVueDemarrage(VueDemarrage vueDemarrage) {
        this.vueDemarrage = vueDemarrage;
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

    public Integer getNiveauEau() {
        return niveauEau;
    }

    public void setNiveauEau(Integer niveauEau) {
        this.niveauEau = niveauEau;
    }

    public VueNiveau getVueNiveau() {
        return vueNiveau;
    }

    public void setVueNiveau(VueNiveau vueNiveau) {
        this.vueNiveau = vueNiveau;
    }

    public ArrayList<Tresor> getTresors() {
        return tresors;
    }

    public void setTresors(ArrayList<Tresor> tresors) {
        this.tresors = tresors;
    }

    public Aventurier getjCourant() {
        return jCourant;
    }

    public void setjCourant(Aventurier jCourant) {
        this.jCourant = jCourant;
    }

    public VueAction getVueAction() {
        return vueAction;
    }

    public void setVueAction(VueAction vueAction) {
        this.vueAction = vueAction;
    }
}
