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
    private int idTuileDepartHelico;
    private boolean piocheCarteMonteEau = false;
    private boolean actionDefausser = false;
    private boolean actionUtiliserSac = false;
    private boolean actionCarteHelico = false;
    private boolean actionEndroitDeplaceHelico = false;
    private boolean actionDeplaceHelico = false;
    private boolean actionDonnerCarte = false;

    private VueNiveau vueNiveau;
    private VueDemarrage vueDemarrage;
    private VueMontrerJoueur vueMontrerJoueur;
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;
    private VueAction vueAction;
    private VueInfo vueInfo;
    private VuePerdu vuePerdu;

    public Controleur() {
        ouvrirFenetreDemarrage();           //Creer l'ihm
        creerGrille();                      //Creer le modele avec la grille;
    }

////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////UPDATE///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
    @Override
    public void update(Observable o, Object arg) {
        if (o == vueDemarrage) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case COMMENCER_PARTIE:                          //Quand on commence la partie
                        vueMontrerJoueur.fermerFenetre();           //On quite la fenetre de demarrage
                        vueDemarrage.fermerFenetre();
                        ouvrirPlateauDeJeu();                       //Creer le plateau de jeu
                        break;
                    case INSCRIRE_JOUEUR:                           //Quand on veux inscrire un joueur
                        vueMontrerJoueur.fermerFenetre();           //On ferme la fenetre de demarrage
                        vueDemarrage.fermerFenetre();
                        ouvrirFenetreInscription();                 //Ouvre la fenetre Inscription
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
                    case ANNULER:                                   //Quand on veux annuler l'inscription des joueur
                        vueInscription.fermerFenetre();             //On ferme la fenetre Inscription
                        vueNiveau.fermerFenetre();
                        ouvrirFenetreDemarrage();                   //On ouvre la fenetre Demarrage
                        break;
                    case VALIDER_JOUEURS:                           //Quand on veux valider l'inscription des joueurs
                        vueInscription.fermerFenetre();            //On ferme la fentre d'inscription     
                        vueNiveau.fermerFenetre();
                        ajouteJoueur();                             //On ajoute les joueur a la vueMontreJOueur
                        ouvrirFenetreDemarrage();                   //On ouvre la fenetre de demarrage
                        break;
                }

            }

            if (arg instanceof Integer) {
                setNiveauEau((Integer) arg);                        //Permet de set le niveau d'eau
                vueNiveau.setNiveau(niveauEau);
            }
        }
        if (o == vuePlateau) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case CHOISIR_TUILE_DEPLACEMENT:                                                     //Quand on veux se deplacer
                        if (actionEndroitDeplaceHelico) {                                               //Si on utilise un Hélico
                            setActionEndroitDeplaceHelico(false);
                            setIdTuileDepartHelico(vuePlateau.getDerniereTuileAppuye());                //On recupere l'id de la tuile selectionner par le joueur
                            destinationHelico();                                                        //On choisit la destination de l'helico    
                        } else if (actionDeplaceHelico) {                                               //Quand on a choisit la destination de l'helicoptere
                            setActionDeplaceHelico(false);
                            deplacerHelico(vuePlateau.getDerniereTuileAppuye());                        //On deplace es joueur vers la tuile selectionner par le joueur
                        } else {
                            vuePlateau.getVueGrille().deplacePion((aventuriers.get(numJoueurQuiJoue)) //Un joueur veux se deplacer donc on deplace son pion sur le plateau
                                    .getCapacite(), vuePlateau.getDerniereTuileAppuye());
                            avancer(aventuriers.get(numJoueurQuiJoue), vuePlateau.getDerniereTuileAppuye());    //On deplace le joueur dans le model
                            debutTour();                                                                        //On recommencer un tour de jeu
                        }
                        break;
                    case CHOISIR_TUILE_ASSECHEMENT:                                                                     //On veux assecher une tuile
                        vuePlateau.getVueGrille().etatTuile(vuePlateau.getDerniereTuileAppuye(), EtatTuile.ASSECHEE);   //On change sur l'ihm l'etat de la tuile
                        assecher(vuePlateau.getDerniereTuileAppuye());                                                  //On asseche une tuile dans le model
                        if (!isCompetanceActitiveRouge()) {//le pion rouge va pouvoir assechee 2 fois                   
                            debutTour();
                        } else {
                            possiblesAssechement(getjCourant());
                        }
                        break;
                    case CHOISIR_CARTE:                                                                                 //Quand on doit choisir une carte
                        for (CarteTirage c : jCourant.getMain()) {                                                      //On regarde si la carte selectionner est une carte action special
                            if (c.getId() == vuePlateau.getDernierCarteAppuye()) {
                                if (c.estHelico()) {
                                    setActionCarteHelico(true);
                                } else if (c.estSac()) {
                                    setActionUtiliserSac(true);
                                }
                            }
                        }
                        if (actionDonnerCarte) {                                                                        //Si l'action de depart est de donner une carte alors on selectionne l'aventurier a qui on veut la donner
                            peutDonnerAventurier(jCourant);
                        } else if (actionDefausser) {                                                                   //Si on veux defausser une carte utilise la methode defausse
                            defausser(jCourant, vuePlateau.getDernierCarteAppuye());
                        } else if (actionCarteHelico) {                                                                 //Si c'est un Hélico alors on utilise la methode pour declencher l'helicoptere
                            utiliserHelico(jCourant);
                        } else if (actionUtiliserSac) {                                                                 //Si c'est un Sac alors on asseche une tuile
                            utiliserSac();
                        }
                        //vuePlateau.getDernierCarteAppuye();
                        break;
                    case CHOISIR_JOUEUR:                                                                                //Quand on choisit un joueur c'est seulement pour donner une carte
                        donnerCarte(jCourant, vuePlateau.getDernierCarteAppuye(), vuePlateau.getDernierJoueurAppuye());
                        //vuePlateau.getDernierJoueurAppuye();
                        break;
                    case INFO:                                                                                          //Quand on veux avoir des info sur la classe du joueur    
                        vueInfo = new VueInfo(aventuriers.get(vuePlateau.getDernierBouttonInfoAppuye()).getCapacite());
                        vueInfo.addObserver(this);
                        break;
                }
            }
        }
        if (o == vueAction) {
            if (arg instanceof Commandes) {
                switch ((Commandes) arg) {
                    case BOUGER:                                                //Quand on veux bouger 
                        vueAction.fermerFenetre();                              //On ferme la fenetre action
                        possiblesDeplacer(getjCourant());                       //On enclenche le deplacement

                        break;
                    case ASSECHER:                                              //Quand on veux assecher une tuile
                        vueAction.fermerFenetre();
                        possiblesAssechement(getjCourant());

                        break;
                    case DONNER:                                                //Quand on veux donner une carte
                        vueAction.fermerFenetre();
                        peutDonnerCarte(getjCourant());
                        break;
                    case CHOISIR_CARTE:                                         //Quand on veux utiliser une carte action special
                        vueAction.fermerFenetre();
                        choisirCarteAUtiliser(jCourant);
                        break;
                    case RECUPERER_TRESOR:                                      //Quand on veux recuperer un tresor
                        vueAction.fermerFenetre();
                        obtenirTresor(jCourant);
                        break;
                    case PASSER_TOUR:                                           //Quand on veux passer sont tour
                        vueAction.fermerFenetre();                              //ferme la vueAction
                        setActionRestante(1);                                   //On supprime toute les actions possible
                        finTour();                                              //On finit le tour
                        debutTour();                                            //On en recommence un autre avec un autre joueur
                        break;
                }
            }
        }
        if (o == vueInfo) {
            if (arg instanceof Commandes) {
                if (arg == Commandes.OK_Info) {                                 //Quand on veux fermer une fenetre INFO
                    vueInfo.fermerFenetre();
                }
            }
        }
    }

