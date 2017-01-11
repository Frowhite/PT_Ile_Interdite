package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteSacsDeSable extends CarteTirage {

    public CarteSacsDeSable(int idCarte) {
        super(idCarte, "Sac de Sable");
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////IDENTIFICATION SACDESABLE//////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean estSac() {
        return true;
    }
}
