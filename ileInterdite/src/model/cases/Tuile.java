package model.cases;

import model.ObjetIdentifie;

/**
 *
 * @author IUT2-Dept Info
 */
public class Tuile extends ObjetIdentifie {
    private int numero;
    private String nomTuile;
    private int l;
    private int c;
    

    public Tuile(int numero, String nomTuile, int l, int c) {
        this.numero = numero;
        this.nomTuile = nomTuile;
        this.l= l;
        this.c=c;
    }
    
    
     public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomTuile() {
        return nomTuile;
    }

    public void setNomTuile(String nomTuile) {
        this.nomTuile= nomTuile;
    }

    public int getL() {
        return l;
    }

    public void setL(int l) {
        this.l = l;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
    
    
    
    
}