////////////////////////////////////////////////////////////////////////////////
//////////////////////////////CREATION & MISE EN PLACE DE LA PARTIE ////////////
////////////////////////////////////////////////////////////////////////////////
    public void initialiserPartie() {
        setNiveauEau(getVueNiveau().getNiveau());                               //On initialise le niveau de l'eau avec la difficulter choisit precedement
        initialiserCartesTirages();                                             //On creer les cartes Tirages et les mélanges
        initialiserCartesInondation();                                          //On creer les cartes Inondations et les mélanges
        initialiserPositionJoueur();                                            //On set la position des joueur de depart en fonction de le capacite
        for (int j = 0; j < 2; j++) {                                           //Chaque joueur pioche 2 cartes tirages
            for (Aventurier jCourant : aventuriers) {
                piocherCarteTresorDepart(jCourant);
            }
        }

        for (int i = 0; i < 6; i++) {                                           //On pioche et active les 6 cartes Inondations de début de jeu
            piocherCarteInondation();
        }
        //le Navigateur a 4 actions
        if (aventuriers.get(numJoueurQuiJoue).getCapacite() == Pion.JAUNE) {
            actionRestante += 1;                                                //Si le premier JOueur est un Navigateur alors on lui rajoute 1 action comme specifier dans les regle modifier
        }
        debutTour();                                                            //On commence un tour de jeu
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
        vuePlateau.getVueGrille().allumerJCourant(aventuriers.get(numJoueurQuiJoue).getPositionCourante().getId());                                 //Permet d'eclairer le jCourant pour le voir

        vueAction = new VueAction(aventuriers.get(numJoueurQuiJoue).getNom(), actionRestante, aventuriers.get(numJoueurQuiJoue).getCapacite());     //Affiche la VueAction        
        vueAction.addObserver(this);
        setjCourant(aventuriers.get(numJoueurQuiJoue));                                                                                             //Set Le jCourant

        if (jCourant.getMain().size() > 5) {                                                                                                        //Permet de Défausser si on a trop de carte en main

            vueAction.apparaitreDisparaitre(false);
            choisirCarteADefausser(jCourant);

        }

    }

    public void finTour() {
        vuePlateau.getVueGrille().eteindrePlateau();                                                //Tout les clairage du plateau s'eteignent
        actionRestante -= 1;                                                                        //Soustrait le nb D'action qui rest
        if (actionRestante == 0) {                                                                  //Si il ne peux plus jouer
            grille.setCompetenceActiveBleu(true);//le Navigateur regagne sa competance à la fin de son tour
            piocherCarteTresor(aventuriers.get(numJoueurQuiJoue));                                  //Il pioche 2 carte Tresor
            piocherCarteTresor(aventuriers.get(numJoueurQuiJoue));
            for (int i = 0; i < getNiveauEau(); i++) {                                              //Il pioche le nombre de carte Inondation correspondant au niveau de l'eau actuel
                piocherCarteInondation();
            }

            numJoueurQuiJoue += 1;                                                                  //Change le jCourant
            numJoueurQuiJoue %= aventuriers.size();                                                 
            actionRestante = 3;
            //le Navigateur a 4 actions
            if (aventuriers.get(numJoueurQuiJoue).getCapacite() == Pion.JAUNE) {
                actionRestante += 1;
            }
        }
        if (perdu()) {                                                                          //Verification en cas de défaaite
            //Faire Disparaitre le Plateau de jeu
            vueAction.apparaitreDisparaitre(false);
            vuePerdu = new VuePerdu();

        }
    }

    public boolean perdu() {
        boolean b = false;
        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getId() == 0 && tuile[i].getEtat() == EtatTuile.COULEE //Heliport couler

                    || (!TresorRecuperer(Tresor.PIERRE) && tuile[i].getId() == 18 && tuile[i].getEtat() == EtatTuile.COULEE && tuile[i + 1].getEtat() == EtatTuile.COULEE)
                    || (!TresorRecuperer(Tresor.CRISTAL) && tuile[i].getId() == 1 && tuile[i].getEtat() == EtatTuile.COULEE && tuile[i + 1].getEtat() == EtatTuile.COULEE)
                    || (!TresorRecuperer(Tresor.CALICE) && tuile[i].getId() == 14 && tuile[i].getEtat() == EtatTuile.COULEE && tuile[i + 1].getEtat() == EtatTuile.COULEE)
                    || (!TresorRecuperer(Tresor.ZEPHYR) && tuile[i].getId() == 10 && tuile[i].getEtat() == EtatTuile.COULEE && tuile[i + 1].getEtat() == EtatTuile.COULEE))
                //On a pas recuper le tresor e les 2 tuile on couler)) {
            {
                b = true;
            }
        }

        return b;
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////ACTION POSSIBLE/////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////ASSECHEMENT////////////////////////////////
    public void possiblesAssechement(Aventurier av) {
        grille.tuilesPossiblesAssechement(av);                                          //On recupere les Tuiles que l'on peux asseher dans la grille
        if (!av.getTuilesPossibleAssechement().isEmpty()) {                             //Si on peut assecher
            if (av.getCapacite() == Pion.ROUGE && !isCompetanceActitiveRouge()) {//le pion rouge va pouvoir assechee 2 fois (voir update)
                setCompetanceActitiveRouge(true);
            } else {
                setCompetanceActitiveRouge(false);
            }
            for (Tuile t : av.getTuilesPossibleAssechement()) {
                vuePlateau.getVueGrille().idTuileAssechementPossible(t.getId());         //On recupere toutes les ID des tuiles que l'ont peux assecher
            }
        } else if (av.getCapacite() == Pion.ROUGE && isCompetanceActitiveRouge()) {      //Si la competence est rouge on recommecne le tour pour en choisir une deuxieme
            setCompetanceActitiveRouge(false);
            finTour();
            debutTour();
        } else {
            debutTour();                                                                //Si on ne peut pas assecher ne fait rien et reaffiche la VueAction
        }
    }

    public void assecher(int idTuileAssechee) {
        for (int i = 0; i < tuile.length; i++) {
            if (idTuileAssechee == tuile[i].getId()) {                                  //Si la tuile correspond à l'Id que l'in a donner
                tuile[i].setEtat(EtatTuile.ASSECHEE);                                   //Change l'etat de la tuile dans le domaine
            }
        }
        jCourant.getTuilesPossibleAssechement().clear();                                //clear la collection de tuiles possible a assecher
        if (!isCompetanceActitiveRouge()) {
            finTour();                                                                  //Si ce n'est pas un rouge alors on finit le tour
        }
    }

    //////////////////////////////////OBTENIR TRESOR////////////////////////////
    public void obtenirTresor(Aventurier av) {
        if (av.getPositionCourante().getTresor() != null) {                             //Si il y a un tresor sur la posisiton du jCourant
            if (peutPrendreTresor(av.getMain(), av.getPositionCourante())) {            //Si il a toute les cartes en main pour le recuperer

                av.addTresor(av.getPositionCourante().getTresor());                         //add le tresor dans la liste tresor du jcOurant
                vuePlateau.getVueGrille().donnerTresor(av.getPositionCourante().getTresor());   //La vue recupere la tuile du tresor

                Tuile secondeTuile = rechercherTresor(av);
                defausseCarteTresor(av, av.getPositionCourante().getTresor());              //On defausse nos cartes tresors
                av.getPositionCourante().setTresor(null);                                   //On ne peut plus recuperer le Tresor
                secondeTuile.setTresor(null);                                               //Sur les 2 tuiles
            }

        }

        finTour();
        debutTour();
    }

    public Boolean peutPrendreTresor(ArrayList<CarteTirage> main, Tuile tuile) {

        int calice = 0;
        int cristal = 0;
        int pierre = 0;
        int zephyr = 0;

        for (int i = 0; i < main.size(); i++) {
            if (main.get(i).estTresor()) {
                if ((main.get(i).getTresor() == Tresor.CALICE)) {
                    calice = calice + 1;
                }

                if (main.get(i).getTresor() == Tresor.CRISTAL) {
                    cristal = cristal + 1;
                }

                if (main.get(i).getTresor() == Tresor.PIERRE) {
                    pierre = pierre + 1;
                }

                if (main.get(i).getTresor() == Tresor.ZEPHYR) {
                    zephyr = zephyr + 1;
                }
                //Prmet de compter combrien on a de cartes de chaque pour savoir si on peut recuperer le tresor voulu
            }
        }
        return (tuile.getTresor() == Tresor.CALICE && calice >= 4)
                || (tuile.getTresor() == Tresor.CRISTAL && cristal >= 4)
                || (tuile.getTresor() == Tresor.PIERRE && pierre >= 4)
                || (tuile.getTresor() == Tresor.ZEPHYR && zephyr >= 4);

    }

    public Tuile rechercherTresor(Aventurier av) {                  //Recherche la deuxieme tuile contenant le tresor   
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
        for (int i = 0; i < av.getMain().size(); i++) {                     //On recupere toute les cartes tresor de la main
            if (av.getMain().get(i).estTresor()) {
                cartesTresor.add((CarteTresor) av.getMain().get(i));

            }
        }
        for (int j = 0; j < cartesTresor.size(); j++) {
            if (cartesTresor.get(j).getTresor() == tresor) {               // Si la carte correspond au tresor voulu alors on la defausse

                vuePlateau.getAventurier().get(av.getId() - 25).enleverCarte(cartesTresor.get(j).getId());

                av.removeCarteMain(cartesTresor.get(j));
            }
        }
    }

    ///////////////////////////////////////DEPLACEMENT//////////////////////////
    public void initialiserPositionJoueur() {              //ajoute a la tuile le joueur qui est dessus
        for (int i = 0; i < aventuriers.size(); i++) {
            aventuriers.get(i).getPositionCourante().getAventuriers().add(aventuriers.get(i));
        }

    }

    public void possiblesDeplacer(Aventurier av) {                  //On calcul le deplacement Possible en envoyant a la VuePlateau tous les Id des dep possibles
        grille.tuilesPossiblesDeplacement(av);
        if (!av.getTuilesPossibles().isEmpty()) {
            for (Tuile t : av.getTuilesPossibles()) {
                vuePlateau.getVueGrille().idTuileDeplacementPossible(t.getId());
            }
        } else {                                                    //Si aucun dep possible alos on retourne au debut du tour
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

    //Si le joueur est bleau
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
    //vrai si il y a au moin une carte trésore
    public boolean mainNull(ArrayList<CarteTirage> carteTirage) {
        for (int i = 0; i < carteTirage.size(); i++) {
            if (carteTirage.get(i).estTresor()) {
                return true;
            }
        }
        return false;
    }

    //vrai si il y a au moin 1 aventuriers sur la case et qu'il a moins de 9 cartes
    public boolean possibliteDonnerAAventurier(Aventurier jDonneur) {
        if (jDonneur.getPositionCourante().getAventuriers().size() > 1) {
            for (int i = 0; i < jDonneur.getPositionCourante().getAventuriers().size(); i++) {
                if (jDonneur.getPositionCourante().getAventuriers().get(i).getMain().size() != 9
                        && jDonneur != jDonneur.getPositionCourante().getAventuriers().get(i)) {
                    return true;
                }
            }

        }
        return false;
    }

    public void peutDonnerCarte(Aventurier jDonneur) {
        if (mainNull(jDonneur.getMain())) { //Si la main n'est pas vide
            if ((jDonneur.getCapacite() == Pion.ORANGE) || (possibliteDonnerAAventurier(jDonneur))) {
                for (CarteTirage c : jDonneur.getMain()) {
                    if (c.estTresor()) {
                        //Donner a la methode l'id de de la carte c
                        vuePlateau.getAventurier().get(jDonneur.getId() - 25).carteCliquable(c.getId());
                    }
                }
                setActionDonnerCarte(true);
            } else {
                debutTour();            //Si main vide on retourne au debut du tour
            }
        } else {
            debutTour();
        }
    }

    public void peutDonnerAventurier(Aventurier jDonneur) {
        if (jDonneur.getCapacite() == Pion.ORANGE) {                //Si le joueur est orange alors on donne la liste de tous les joueurs
            setJoueurPourDonnerCarte(aventuriers);
        } else if (!jDonneur.getPositionCourante().getAventuriers().isEmpty()) {    //Sinon seulement ceux sur la meme case
            setJoueurPourDonnerCarte(jDonneur.getPositionCourante().getAventuriers());
        }

        for (Aventurier a : getJoueurPourDonnerCarte()) {
            //donner l'id des aventuriers a la methode coresspondante
            if (a != jDonneur) {
                //ne peut pas donner au joueur qui on 9 cartes
                if (a.getMain().size() != 9) {
                    vuePlateau.getAventurier().get(a.getId() - 25).aventurierCliquable();
                }
            }
        }

    }

    public void donnerCarte(Aventurier jDonneur, int idCarte, int idReceveur) {
        setActionDonnerCarte(false);
        Aventurier jReceveur = null;
        CarteTirage carteADonner = null;
        for (Aventurier av : aventuriers) {         //On recupere l'aventurier avec l'id donner
            if (av.getId() == idReceveur) {
                jReceveur = av;
            }
        }

        for (CarteTirage c : jDonneur.getMain()) {  //On recupere la carte avec l'id donner
            if (c.getId() == idCarte) {
                carteADonner = c;
            }
        }

        jDonneur.removeCarteMain(carteADonner);         //retire la carte de la main du jCourant

        jReceveur.addCarteMain(carteADonner);           //Pour la donner au joueur selectionner

        vuePlateau.getAventurier().get(jReceveur.getId() - 25).ajouterCarte(carteADonner.getId());  //Affichage

        finTour();
        debutTour();

    }

    ///////////////////////////////PIOCHER CARTE TIRAGE/////////////////////////
    public void piocherCarteTresorDepart(Aventurier jCourant) {     //Permet de piocher au debut de la Partie et de remetrre les cartes Montee des eaux dans la pioche

        CarteTirage cartePioche = getPiocheTirage().get(0);
        remPiocheTirage(cartePioche);
        if (!cartePioche.estMontee()) {
            jCourant.addCarteMain(cartePioche);
            vuePlateau.getAventurier().get(jCourant.getId() - 25).ajouterCarte(cartePioche.getId());
        }
        if (cartePioche.estMontee()) {
            addPiocheTirage(cartePioche);
            setPiocheTirage(melangerTirage(getPiocheTirage())); //Remélanger les cartes
            piocherCarteTresorDepart(jCourant);
        }

    }

    public void piocherCarteTresor(Aventurier av) {
        if (!getPiocheTirage().isEmpty()) {//Si la pioche n'est pas vide 
            CarteTirage cartePioche = getPiocheTirage().get(0); //On pioche la premiere carte du tas
            remPiocheTirage(cartePioche);                       //On la suprime de la pioche
            if (!cartePioche.estMontee()) {                     //Si ce n'est pas une carte Montee des eaux
                av.addCarteMain(cartePioche);                   //On add la carte a la main 
                vuePlateau.getAventurier().get(av.getId() - 25).ajouterCarte(cartePioche.getId()); //Affichage
               
            }
            if (cartePioche.estMontee()) { //Si la cartes est une montee des eaux

                if (!piocheCarteMonteEau) {//tout les 2 cartes monte des l'eaux augmente le niveau de l'eau de 1
                    setPiocheCarteMonteEau(true);
                } else {
                    setNiveauEau(getNiveauEau() + 1);
                    setPiocheCarteMonteEau(false);
                }

                addDefausseTirage(cartePioche);             //On la remet tous de suite dans la Défausse
                if (!getDefausseInondation().isEmpty()) {   //Si la pioche Inondation n'est pas vide
                    setDefausseInondation(melangerInondation(getDefausseInondation())); //On melange la défausse Inondation 
                    for (CarteInondation c : piocheInondation) {                        //Et on la remet sur le dessus du tas
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
            setDéfausseTirage(melangerTirage(getDéfausseTirage()));
            for (CarteTirage t : getDéfausseTirage()) {
                addPiocheTirage(t);
            }
            getDéfausseTirage().clear();
            piocherCarteTresor(av);
        }
    }

    ///////////////////////////////PIOCHER CARTE INONDATION/////////////////////
    public void piocherCarteInondation() {                  //Quand on veux piocher une Carte Inondation
        if (!getPiocheInondation().isEmpty()) {             //Si la pioche n'est pas vide
            CarteInondation tuileInonde = getPiocheInondation().get(0); //On pioche la premeire carte du TAs

            remPiocheInondation(tuileInonde);                           //On la supprie de la pioche
            for (int i = 0; i < getTuile().length; i++) {               //On la compare au tuiles
                                                                                    
                if (tuileInonde.getNom().equals(tuile[i].getNomTuile()) && tuile[i].getEtat() == EtatTuile.ASSECHEE) {
                    tuile[i].setEtat(EtatTuile.INONDEE);    //Si elle etait assecher alors elle devient inonder
                    vuePlateau.getVueGrille().etatTuile(tuile[i].getId(), EtatTuile.INONDEE);
                    addDefausseInondation(tuileInonde);     //On la met dans la Defausse

                } else if (tuileInonde.getNom().equals(tuile[i].getNomTuile()) && tuile[i].getEtat() == EtatTuile.INONDEE) {
                    tuile[i].setEtat(EtatTuile.COULEE);     //Si elle etait Inondee alors elle devient Couler
                    vuePlateau.getVueGrille().etatTuile(tuile[i].getId(), EtatTuile.COULEE);
                }
            }
        } else { //Si la pioche est vide on remet la defausse sur la pioche en la melangeant

            setDefausseInondation(melangerInondation(getDefausseInondation()));
            for (CarteInondation c : getDefausseInondation()) {
                addPiocheInondation(c);
            }
            getDefausseInondation().clear();
            piocherCarteInondation();
        }
    }

    ///////////////////////////////DEFAUSSER CARTE//////////////////////////////
    public void choisirCarteADefausser(Aventurier jDefausseur) { //On choisit la carte a Défausser
        for (CarteTirage c : jDefausseur.getMain()) {
            //Donner a la methode l'ide de la carte c
            setActionDefausser(true);
            vuePlateau.getAventurier().get(jDefausseur.getId() - 25).carteCliquable(c.getId());
        }

    }

    public void defausser(Aventurier jDefausseur, int idCarte) {
        setActionDefausser(false);              //On recupere l'Id de la carte a defausser
        CarteTirage carteADefausser = null;

        for (CarteTirage c : jDefausseur.getMain()) {   //On recupere la carte a partir de l'id
            if (c.getId() == idCarte) {
                carteADefausser = c;
            }
        }

        jDefausseur.removeCarteMain(carteADefausser);   //On la suprime de la main du jCOurant
        addDefausseTirage(carteADefausser);             //On l'ajoute a la défausse
        if (jCourant.getMain().size() > 5) {            //Si il a toujours plus de 5 cartes alors on relance la démarche
            choisirCarteADefausser(jCourant);
        } else {
            vueAction.apparaitreDisparaitre(true);
        }
    }

    ///////////////////////////////UTILISATION CARTE SPECIAL////////////////////
    public void choisirCarteAUtiliser(Aventurier jUtilisateur) {//On verifie si on des cartes Actions speciales pour pouvoir Utiliser cette fonctionnaliter
        boolean t = true;
        if (!jUtilisateur.getMain().isEmpty()) {

            ArrayList<CarteTirage> verifSiCarte = new ArrayList();

            for (CarteTirage c : jUtilisateur.getMain()) {
                if (c.estSac() || c.estHelico()) {
                    verifSiCarte.add(c);
                }
            }

            if (!verifSiCarte.isEmpty()) {
                for (CarteTirage c : verifSiCarte) {
                    vuePlateau.getAventurier().get(jUtilisateur.getId() - 25).carteCliquable(c.getId());
                }

            } else {
                debutTour();
            }
        } else {
            debutTour();
        }
    }

    public void utiliserCarte(Aventurier jUtilisateur, int idCarte) {

        for (CarteTirage c : jUtilisateur.getMain()) {
            if (c.getId() == idCarte) {                 //On recupere la carte a partir de l'Id
                if (c.estSac()) {

                    utiliserSac();                      //Si c'est un sac de sable alors on uilise la methode associer
                }
                if (c.estHelico()) {
                    utiliserHelico(jUtilisateur);       //Sinon c'est un hélicoptere
                }
            }
        }
    }

    public void utiliserSac() {
        setActionUtiliserSac(false);
        ArrayList<Tuile> casesInondee = new ArrayList();
        casesInondee = grille.CasesInonder();           //On recupere toutes les cases Inonder du plateau
        if (!casesInondee.isEmpty()) {                  //Si il y a des cases a secher
            actionRestante++;                           //POur faire en sorte de ne pas utiliser d'action
            for (Tuile t : casesInondee) {
                vuePlateau.getVueGrille().idTuileAssechementPossible(t.getId());    //Envoit a la VueGrille l'id de la tuille qui doit etre assecher
            }
        } else {                                        //Si pas de case alors on recommence le tour

            debutTour();
        }
    }

    public void utiliserHelico(Aventurier jUtilisateur) {
        setActionCarteHelico(false);
        if (jUtilisateur.getId() == 0) {            //Condition de Victoire
            ArrayList<Tresor> tresorPossedesParEquipe = new ArrayList();
            for (Aventurier av : aventuriers) {
                if (av.getTresors().contains(Tresor.CALICE)) {
                    tresorPossedesParEquipe.add(Tresor.CALICE);
                }
                if (av.getTresors().contains(Tresor.CRISTAL)) {
                    tresorPossedesParEquipe.add(Tresor.CRISTAL);
                }
                if (av.getTresors().contains(Tresor.PIERRE)) {
                    tresorPossedesParEquipe.add(Tresor.PIERRE);
                }
                if (av.getTresors().contains(Tresor.ZEPHYR)) {
                    tresorPossedesParEquipe.add(Tresor.ZEPHYR);
                }
            }
            if (tresorPossedesParEquipe.contains(Tresor.CALICE)
                    && tresorPossedesParEquipe.contains(Tresor.ZEPHYR)
                    && tresorPossedesParEquipe.contains(Tresor.PIERRE)
                    && tresorPossedesParEquipe.contains(Tresor.CRISTAL) && (jUtilisateur.getPositionCourante().getAventuriers().size() == 3)) {

                //Faire Disparaitre le plateau de jeu
                vueAction.apparaitreDisparaitre(false);
                VueGagner vueGagner = new VueGagner();

            }
        } else {
            for (int i = 0; i < tuile.length; i++) {
                if (!tuile[i].getAventuriers().isEmpty()) {
                    setActionEndroitDeplaceHelico(true);
                    vuePlateau.getVueGrille().idTuileDeplacementPossible(tuile[i].getId());
                }

            }
        }

    }

    public void destinationHelico() {       //Donnes a la VueGrille toutes les destinations possible pour l'Helico
        ArrayList<Tuile> depPossible = new ArrayList<>();
        depPossible = getGrille().tuilesPossiblesDeplacementHelico();
        for (int i = 0; i < depPossible.size(); i++) {
            vuePlateau.getVueGrille().idTuileDeplacementPossible(depPossible.get(i).getId());
        }
        setActionDeplaceHelico(true);
    }

    public void deplacerHelico(int idTuileDestinationHelico) {
        ArrayList<Aventurier> aventurierHelico = new ArrayList();
        //enlever tous les joueurs sur la tuile de départ de l'Hélico
        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getId() == getIdTuileDepartHelico()) {
                aventurierHelico.addAll(tuile[i].getAventuriers());
                tuile[i].getAventuriers().removeAll(aventuriers);
            }
        }
        //mettre tous les joueurs sur la tuile de d'arrivé de l'Hélico
        for (int i = 0; i < tuile.length; i++) {
            if (tuile[i].getId() == idTuileDestinationHelico) {
                for (int j = 0; j < aventurierHelico.size(); j++) {
                    tuile[i].getAventuriers().add(aventurierHelico.get(j));
                    aventurierHelico.get(j).setPositionCourante(tuile[i]);
                    vuePlateau.getVueGrille().deplacePion(aventurierHelico.get(j).getCapacite(), idTuileDestinationHelico);
                }

            }
        }
        finTour();
        debutTour();
    }

    ////////////////////////////////AUTRE///////////////////////////////////////
    public boolean TresorRecuperer(Tresor tresor) {     //Verifie Si les joueur on recuperer le tresor
        int i = 0;
        boolean t = false;
        while (t = false || i < tuile.length) {
            if (tuile[i].getTresor() == tresor) {
                t = true;
            }
            i++;
        }
        return t;
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////Partie IHM /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
    public void ouvrirPlateauDeJeu() {
        vuePlateau = new VuePlateau(aventuriers.size());
        vuePlateau.addObserver(this);

        for (int i = 0; i < aventuriers.size(); i++) {
            vuePlateau.getAventurier().get(i).setNomJoueur(aventuriers.get(i).getNom(), aventuriers.get(i).getCapacite());

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

    public void setActionDonnerCarte(boolean actionDonnerCarte) {
        this.actionDonnerCarte = actionDonnerCarte;
    }

    public boolean isPiocheCarteMonteEau() {
        return piocheCarteMonteEau;
    }

    public void setPiocheCarteMonteEau(boolean piocheCarteMonteEau) {
        this.piocheCarteMonteEau = piocheCarteMonteEau;
    }

    public boolean isActionDefausser() {
        return actionDefausser;
    }

    public void setActionDefausser(boolean ActionDefausser) {
        this.actionDefausser = ActionDefausser;
    }

    public boolean isActionUtiliserSac() {
        return actionUtiliserSac;
    }

    public void setActionUtiliserSac(boolean actionUtiliserSac) {
        this.actionUtiliserSac = actionUtiliserSac;
    }

    public boolean isActionCarteHelico() {
        return actionCarteHelico;
    }

    public void setActionCarteHelico(boolean actionCarteHelico) {
        this.actionCarteHelico = actionCarteHelico;
    }

    public boolean isActionEndroitDeplaceHelico() {
        return actionEndroitDeplaceHelico;
    }

    public void setActionEndroitDeplaceHelico(boolean actionEndroitDeplaceHelico) {
        this.actionEndroitDeplaceHelico = actionEndroitDeplaceHelico;
    }

    public int getIdTuileDepartHelico() {
        return idTuileDepartHelico;
    }

    public void setIdTuileDepartHelico(int idTuileDepartHelico) {
        this.idTuileDepartHelico = idTuileDepartHelico;
    }

    public boolean isActionDeplaceHelico() {
        return actionDeplaceHelico;
    }

    public void setActionDeplaceHelico(boolean actionDeplaceHelico) {
        this.actionDeplaceHelico = actionDeplaceHelico;
    }

}
