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
    private int ligne;
    private int colonnes;

    public Tuile(int id, String nomTuile, Tresor tresor) {
        super(id);
        this.nomTuile = nomTuile;
        this.tresor = tresor;
        etat = EtatTuile.ASSECHEE;
        aventuriers = new ArrayList();
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////GESTION DE L'AVENTURIER SUR TUILE///////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void addAventuriers(Aventurier a) {                                   // Ajout d'un aventurier sur une tuile
        getAventuriers().add(a);
    }

    public void remAventurier(Aventurier a) {                                    // Enlève un aventurier d'une tuile
        getAventuriers().remove(a);
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////GETTEURS&SETTEURS////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public String getNomTuile() {
        return nomTuile;
    }

    public void setNomTuile(String nomTuile) {
        this.nomTuile = nomTuile;
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

    public int getLigne() {
        return ligne;
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public int getColonnes() {
        return colonnes;
    }

    public void setColonnes(int colonnes) {
        this.colonnes = colonnes;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

}
