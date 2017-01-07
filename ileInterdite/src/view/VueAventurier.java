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

    private ArrayList<Integer> cartePossede;
    private ArrayList<JLabel> emplacementCarte;
    private JLabel donnerNomJoueur, carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;

    private Font font1 = new Font("Arial", 0, 25);
    private Font font2 = new Font("Arial", 0, 18);
    private JPanel panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(3, 3);

    public VueAventurier() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setSize(dim.width / 4, 430);
        cartePossede = new ArrayList<>();
        emplacementCarte = new ArrayList<>();

        //***haut***
        panelGlobale = new JPanel(new BorderLayout());
        donnerNomJoueur = new JLabel();
        donnerNomJoueur.setFont(font1);//taille de la police
        panelGlobale.add(donnerNomJoueur, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel(gl);

        //carte 1
        carte1 = new JLabel();
        creeCarte(carte1);

        //carte 2
        carte2 = new JLabel();
        creeCarte(carte2);

        //carte 3
        carte3 = new JLabel();
        creeCarte(carte3);

        //carte 4
        carte4 = new JLabel();
        creeCarte(carte4);

        //carte 5
        carte5 = new JLabel();
        creeCarte(carte5);

        //carte 6
        carte6 = new JLabel();
        creeCarte(carte6);

        //carte 7
        carte7 = new JLabel();
        creeCarte(carte7);

        //carte 8
        carte8 = new JLabel();
        creeCarte(carte8);

        //carte 9
        carte9 = new JLabel();
        creeCarte(carte9);

        panelGlobale.add(panelCentre, BorderLayout.CENTER);

        this.add(panelGlobale);

    }

    public void creeCarte(JLabel carte) {
        carte.setPreferredSize(new Dimension(110, 130));//taille
        carte.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//bordure
        carte.setFont(font2);//taille de la police
        emplacementCarte.add(carte);

        panelCentre.add(carte);
    }

    public void ajouterCarte(int carteRecue) {
        cartePossede.add(carteRecue);
        switch (carteRecue) {

            case 0:
                emplacementCarte.get(cartePossede.size() - 1).setText("<html>Carte<br>Trésor</html>");
                break;
            case 1:
                emplacementCarte.get(cartePossede.size() - 1).setText("<html>Carte<br>Helicoptere</html>");
                break;
            case 2:
                emplacementCarte.get(cartePossede.size() - 1).setText("<html>Carte sacs<br>de sable</html>");
                break;
            case 3:
                emplacementCarte.get(cartePossede.size() - 1).setText("<html>Carte montee<br>des eaux</html>");
                break;
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
                donnerNomJoueur.setForeground(new Color(153,0,153));
                break;
        }

    }

    /*public void paint(Graphics g) {

        //***centre***
        for (int i = 0; i < cartePossede.size(); i++) {
            ImageIcon img=null;
            switch (cartePossede.get(i)) {
                case 0:
                    img = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/SacsDeSable.png"))
                            .getImage().getScaledInstance(90, 110, Image.SCALE_DEFAULT));
                    break;
                case 1:
                    img = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/SacsDeSable.png"))
                            .getImage().getScaledInstance(90, 110, Image.SCALE_DEFAULT));
                    break;
                case 2:
                    img = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/SacsDeSable.png"))
                            .getImage().getScaledInstance(90, 110, Image.SCALE_DEFAULT));
                    break;
                case 3:
                    img = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/SacsDeSable.png"))
                            .getImage().getScaledInstance(90, 110, Image.SCALE_DEFAULT));
                    break;
            }
            
            g.drawImage(img.getImage(), (i % 3) * 95, (i/3)*115, this);
        }
        this.add(panelGlobale);
        this.setBackground(Color.red);

    }*/
}
