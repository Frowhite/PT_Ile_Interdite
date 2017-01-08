package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import util.Utils.Pion;

public class VueAventurier extends JPanel {

    private ArrayList<VueCarte> vueCarte;
    private JLabel donnerNomJoueur;

    private Font font1 = new Font("Arial", 0, 25);
    private Font font2 = new Font("Arial", 0, 18);
    private JPanel panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(3, 3);

    public VueAventurier() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setSize(dim.width / 4, 430);
        vueCarte = new ArrayList<>();

        //***haut***
        panelGlobale = new JPanel(new BorderLayout());
        donnerNomJoueur = new JLabel();
        donnerNomJoueur.setFont(font1);//taille de la police
        panelGlobale.add(donnerNomJoueur, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel(gl);

        for (int i = 0; i < 9; i++) {
            VueCarte c = new VueCarte();
            panelCentre.add(c);
            vueCarte.add(c);
        }
        panelGlobale.add(panelCentre, BorderLayout.CENTER);

        this.add(panelGlobale);
    }

    public void ajouterCarte(int carteRecue) {
        boolean cartePlacee = false;
        for (int i = 0; i < vueCarte.size() && !cartePlacee; i++) {
            if (vueCarte.get(i).getImg() == null) {
                vueCarte.get(i).mettreCarte(carteRecue);
                cartePlacee = true;
            }
        }
    }

    public void enleverCarte(int numEmplacementCarteEnleve) {
        vueCarte.remove(numEmplacementCarteEnleve-1);        
        int x=1;
        //trie les cartes
        //enlève un emplacement de carte vide
        for (int i = 0; i < vueCarte.size(); i++) {
            if (vueCarte.get(i).getImg() == null) {
                vueCarte.remove(i);
                i--;
                x++;
            }
        }
        
        
        for (int i = 0; i < x; i++) {
            //rajoute un emplacement de carte vide
                VueCarte c = new VueCarte();
                vueCarte.add(c);
        }
        panelCentre.removeAll();
        //redessine les cartes possédé
        for (int i = 0; i < vueCarte.size(); i++) {
            panelCentre.add(vueCarte.get(i));
        }
    }

    public void setNomJoueur(String nomJoueur, Pion p) {

        donnerNomJoueur.setText("Information " + nomJoueur + " :");

        switch (p) {
            case BLEU:
                donnerNomJoueur.setForeground(Color.BLUE);
                break;
            case JAUNE:
                donnerNomJoueur.setForeground(Color.YELLOW);
                break;
            case ORANGE:
                donnerNomJoueur.setForeground(Color.ORANGE);
                break;
            case ROUGE:
                donnerNomJoueur.setForeground(Color.RED);
                break;
            case VERT:
                donnerNomJoueur.setForeground(Color.GREEN);
                break;
            case VIOLET:
                donnerNomJoueur.setForeground(new Color(153, 0, 153));
                break;
        }

    }
}
