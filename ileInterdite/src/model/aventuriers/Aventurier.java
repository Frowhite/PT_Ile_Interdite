package model.aventuriers;

import java.util.*;
import model.cartes.*;
import model.*;
import model.cases.*;
import util.Utils.*;
import controler.Controleur;

/**
 *
 * @author IUT2-Dept Info
 */
public class Aventurier extends ObjetIdentifie {

    private String nom;
    private Pion capacite;
    private ArrayList<CarteTirage> main;
    private ArrayList<Tresor> tresors;
    private ArrayList<Tuile> tuilesPossibles;
    private ArrayList<Tuile> tuilesPossibleAssechement;
    private Tuile positionCourante;
    private Controleur controleur;

    public Aventurier(String nom, Pion capacite, Tuile positionCourante, Controleur controleur) {
        super();
        this.nom = nom;
        this.capacite = capacite;
        main = new ArrayList();
        tresors = new ArrayList();
        tuilesPossibles = new ArrayList();
        tuilesPossibleAssechement = new ArrayList();
        this.positionCourante = positionCourante;
        this.controleur = controleur;

    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////METHODES AJOUT&SUPPRESSION///////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void addTuilesPossibles(Tuile t) {                                    // Ajoute le déplacement possible de tuile adjacentes
        getTuilesPossibles().add(t);
    }

    public void remTuilesPossibles(Tuile t) {                                    // Supprime le déplacement possible
        getTuilesPossibles().remove(t);
    }

    public void addTresor(Tresor t) {                                            // Ajoute un trésor à l'aventurier
        getTresors().add(t);
    }

    public void addCarteMain(Carte c) {                                          // Ajoute à la main du joueur un carte tirage
        getMain().add((CarteTirage) c);
    }

    public void removeCarteMain(Carte c) {                                       // Enlève à la main du joueur un carte tirage
        getMain().remove(c);
    }

    public void addTuilesPossiblesAssechement(Tuile t) {                         // Ajoute l'assèchement possible de tuile adjacente
        getTuilesPossibleAssechement().add(t);
    }

    public void remTuilesPossiblesAssechement(Tuile t) {                         // Supprime l'assèchement possible
        getTuilesPossibleAssechement().remove(t);
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////GETTERS&SETTERS//////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Pion getCapacite() {
        return capacite;
    }

    public void setCapacite(Pion capacite) {
        this.capacite = capacite;
    }

    public ArrayList<CarteTirage> getMain() {
        return main;
    }

    public void setMain(ArrayList<CarteTirage> main) {
        this.main = main;
    }

    public Tuile getPositionCourante() {
        return positionCourante;
    }

    public void setPositionCourante(Tuile positionCourante) {
        this.positionCourante = positionCourante;
    }

    public Controleur getControleur() {
        return controleur;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public ArrayList<Tresor> getTresors() {
        return tresors;
    }

    public void setTresors(ArrayList<Tresor> tresors) {
        this.tresors = tresors;
    }

    public ArrayList<Tuile> getTuilesPossibles() {
        return tuilesPossibles;
    }

    public void setTuilesPossibles(ArrayList<Tuile> tuilesPossibles) {
        this.tuilesPossibles = tuilesPossibles;
    }

    public ArrayList<Tuile> getTuilesPossibleAssechement() {
        return tuilesPossibleAssechement;
    }

    public void setTuilesPossibleAssechement(ArrayList<Tuile> tuilesPossibleAssechement) {
        this.tuilesPossibleAssechement = tuilesPossibleAssechement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
