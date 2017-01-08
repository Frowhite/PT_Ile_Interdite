package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cases.Tuile;
import util.Utils;
import util.Utils.EtatTuile;

public class VueGrille extends JPanel {
    
    private HashMap<Integer,VueTuile> tuile = new HashMap<>();
    
    private VuePlateau vuePlateau;
    private Image img;
    private JPanel panelGlobale;
    private GridBagConstraints gbc = new GridBagConstraints();
    
    public VueGrille(Image img, VuePlateau vuePlateau) {
        this.vuePlateau=vuePlateau;
        this.img = img;
        this.setPreferredSize(new Dimension(600, 600));
        
        panelGlobale = new JPanel();
        panelGlobale.setLayout(new GridBagLayout());
        
        this.add(panelGlobale);
    }
    
    public void initialiserPlateau(Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile(this);
            tuile.put(tuiles[i].getId()-1, t);
            t.assecheeInondeeOuCouleeTuile(tuiles[i].getId()-1, EtatTuile.ASSECHEE);// il calasse du plus petit au plus grand
        }
        affichePlateau(panelGlobale, tuiles);
    }
    
    //crée le plateau
    public void affichePlateau(JPanel panel, Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            gbc.gridx = tuiles[i].getColonnes();
            gbc.gridy = tuiles[i].getLigne();
            panel.add(tuile.get(tuiles[i].getId()-1), gbc);
        }
        panel.setOpaque(false);
    }
    
    public void etatTuile(int numTuile, EtatTuile etatTuile) {
        tuile.get(numTuile).assecheeInondeeOuCouleeTuile(numTuile, etatTuile);
    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, 0, 0, null);
    }

    public VuePlateau getVuePlateau() {
        return vuePlateau;
    }
    
    
}