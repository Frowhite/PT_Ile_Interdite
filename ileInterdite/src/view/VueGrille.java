package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import model.cases.Tuile;
import util.Utils.EtatTuile;
import util.Utils.Pion;

public class VueGrille extends JPanel {

    private HashMap<Integer, VueTuile> tuile = new HashMap<>();

    private VuePlateau vuePlateau;
    private Image img;
    private JPanel panelGlobale;
    private GridBagConstraints gbc = new GridBagConstraints();

    public VueGrille(Image img, VuePlateau vuePlateau) {
        this.vuePlateau = vuePlateau;
        this.img = img;
        this.setPreferredSize(new Dimension(600, 600));

        panelGlobale = new JPanel();
        panelGlobale.setLayout(new GridBagLayout());

        this.add(panelGlobale);
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
    
    public void remiseAZeroDesTuiles(){
        for (int i = 0; i < tuile.size(); i++) {
            tuile.get(i).setPossibliteDeplacement(false);
            tuile.get(i).setPossibliteAssechement(false);
            //tuile.get(i).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));//bordure
        }
    }

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, 0, 0, null);
    }

    public void allumerJCourant(int idTuile){
        tuile.get(idTuile).setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(215,0,51)));
    }
    public void eteindrePlateau(){
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
