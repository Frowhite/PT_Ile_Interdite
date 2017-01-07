package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteHelicoptere extends CarteTirage {
    
    public CarteHelicoptere(){
        super("Hélicoptere");
    }
    
    @Override
    public boolean estHelico(){
        return true;
    }
}
