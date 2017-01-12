package model.cartes;

import util.Utils.Tresor;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteTresor extends CarteTirage {

    public CarteTresor(int idCarte, String nom, Tresor tresor) {
        super(idCarte, nom);
        setTresor(tresor);
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////GETTERS&SETTERS//////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public Tresor getTresor() {
        return super.getTresor();
    }

    @Override
    public void setTresor(Tresor tresor) {
        super.setTresor(tresor);
    }

    @Override
    public boolean estTresor() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
