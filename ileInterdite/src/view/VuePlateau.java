package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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
    private JPanel panelGlobale,panelGauche,panelDroite;
    private GridLayout gl = new GridLayout(2, 1);

    public VuePlateau(int nbJoueur) {
        window = new JFrame();
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        Toolkit kit =  Toolkit.getDefaultToolkit(); 
        Dimension dim = kit.getScreenSize();
      plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/eau.jpg"))
                .getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_DEFAULT));

        
        panelGlobale=new JPanel(new BorderLayout());
        //***création de la grille de jeu***
        vueGrille = new VueGrille(plateau.getImage());
        panelGlobale.add(vueGrille, BorderLayout.CENTER);
        
        //***création des aventuriers de 2 à 4***
        panelGauche=new JPanel(gl);
        panelDroite=new JPanel(gl);
        aventurier1 = new VueAventurier();
        panelGauche.add(aventurier1);
        aventurier2 = new VueAventurier();
        panelDroite.add(aventurier2);
        if (nbJoueur>2){
        aventurier3 = new VueAventurier();
        panelGauche.add(aventurier3);
        }
        if (nbJoueur==4){
        aventurier4 = new VueAventurier();
        panelDroite.add(aventurier4);
        }
        panelGlobale.add(panelGauche, BorderLayout.WEST);
        panelGlobale.add(panelDroite, BorderLayout.EAST);
        
        window.setContentPane(panelGlobale);
        //mettre en plein écran
        window.setAlwaysOnTop(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setVisible(true);
    }

    public VueAventurier getAventurier1() {
        return aventurier1;
    }

    public VueAventurier getAventurier2() {
        return aventurier2;
    }
    

}
