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
    private ArrayList<Carte> main;
    private Tuile positionCourante;
    private Controleur controleur;

    public Aventurier(String nom, Pion capacite, Tuile positionCourante, Controleur controleur) {
        super();
        this.nom = nom;
        this.capacite = capacite;
        main = new ArrayList();
        this.positionCourante = positionCourante;
        this.controleur = controleur;
    }
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////GUETTEURS&SETTEURS///////////////////////
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

    public ArrayList<Carte> getMain() {
        return main;
    }

    public void setMain(ArrayList<Carte> main) {
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
    
     
    
}
