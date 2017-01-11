package model.cartes;

import util.Utils;
import util.Utils.Tresor;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class CarteTirage extends Carte {

    private Tresor tresor;

    public CarteTirage(String nom) {
        super(nom);
        tresor = null;

    }

    public CarteTirage(int idCarte, String nom) {
        super(idCarte, nom);
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////IDENTIFICATION CLASSE///////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public boolean estTresor() {
        return false;
    }

    public boolean estMontee() {
        return false;
    }

    public boolean estHelico() {
        return false;
    }

    public boolean estSac() {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////GETTERS&SETTERS///////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public Tresor getTresor() {
        return tresor;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }

}
