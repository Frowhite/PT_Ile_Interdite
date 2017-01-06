package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueAventurier extends JPanel {

    private String nomJoueur;
    private ArrayList<Integer> cartePossede;
    private JLabel donnerNomJoueur, carte;

    private JPanel panelGlobale;

    public VueAventurier() {
        cartePossede = new ArrayList<>();

        //***haut***
        panelGlobale = new JPanel(new BorderLayout());
        donnerNomJoueur = new JLabel("Information " + nomJoueur);
        panelGlobale.add(donnerNomJoueur, BorderLayout.NORTH);
    }

    public void ajouterCarte(int carte) {
        cartePossede.add(carte);
        this.repaint();
    }

    public void paint(Graphics g) {

        //***centre***
        for (int i = 1; i <= cartePossede.size(); i++) {
            g.drawImage(new ImageIcon(getClass().getResource("/images/titre.png")).getImage(), i * 100, 0, this);
        }
        this.add(panelGlobale);
        this.setBackground(Color.red);

    }

}
