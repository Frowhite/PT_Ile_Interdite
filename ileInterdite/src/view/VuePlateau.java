package view;

import java.awt.Image;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author IUT2-Dept Info
 */
public class VuePlateau extends Observable {

    private ImageIcon plateau;
    private VueGrille vueGrille;
    private VueAventurier aventurier1, aventurier2, aventurier3, aventurier4;

    private JFrame window;
    private JPanel panelGlobale;

    public VuePlateau() {
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/plateau.png"))
                .getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));
        vueGrille = new VueGrille(plateau.getImage());

        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
      
        window.setContentPane(vueGrille);

        window.setAlwaysOnTop(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        window.setVisible(true);
    }
}
