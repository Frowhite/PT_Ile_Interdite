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

public abstract class Aventurier extends ObjetIdentifie {
    private String nom;
    private Pion capacite;
    private ArrayList<CarteTirage> main;
    private ArrayList<Tresor> tresors;
    private ArrayList<Tuile> tuilesPossibles;
    private Tuile positionCourante;
    private Controleur controleur;
    

    public Aventurier(String nom, Pion capacite, Tuile positionCourante, Controleur controleur) {
        super();
        this.nom = nom;
        this.capacite = capacite;
        main = new ArrayList();
        tresors = new ArrayList();
        tuilesPossibles = new ArrayList();
        this.positionCourante = positionCourante;
        this.controleur = controleur;
        
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////METHODES/////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    public void addTuilesPossibles(Tuile t){
        getTuilesPossibles().add(t);
    }
    
    public void addTresor(Tresor t){
        getTresors().add(t);
    }
    
    public void removeCarte(Carte c){
        getMain().remove(c);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////GETTERS&SETTERS///////////////////////
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
    
    
    
    
    
    
}
