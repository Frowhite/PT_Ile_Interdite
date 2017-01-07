package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public abstract class CarteTirage extends Carte {
    
    public CarteTirage(String nom){
        super(nom);
    }
    
    public boolean estTresor(){
        return false;
    }
}
