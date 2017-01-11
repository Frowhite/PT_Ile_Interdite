package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Pion;

public class VueAventurier extends JPanel {

    private int idAventurier;
    private boolean possibliteJoueurDonnerCarte = false;
    private JButton bInfo;
    private VuePlateau vuePlateau;
    private ArrayList<VueCarte> vueCarte;
    private JLabel donnerNomJoueur;

    private Font font1 = new Font("Arial", 0, 20);
    private Font font2 = new Font("Arial", 0, 25);
    private JPanel panelHaut, panelGlobale, panelCentre;
    private GridLayout gl = new GridLayout(3, 3);

    public VueAventurier(int idAventurir, VuePlateau vuePlateau) {
        this.vuePlateau = vuePlateau;
        this.idAventurier = idAventurir;
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setSize(dim.width / 4, dim.height / 2);
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (possibliteJoueurDonnerCarte) {
                    getVuePlateau().choisirJoueurDonnerCarte(idAventurier);
                }
            }
        });
        vueCarte = new ArrayList<>();

        panelGlobale = new JPanel(new BorderLayout());
        //***panel haut***
        panelHaut = new JPanel(new BorderLayout());
        bInfo = new JButton("?");
        bInfo.setSize(10, 5);
        bInfo.setFont(font2);
        bInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vuePlateau.infoAventurier(idAventurir - 25);
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
        panelHaut.setOpaque(false);
        panelCentre.setOpaque(false);
        panelGlobale.setOpaque(false);
        panelGlobale.add(panelCentre, BorderLayout.CENTER);

        this.add(panelGlobale);
    }

    public void ajouterCarte(int carteRecue) {
        /*
        for (int i = 0; i < vueCarte.size() && vueCarte.get(i).getImg() != null; i++) {
            if (vueCarte.get(i).getImg() == null) {
                vueCarte.get(i).mettreCarte(carteRecue);
                cartePlacee = true;
            }
        }*/
        for (int i = 0; i < vuePlateau.getAventurier().size(); i++) {
            vuePlateau.getAventurier().get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            vuePlateau.getAventurier().get(i).setPossibliteJoueurDonnerCarte(false);
        }

        int i = 0;
        while (i < vueCarte.size() && vueCarte.get(i).getImg() != null) {
            i++;
        }
        vueCarte.get(i).mettreCarte(carteRecue);
    }

    public void enleverCarte(int idCarte) {
        for (int i = 0; i < vueCarte.size() /*&& vueCarte.contains(i)*/; i++) {
            if (vueCarte.get(i).getIdCarte() == idCarte) {
                vueCarte.get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
                vueCarte.remove(vueCarte.get(i));
            }
        }

        int x = 1;
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

        donnerNomJoueur.setText("Carte de " + nomJoueur + " :");

        switch (p) {
            case BLEU:
                this.setBackground(new Color(51, 153, 255));
                break;
            case JAUNE:
                this.setBackground(new Color(255, 255, 102));
                break;
            case ORANGE:
                this.setBackground(new Color(255, 178, 102));
                break;
            case ROUGE:
                this.setBackground(new Color(255, 102, 102));
                break;
            case VERT:
                this.setBackground(new Color(153, 255, 153));
                break;
            case VIOLET:
                this.setBackground(new Color(178, 102, 255));
                break;
        }
    }

    public void aventurierCliquable() {
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
        possibliteJoueurDonnerCarte = true;

    }

    public void carteCliquable(int idCate) {
        for (int i = 0; i < vueCarte.size(); i++) {
            if (vueCarte.get(i).getIdCarte() == idCate) {
                vueCarte.get(i).setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
                vueCarte.get(i).setPossibliteAssechement(true);
            }
        }
    }

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

    public ArrayList<VueCarte> getVueCarte() {
        return vueCarte;
    }

    public void setPossibliteJoueurDonnerCarte(boolean possibliteJoueurDonnerCarte) {
        this.possibliteJoueurDonnerCarte = possibliteJoueurDonnerCarte;
    }

}
