package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Pion;

public class VueAventurier extends JPanel {
    private JButton bInfo;
    private VuePlateau vuePlateau;
    private ArrayList<VueCarte> vueCarte;
    private JLabel donnerNomJoueur;

    private Font font1 = new Font("Arial", 0, 25);
    private Font font2 = new Font("Arial", 0, 18);
    private JPanel panelHaut, panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(3, 3);

    public VueAventurier(int numAventurier, VuePlateau vuePlateau) {
        this.vuePlateau=vuePlateau;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setSize(dim.width / 4, 430);
        vueCarte = new ArrayList<>();
        
        panelGlobale = new JPanel(new BorderLayout());
        //***panel haut***
        panelHaut = new JPanel(new BorderLayout());
        bInfo = new JButton("?");
        bInfo.setSize(10, 5);
        bInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vuePlateau.infoAventurier(numAventurier);
            }
        });
        
        panelHaut.add(bInfo, BorderLayout.WEST);
        
        donnerNomJoueur = new JLabel();
        donnerNomJoueur.setFont(font1);//taille de la police
        panelHaut.add(donnerNomJoueur, BorderLayout.CENTER);
        panelGlobale.add(panelHaut, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel(gl);

        for (int i = 0; i < 9; i++) {
            VueCarte c = new VueCarte(this);
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
        //rajoute un emplacement de carte vide
        for (int i = 0; i < x; i++) {
                VueCarte c = new VueCarte(this);
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
                donnerNomJoueur.setForeground(new Color(51,153,255));
                bInfo.setForeground(new Color(51,153,255));
                break;
            case JAUNE:
                donnerNomJoueur.setForeground(new Color(255,255,102));
                bInfo.setForeground(new Color(255,255,102));
                break;
            case ORANGE:
                donnerNomJoueur.setForeground(new Color(255,178,102));
                bInfo.setForeground(new Color(255,178,102));
                break;
            case ROUGE:
                donnerNomJoueur.setForeground(new Color(255,102,102));
                bInfo.setForeground(new Color(255,102,102));
                break;
            case VERT:
                donnerNomJoueur.setForeground(new Color(153,255, 153));
                bInfo.setForeground(new Color(153,255, 153));
                break;
            case VIOLET:
                donnerNomJoueur.setForeground(new Color(178,102, 255));
                bInfo.setForeground(new Color(178,102, 255));
                break;
        }

    }

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }
    
    
}
