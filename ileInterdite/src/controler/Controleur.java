package controler;

import java.util.Observable;
import java.util.Observer;
import view.VueInscription;
import view.VueInterface;
import view.VuePlateau;

/**
 *
 * @author IUT2-Dept Info
 */

public class Controleur implements Observer {
    private VueInterface vueInterface;
    private VueInscription vueInscription;
    private VuePlateau vuePlateau;
    public Controleur() {
        vueInterface= new VueInterface();
        vueInscription= new VueInscription();
        vuePlateau = new VuePlateau();
    }

    
    
    

    @Override
    public void update(Observable o, Object arg) {
    }

}
