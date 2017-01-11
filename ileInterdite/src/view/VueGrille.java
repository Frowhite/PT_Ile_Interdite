package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import model.cases.Tuile;
import util.Utils;
import util.Utils.EtatTuile;
import util.Utils.Pion;
import util.Utils.Tresor;

public class VueGrille extends JPanel {

    private HashMap<Integer, VueTuile> tuile = new HashMap<>();
    private ArrayList<Tresor> tresors = new ArrayList<>();

    private VuePlateau vuePlateau;
    private JPanel panelGlobale;
    private GridBagConstraints gbc = new GridBagConstraints();
    private ImageIcon plateau, imgTresor;
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();

    public VueGrille(VuePlateau vuePlateau) {
        this.vuePlateau = vuePlateau;
        this.setPreferredSize(new Dimension(600, 600));
        panelGlobale = new JPanel();
        panelGlobale.setLayout(new GridBagLayout());

        this.add(panelGlobale);
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/eau.jpg"))
                .getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
    }

    public void initialiserPlateau(Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile(tuiles[i].getId(), this);
            tuile.put(tuiles[i].getId(), t);
            t.assecheeInondeeOuCouleeTuile(tuiles[i].getId(), EtatTuile.ASSECHEE);// il calasse du plus petit au plus grand
        }
        affichePlateau(panelGlobale, tuiles);
    }

    //crée le plateau
    public void affichePlateau(JPanel panel, Tuile[] tuiles) {
        for (int i = 0; i < tuile.size(); i++) {
            gbc.gridx = tuiles[i].getColonnes();
            gbc.gridy = tuiles[i].getLigne();
            gbc.insets = new Insets(10, 10, 0, 0);
            panel.add(tuile.get(tuiles[i].getId()), gbc);
        }
        panel.setOpaque(false);
    }

    public void etatTuile(int idTuile, EtatTuile etatTuile) {
        tuile.get(idTuile).assecheeInondeeOuCouleeTuile(idTuile, etatTuile);
    }

    public void deplacePion(Pion p, int id) {
        //enlève le pion de la dernière tuile
        for (int i = 0; i < tuile.size(); i++) {
            for (int j = 0; j < tuile.get(i).getPion().size(); j++) {
                if (tuile.get(i).getPion().get(j) == p) {
                    tuile.get(i).enlevePion(tuile.get(i).getPion().get(j));
                }
            }
        }
        //rajoute le pion sur la nouvelle tuile
        tuile.get(id).mettrePion(p);
    }

    public void remiseAZeroDesTuiles() {
        for (int i = 0; i < tuile.size(); i++) {
            tuile.get(i).setPossibliteDeplacement(false);
            tuile.get(i).setPossibliteAssechement(false);
        }
    }

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        g.drawImage(plateau.getImage(), 0, 0, null);
        for (int i = 0; i < tresors.size(); i++) {
            switch (tresors.get(i)) {
                case CALICE:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 10, 10, null);
                    System.out.println("CALICE");
                    break;
                case CRISTAL:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 66, 10, null);
                    System.out.println("CRISTAL");
                    break;
                case PIERRE:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 28, 50, null);
                    System.out.println("PIERRE");
                    break;
                case ZEPHYR:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 84, 50, null);
                    System.out.println("ZEPHYR");
                    break;
            }

        }
    }

    public void dessinerTresor(Tresor t) {

        String s = "/images/tresors/";
        switch (t) {
            case CALICE:
                s += "calice";
                break;
            case CRISTAL:
                s += "cristal";
                break;
            case PIERRE:
                s += "pierre";
                break;
            case ZEPHYR:
                s += "zephyr";
                break;
                
        }
        s += ".png";
        
        imgTresor = new ImageIcon(new ImageIcon(getClass().getResource(s))
                .getImage().getScaledInstance(dim.width / 6 - 200, dim.height / 6 - 30, Image.SCALE_SMOOTH));

    }

    /*public void imageFond() {
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/eau.jpg"))
                .getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
        tresorCalice = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/calice.png"))
                .getImage().getScaledInstance(dim.height / 6 - 90, dim.height / 6 - 30, Image.SCALE_SMOOTH));
        tresorCristal = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/cristal.png"))
                .getImage().getScaledInstance(dim.width / 6 - 200, dim.height / 6 - 30, Image.SCALE_SMOOTH));
        tresorPierre = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/pierre.png"))
                .getImage().getScaledInstance(dim.width / 6 - 210, dim.height / 6 - 30, Image.SCALE_SMOOTH));
        tresorZephyr = new ImageIcon(new ImageIcon(getClass().getResource("/images/tresors/zephyr.png"))
                .getImage().getScaledInstance(dim.width / 6 - 190, dim.height / 6 - 30, Image.SCALE_SMOOTH));
    }*/

    public void donnerTresor(Tresor t) {
        tresors.add(t);
    }

    public void allumerJCourant(int idTuile) {
        tuile.get(idTuile).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(215, 0, 51)));
    }

    public void eteindrePlateau() {
        for (int i = 0; i < tuile.size(); i++) {
            tuile.get(i).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        }

    }

    public void idTuileDeplacementPossible(int idTuile) {
        tuile.get(idTuile).tuilePossibleDeplacement();
    }

    public void idTuileAssechementPossible(int idTuile) {
        tuile.get(idTuile).tuilePossibleAssechement();
    }

}
