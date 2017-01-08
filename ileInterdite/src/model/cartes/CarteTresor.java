package model.cartes;

import util.Utils.Tresor;



/**
 *
 * @author IUT2-Dept Info
 */
public class CarteTresor extends CarteTirage {
    
     private Tresor tresor;

    public CarteTresor(int idCarte,String nom,Tresor tresor) {
        super(idCarte,nom);
        this.tresor = tresor;
    }

    public Tresor getTresor() {
        return tresor;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }
    
     @Override
    public boolean estTresor(){
        return true;
    }
}
