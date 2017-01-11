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

    private boolean competanceActitiveRouge = false;
    private ArrayList<Aventurier> aventuriers = new ArrayList<>();
    private Tuile tuile[];
    private ArrayList<Tresor> tresors = new ArrayList<>();
    private CarteTresor carteTresor;
    private Grille grille;
    private ArrayList<CarteTirage> défausseTirage = new ArrayList<>();
    private ArrayList<CarteTirage> piocheTirage = new ArrayList<>();
    private ArrayList<CarteInondation> defausseInondation = new ArrayList<>();
    private ArrayList<CarteInondation> piocheInondation = new ArrayList<>();
    private ArrayList<Aventurier> joueurPourDonnerCarte = new ArrayList();
    private Integer niveauEau = 1;
    private Aventurier jCourant;
    private int numJoueurQuiJoue = 0;
    private int actionRestante = 3;
    private boolean lActionEstDonnerCarte = false;

    private VueNiveau vueNiveau;
    private VueDemarrage vueDemarrage;
    private VueMontrerJoueur vueMontrerJoueur;
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;
    private VueAction vueAction;
    private VueInfo vueInfo;

    public Controleur() {
        ouvrirFenetreDemarrage();
        creerGrille();
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
                        vueNiveau.fermerFenetre();
                        ouvrirFenetreDemarrage();
                        break;
                    case VALIDER_JOUEURS:
                        vueInscription.fermerFenetre();
                        vueNiveau.fermerFenetre();
                        ajouteJoueur();
                        ouvrirFenetreDemarrage();
                        break;
                }

            }

            if (arg instanceof Integer) {
                setNiveauEau((Integer) arg);
                vueNiveau.setNiveau(niveauEau);
            }
        }
        if (o == vuePlateau) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case CHOISIR_TUILE_DEPLACEMENT:
                        vuePlateau.getVueGrille().deplacePion((aventuriers.get(numJoueurQuiJoue))
                                .getCapacite(), vuePlateau.getDerniereTuileAppuye());
                        avancer(aventuriers.get(numJoueurQuiJoue), vuePlateau.getDerniereTuileAppuye());
                        debutTour();
                        break;
                    case CHOISIR_TUILE_ASSECHEMENT:
                        vuePlateau.getVueGrille().etatTuile(vuePlateau.getDerniereTuileAppuye(), EtatTuile.ASSECHEE);
                        assecher(vuePlateau.getDerniereTuileAppuye());
                        if (!isCompetanceActitiveRouge()) {//le pion rouge va pouvoir assechee 2 fois
                            debutTour();
                        } else {
                            possiblesAssechement(getjCourant());
                        }
                        break;
                    case CHOISIR_CARTE:
                        if (lActionEstDonnerCarte) {
                            setlActionEstDonnerCarte(false);
                            peutDonnerAventurier(jCourant);
                        } else {
                            defausser(jCourant, vuePlateau.getDernierCarteAppuye());
                        }
                        //vuePlateau.getDernierCarteAppuye();
                        break;
                    case CHOISIR_JOUEUR:
                        donnerCarte(jCourant, vuePlateau.getDernierCarteAppuye(), vuePlateau.getDernierJoueurAppuye());
                        //vuePlateau.getDernierJoueurAppuye();
                        break;
                    case INFO:
                        vueInfo = new VueInfo(aventuriers.get(vuePlateau.getDernierBouttonInfoAppuye()).getCapacite());
                        vueInfo.addObserver(this);
                        break;
                }
            }
        }
        if (o == vueAction) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case BOUGER:
                        vueAction.fermerFenetre();
                        possiblesDeplacer(getjCourant());

                        break;
                    case ASSECHER:
                        vueAction.fermerFenetre();
                        possiblesAssechement(getjCourant());

                        break;
                    case DONNER:
                        vueAction.fermerFenetre();
                        peutDonnerCarte(getjCourant());
                        break;
                    case CHOISIR_CARTE:
                        vueAction.fermerFenetre();
                        System.out.println("4");
                        break;
                    case RECUPERER_TRESOR:
                        vueAction.fermerFenetre();
                        System.out.println("5");
                        break;
                    case PASSER_TOUR:
                        vueAction.fermerFenetre();
                        setActionRestante(1);
                        finTour();
                        debutTour();
                        break;
                }
            }
        }
        if (o == vueInfo) {
            if (arg instanceof Commandes) {
                if (arg == Commandes.OK_Info) {
                    vueInfo.fermerFenetre();
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////
//////////////////////////////CREATION & MISE EN PLACE DE LA PARTIE ////////////
////////////////////////////////////////////////////////////////////////////////
    public void initialiserPartie() {
      //  vueNiveau = new VueNiveau(niveauEau);
        setNiveauEau(getVueNiveau().getNiveau());
        initialiserCartesTirages();
        initialiserCartesInondation();
        initialiserPositionJoueur();
        for (int j = 0; j < 2; j++) {
            for (Aventurier jCourant : aventuriers) {
                piocherCarteTresorDepart(jCourant);
            }
        }

        for (int i = 0; i < 6; i++) {
            piocherCarteInondation();
        }
        //le Navigateur a 4 actions
        if (aventuriers.get(numJoueurQuiJoue).getCapacite() == Pion.JAUNE) {
            actionRestante += 1;
        }
        debutTour();
    }

    ////////////////////////////////GRILLE//////////////////////////////////////
    public void creerGrille() {
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

    public ArrayList<CarteTirage> melangerTirage(ArrayList<CarteTirage> listeDepart) {

        ArrayList<CarteTirage> nouvelle = new ArrayList(listeDepart);
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

    public ArrayList<CarteInondation> melangerInondation(ArrayList<CarteInondation> listeDepart) {

        ArrayList<CarteInondation> nouvelle = new ArrayList(listeDepart);
        Collections.shuffle(nouvelle);
        return nouvelle;

    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////LANCEMENT//////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void debutTour() {
        vuePlateau.getVueGrille().allumerJCourant(aventuriers.get(numJoueurQuiJoue).getPositionCourante().getId());
        
        vueAction = new VueAction(aventuriers.get(numJoueurQuiJoue).getNom(), actionRestante, aventuriers.get(numJoueurQuiJoue).getCapacite());
        vueAction.addObserver(this);
        setjCourant(aventuriers.get(numJoueurQuiJoue));
        if (jCourant.getMain().size() > 5) {
            vueAction.apparaitreDisparaitre(false);
            choisirCarteADefausser(jCourant);
        }
    }

    public void finTour() {
        vuePlateau.getVueGrille().eteindrePlateau();
        actionRestante -= 1;
        if (actionRestante == 0) {
            grille.setCompetenceActiveBleu(true);//le Navigateur regagne sa competance à la fin de son tour
            piocherCarteTresor(aventuriers.get(numJoueurQuiJoue));
            piocherCarteTresor(aventuriers.get(numJoueurQuiJoue));
            for (int i = 0; i < getNiveauEau(); i++) {
                piocherCarteInondation();

            }
            if (jCourant.getMain().size() > 5) {
                choisirCarteADefausser(jCourant);
            }

            numJoueurQuiJoue += 1;
            numJoueurQuiJoue %= aventuriers.size();
            actionRestante = 3;
            //le Navigateur a 4 actions
            if (aventuriers.get(numJoueurQuiJoue).getCapacite() == Pion.JAUNE) {
                actionRestante += 1;
            }
            //vuePlateau.getVueGrille().allumerJCourant(jCourant.getPositionCourante().getId());
        }

        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getNomTuile() == "Heliport" && tuile[i].getEtat() == EtatTuile.COULEE) {
                System.out.println("Heliport Coulee");

            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////ACTION POSSIBLE/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////ASSECHEMENT////////////////////////////////
    public void possiblesAssechement(Aventurier av) {
        grille.tuilesPossiblesAssechement(av);
        if (!av.getTuilesPossibleAssechement().isEmpty()) {
            if (av.getCapacite() == Pion.ROUGE && !isCompetanceActitiveRouge()) {//le pion rouge va pouvoir assechee 2 fois (voir update)
                setCompetanceActitiveRouge(true);
            } else {
                setCompetanceActitiveRouge(false);
            }
            for (Tuile t : av.getTuilesPossibleAssechement()) {
                vuePlateau.getVueGrille().idTuileAssechementPossible(t.getId());
            }
        } else if (av.getCapacite() == Pion.ROUGE && isCompetanceActitiveRouge()) {
            setCompetanceActitiveRouge(false);
            finTour();
            debutTour();
        } else {
            debutTour();
        }
    }

    public void assecher(int idTuileAssechee) {
        for (int i = 0; i < tuile.length; i++) {
            if (idTuileAssechee == tuile[i].getId()) {
                tuile[i].setEtat(EtatTuile.ASSECHEE);
            }
        }
        jCourant.getTuilesPossibleAssechement().clear();
        if (!isCompetanceActitiveRouge()) {
            finTour();
        }
    }

    //////////////////////////////////OBTENIR TRESOR////////////////////////////
    public void obtenirTresor(Aventurier av, Tuile tuile) {
        if (av.getPositionCourante().getTresor() != null) {
            if (peutPrendreTresor(av.getMain(), av.getPositionCourante())) {
                av.addTresor(av.getPositionCourante().getTresor());
            }
            Tuile secondeTuile = rechercherTresor(av);
            defausseCarteTresor(av, av.getPositionCourante().getTresor());
            av.getPositionCourante().setTresor(null);
            secondeTuile.setTresor(null);
        }

    }

    public Boolean peutPrendreTresor(ArrayList<CarteTirage> main, Tuile tuile) {

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
    public void initialiserPositionJoueur() {
        for (int i = 0; i < aventuriers.size(); i++) {
            aventuriers.get(i).getPositionCourante().getAventuriers().add(aventuriers.get(i));
        }

    }

    public void possiblesDeplacer(Aventurier av) {
        grille.tuilesPossiblesDeplacement(av);
        if (!av.getTuilesPossibles().isEmpty()) {
            for (Tuile t : av.getTuilesPossibles()) {
                vuePlateau.getVueGrille().idTuileDeplacementPossible(t.getId());
            }
        } else {
            debutTour();
        }
    }

    //suppr la dèrnière position du joueur et met la nouvelle
    public void avancer(Aventurier jCourant, int idNvTuile) {
        if (jCourant.getCapacite() == Pion.BLEU && getGrille().isCompetenceActiveBleu()) {
            competenceBleu(jCourant, idNvTuile);
        }
        jCourant.getPositionCourante().getAventuriers().remove(jCourant);
        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getId() == idNvTuile) {
                jCourant.setPositionCourante(tuile[i]);
                jCourant.getPositionCourante().addAventuriers(jCourant);
            }
        }
        jCourant.getTuilesPossibles().clear();
        finTour();
    }

    public void competenceBleu(Aventurier jCourant, int idNvTuile) {
        int l = jCourant.getPositionCourante().getLigne();
        int c = jCourant.getPositionCourante().getColonnes();
        getGrille().setCompetenceActiveBleu(false);
        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getId() == idNvTuile) {
                if (tuile[i].getColonnes() == c - 1 && tuile[i].getLigne() == l
                        || tuile[i].getColonnes() == c + 1 && tuile[i].getLigne() == l
                        || tuile[i].getColonnes() == c && tuile[i].getLigne() == l - 1
                        || tuile[i].getColonnes() == c && tuile[i].getLigne() == l + 1) {
                    getGrille().setCompetenceActiveBleu(true);
                }
            }
        }

    }

    ////////////////////////////////DONNER CARTE////////////////////////////////
    public void peutDonnerCarte(Aventurier jDonneur) {
        if (!jDonneur.getMain().isEmpty()) {  //-->si il ne lui reste que une carte hélioco ou sable
            if ((jDonneur.getCapacite() == Pion.ORANGE) || (jDonneur.getPositionCourante().getAventuriers().size() > 1)) {
                for (CarteTirage c : jDonneur.getMain()) {
                    if (c.estTresor()) {
                        //Donner a la methode l'ide de la carte c
                        vuePlateau.getAventurier().get(jDonneur.getId() - 25).carteCliquable(c.getId());
                    }
                }
                setlActionEstDonnerCarte(true);
            } else {
                debutTour();
            }
        } else {
            debutTour();
        }
    }

    public void peutDonnerAventurier(Aventurier jDonneur) {
        if (jDonneur.getCapacite() == Pion.ORANGE) {
            setJoueurPourDonnerCarte(aventuriers);
        } else if (!jDonneur.getPositionCourante().getAventuriers().isEmpty()) {
            setJoueurPourDonnerCarte(jDonneur.getPositionCourante().getAventuriers());
        }
        // remJoueurPourDonnerCarte(jDonneur);  --> c'est cette ligne qui fait buger

        for (Aventurier a : getJoueurPourDonnerCarte()) {
            //donner l'id des aventuriers a la methode coresspondante
            if (a != jDonneur) {
                vuePlateau.getAventurier().get(a.getId() - 25).aventurierCliquable();
            }
        }
    }

    public void donnerCarte(Aventurier jDonneur, int idCarte, int idReceveur) {
        Aventurier jReceveur = null;
        CarteTirage carteADonner = null;
        for (Aventurier av : aventuriers) {
            if (av.getId() == idReceveur) {
                jReceveur = av;
            }
        }

        for (CarteTirage c : jDonneur.getMain()) {
            if (c.getId() == idCarte) {
                carteADonner = c;
            }
        }

        jDonneur.removeCarteMain(carteADonner);

        jReceveur.addCarteMain(carteADonner);

        vuePlateau.getAventurier().get(jReceveur.getId() - 25).ajouterCarte(carteADonner.getId());
        finTour();
        debutTour();

    }

    ///////////////////////////////PIOCHER CARTE TIRAGE/////////////////////////
    public void piocherCarteTresorDepart(Aventurier jCourant) {

        CarteTirage cartePioche = getPiocheTirage().get(0);
        remPiocheTirage(cartePioche);
        if (!cartePioche.estMontee()) {
            jCourant.addCarteMain(cartePioche);
            vuePlateau.getAventurier().get(jCourant.getId() - 25).ajouterCarte(cartePioche.getId());
            //vuePlateau.getAventurier(jCourant.getId()).ajouterCarte(cartePioche.getId());
        }
        if (cartePioche.estMontee()) {
            addPiocheTirage(cartePioche);
            setPiocheTirage(melangerTirage(getPiocheTirage())); //Remélanger les cartes
            piocherCarteTresorDepart(jCourant);
        }

    }

    public void piocherCarteTresor(Aventurier av) {
        if (!getPiocheTirage().isEmpty()) {
            CarteTirage cartePioche = getPiocheTirage().get(0);
            remPiocheTirage(cartePioche);
            if (!cartePioche.estMontee()) {
                av.addCarteMain(cartePioche);
                vuePlateau.getAventurier().get(av.getId() - 25).ajouterCarte(cartePioche.getId());
                //vuePlateau.getAventurier(av.getId()).ajouterCarte(cartePioche.getId());
            }
            if (cartePioche.estMontee()) {
                setNiveauEau(getNiveauEau() + 1);
                addDefausseTirage(cartePioche);
                if (!getDefausseInondation().isEmpty()) {
                    setDefausseInondation(melangerInondation(getDefausseInondation()));
                    for (CarteInondation c : piocheInondation) {
                        addDefausseInondation(c);
                    }

                    piocheInondation.clear();

                    for (CarteInondation c : defausseInondation) {
                        addPiocheInondation(c);
                    }
                    getDefausseInondation().clear();

                }
            }
        } else {
            //   System.out.println("Vide Tirage");
            setDéfausseTirage(melangerTirage(getDéfausseTirage()));
            for (CarteTirage t : getDéfausseTirage()) {
                addPiocheTirage(t);
            }
            getDéfausseTirage().clear();
            piocherCarteTresor(av);
        }
    }

    ///////////////////////////////PIOCHER CARTE INONDATION/////////////////////
    public void piocherCarteInondation() {
        if (!getPiocheInondation().isEmpty()) {
            CarteInondation tuileInonde = getPiocheInondation().get(0);

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
        } else {

            setDefausseInondation(melangerInondation(getDefausseInondation()));
            for (CarteInondation c : getDefausseInondation()) {
                addPiocheInondation(c);
            }
            getDefausseInondation().clear();
            piocherCarteInondation();
        }
    }

    ///////////////////////////////DEFAUSSER CARTE//////////////////////////////
    public void choisirCarteADefausser(Aventurier jDefausseur) {
        for (CarteTirage c : jDefausseur.getMain()) {
            //Donner a la methode l'ide de la carte c
            vuePlateau.getAventurier().get(jDefausseur.getId() - 25).carteCliquable(c.getId());
        }
        
    }

    public void defausser(Aventurier jDefausseur, int idCarte) {

        CarteTirage carteADefausser = null;

        for (CarteTirage c : jDefausseur.getMain()) {
            if (c.getId() == idCarte) {
                carteADefausser = c;
            }
        }

        jDefausseur.removeCarteMain(carteADefausser);
        addDefausseTirage(carteADefausser);
        if (jCourant.getMain().size() > 5) {
            choisirCarteADefausser(jCourant);
        }else{
            vueAction.apparaitreDisparaitre(true);
        }
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////Partie IHM /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public void ouvrirPlateauDeJeu() {
        vuePlateau = new VuePlateau(aventuriers.size());
        vuePlateau.addObserver(this);

        for (int i = 0; i < aventuriers.size(); i++) {
            vuePlateau.getAventurier().get(i).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());

            System.out.println("id case = " + aventuriers.get(i).getNom() + ":" + aventuriers.get(i).getPositionCourante().getId());

        }
        vuePlateau.getVueGrille().initialiserPlateau(tuile);//met les tuiles sur le plateau

        //place les pions sur le plateau
        for (int i = 0; i < aventuriers.size(); i++) {
            vuePlateau.getVueGrille().deplacePion(aventuriers.get(i).getCapacite(),
                    aventuriers.get(i).getPositionCourante().getId());
        }
        //vuePlateau.getVueGrille().etatTuile(5, EtatTuile.INONDEE);
        initialiserPartie();

        //vuePlateau.getVueGrille().deplacePion(aventuriers.get(0).getCapacite(), 20);
    }

    public void ouvrirFenetreDemarrage() {
        vueMontrerJoueur = new VueMontrerJoueur();
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
        vueNiveau = new VueNiveau(niveauEau);
        vueInscription = new VueInscription(niveauEau);
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

    public void addJoueurPourDonnerCarte(Aventurier av) {
        getJoueurPourDonnerCarte().add(av);
    }

    public void remJoueurPourDonnerCarte(Aventurier av) {
        getJoueurPourDonnerCarte().remove(av);
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

    public boolean isCompetanceActitiveRouge() {
        return competanceActitiveRouge;
    }

    public void setCompetanceActitiveRouge(boolean competanceActitiveRouge) {
        this.competanceActitiveRouge = competanceActitiveRouge;
    }

    public int getNumJoueurQuiJoue() {
        return numJoueurQuiJoue;
    }

    public void setNumJoueurQuiJoue(int numJoueurQuiJoue) {
        this.numJoueurQuiJoue = numJoueurQuiJoue;
    }

    public int getActionRestante() {
        return actionRestante;
    }

    public void setActionRestante(int actionRestante) {
        this.actionRestante = actionRestante;
    }

    public VueInfo getVueInfo() {
        return vueInfo;
    }

    public void setVueInfo(VueInfo vueInfo) {
        this.vueInfo = vueInfo;
    }

    public ArrayList<Aventurier> getJoueurPourDonnerCarte() {
        return joueurPourDonnerCarte;
    }

    public void setJoueurPourDonnerCarte(ArrayList<Aventurier> joueurPourDonnerCarte) {
        this.joueurPourDonnerCarte = joueurPourDonnerCarte;
    }

    public void setlActionEstDonnerCarte(boolean lActionEstDonnerCarte) {
        this.lActionEstDonnerCarte = lActionEstDonnerCarte;
    }

}
