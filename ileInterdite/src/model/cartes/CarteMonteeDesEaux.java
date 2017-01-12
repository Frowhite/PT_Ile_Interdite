package model.cartes;

/**
 *
 * @author IUT2-Dept Info
 */
public class CarteMonteeDesEaux extends CarteTirage {

    public CarteMonteeDesEaux(int idCarte) {
        super(idCarte, "Montee Des Eaux");
    }

    ////////////////////////////////////////////////////////////////////////////
    /////////////////////////////IDENTIFICATION MONTEE//////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean estMontee() {
        return true;
    }
}
