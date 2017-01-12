package model.cartes;

import model.ObjetIdentifie;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class Carte extends ObjetIdentifie {

    private String nom;

    public Carte(String nom) {
        super();
        this.nom = nom;
    }

    public Carte(int id, String nom) {
        super(id);
        this.nom = nom;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
