package model.cartes;

import util.Utils.Tresor;



/**
 *
 * @author IUT2-Dept Info
 */
public class CarteTresor extends CarteTirage {
    
     private Tresor tresor;

    public CarteTresor(Tresor tresor) {
        this.tresor = tresor;
    }

    public Tresor getTresor() {
        return tresor;
    }

    public void setTresor(Tresor tresor) {
        this.tresor = tresor;
    }
    
    
}
