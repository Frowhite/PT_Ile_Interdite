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

    public VuePlateau(int nbJoueur) {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.setSize(360, 430);
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/plateau.png"))
                .getImage().getScaledInstance(800, 800, Image.SCALE_DEFAULT));

        //***création des aventuriers de 2 à 4***
        aventurier1 = new VueAventurier();
        window.add(aventurier1);
//        aventurier2 = new VueAventurier();
//        window.add(aventurier2);
//        if (nbJoueur>2){
//        aventurier3 = new VueAventurier();
//        window.add(aventurier3);
//        }
//        if (nbJoueur==4){
//        aventurier4 = new VueAventurier();
//        window.add(aventurier4);
//        }

        //***création de la grille de jeu***
//        vueGrille = new VueGrille(plateau.getImage());
//        window.setContentPane(vueGrille);

        //mettre en plein écran
        //window.setAlwaysOnTop(true);
        //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    public VueAventurier getAventurier1() {
        return aventurier1;
    }

}
