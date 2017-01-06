package model.cases;

import java.util.*;
import model.ObjetIdentifie;
import model.aventuriers.Aventurier;
import util.Utils.*;

/**
 *
 * @author IUT2-Dept Info
 */
public class Tuile extends ObjetIdentifie {
   
    private String nomTuile;
    private Tresor tresor;
    private EtatTuile etat;
    private ArrayList<Aventurier> aventuriers;

    public Tuile( String nomTuile,Tresor tresor) {
        super();
     
        this.nomTuile = nomTuile;
        this.tresor = tresor;
        etat = EtatTuile.ASSECHEE;
        aventuriers = new ArrayList();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////GETTEURS&SETTEURS////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    
 

    public String getNomTuile() {
        return nomTuile;
    }

    public void setNomTuile(String nomTuile) {
        this.nomTuile= nomTuile;
    }


    public Tresor getTresor() {
        return tresor;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }

    public EtatTuile getEtat() {
        return etat;
    }

    public void setEtat(EtatTuile etat) {
        this.etat = etat;
    }

    public ArrayList<Aventurier> getAventuriers() {
        return aventuriers;
    }

    public void setAventuriers(ArrayList<Aventurier> aventuriers) {
        this.aventuriers = aventuriers;
    }

    
    
    
    
}
