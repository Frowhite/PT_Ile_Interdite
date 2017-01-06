package view;

import java.util.Observable;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {
    private VueGrille vueGrille = new VueGrille();;
    
    private JFrame window;
    private JPanel panelGlobale;

    public VuePlateau() {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 800);
        window.setLocation(0, 0);
        window.add(vueGrille);

        window.setVisible(true);
    }
}
