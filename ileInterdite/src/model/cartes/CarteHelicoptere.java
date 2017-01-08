package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteHelicoptere extends CarteTirage {
    
    public CarteHelicoptere(int idCarte){
        super(idCarte, "Hélicoptere");
    }
    
    @Override
    public boolean estHelico(){
        return true;
    }
}
