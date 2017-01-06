package controler;

import java.util.Observable;
import java.util.Observer;
import view.VueInscription;
import view.VuePlateau;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    private VuePlateau vuePlateau;
    private VueInscription vueInscription;
    public Controleur() {
        vuePlateau = new VuePlateau();
        //vueInscription = new VueInscription();
    }


    @Override
    public void update(Observable o, Object arg) {
    }

}
