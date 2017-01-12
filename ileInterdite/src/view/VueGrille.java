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
        panelGlobale = new JPanel();
        panelGlobale.setLayout(new GridBagLayout());

        this.add(panelGlobale);
        //enregistre l'image de l'eau en font
        plateau = new ImageIcon(new ImageIcon(getClass().getResource("/images/eau.jpg"))
                .getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH));
    }

    public void initialiserPlateau(Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile(tuiles[i].getId(), this);
            tuile.put(tuiles[i].getId(), t);
            t.assecheeInondeeOuCouleeTuile(tuiles[i].getId(), EtatTuile.ASSECHEE);// il calasse du plus petit au plus grand
        }
        affichePlateau(panelGlobale, tuiles);//crée le plateau
    }

    //crée le plateau: utilise un GridBagLayout
    public void affichePlateau(JPanel panel, Tuile[] tuiles) {
        for (int i = 0; i < tuile.size(); i++) {
            gbc.gridx = tuiles[i].getColonnes();
            gbc.gridy = tuiles[i].getLigne();
            gbc.insets = new Insets(10, 10, 0, 0);
            panel.add(tuile.get(tuiles[i].getId()), gbc);
        }
        panel.setOpaque(false);
    }

    //change l'etat d'une tuile (ex : etat assechée)
    public void etatTuile(int idTuile, EtatTuile etatTuile) {
        tuile.get(idTuile).assecheeInondeeOuCouleeTuile(idTuile, etatTuile);
    }

    //deplace le pion sur la nouvelle tuile
    public void deplacePion(Pion p, int idTuile) {
        //enlève le pion de sa dernière tuile
        for (int i = 0; i < tuile.size(); i++) {
            for (int j = 0; j < tuile.get(i).getPion().size(); j++) {
                if (tuile.get(i).getPion().get(j) == p) {
                    tuile.get(i).enlevePion(tuile.get(i).getPion().get(j));
                }
            }
        }
        //rajoute le pion sur la nouvelle tuile
        tuile.get(idTuile).mettrePion(p);
    }

    //remet à zero tout le plateau: on le peut plus cliqué dessus
    public void remiseAZeroDesTuiles() {
        for (int i = 0; i < tuile.size(); i++) {
            tuile.get(i).setPossibliteDeplacement(false);
            tuile.get(i).setPossibliteAssechement(false);
            tuile.get(i).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));//bordure
        }
    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        //met l'image de font : eau
        g.drawImage(plateau.getImage(), 0, 0, null);
        //met l'image des trésores quand les joueurs en possèdent
        for (int i = 0; i < tresors.size(); i++) {
            switch (tresors.get(i)) {
                case CALICE:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 0, 5, null);
                    System.out.println("CALICE");
                    break;
                case CRISTAL:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 90, 5, null);
                    System.out.println("CRISTAL");
                    break;
                case PIERRE:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 180, 5, null);
                    System.out.println("PIERRE");
                    break;
                case ZEPHYR:
                    dessinerTresor(tresors.get(i));
                    g.drawImage(imgTresor.getImage(), 270, 5, null);
                    System.out.println("ZEPHYR");
                    break;
            }

        }
    }

    //selectionne la bonne image du trésor
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

    //ajoute un trésor
    public void donnerTresor(Tresor t) {
        tresors.add(t);
        repaint();//repaint le font (avec le trésore ajouté)
    }

    //met en rouge la case du joueur qui joue
    public void allumerJCourant(int idTuile) {
        tuile.get(idTuile).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(215, 0, 51)));
    }

    //remet tout le plateau dans sa couleur initial
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

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

}
