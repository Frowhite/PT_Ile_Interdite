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
    
    private Image img;
    private JPanel panelGlobale;
    private GridBagConstraints gbc = new GridBagConstraints();
    
    public VueGrille(Image img) {
        this.img = img;
        this.setPreferredSize(new Dimension(600, 600));
        
        panelGlobale = new JPanel();
        panelGlobale.setLayout(new GridBagLayout());
        
        this.add(panelGlobale);
    }
    
    public void initialiserPlateau(Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile();
            tuile.put(tuiles[i].getId(), t);
            t.assecheeInondeeOuCouleeTuile(tuiles[i].getId(), EtatTuile.ASSECHEE);// il calasse du plus petit au plus grand
        }
        affichePlateau(panelGlobale, tuiles);
    }
    
    public void affichePlateau(JPanel panel, Tuile[] tuiles) {
        for (int i = 0; i < 24; i++) {
            gbc.gridx = tuiles[i].getColonnes();
            gbc.gridy = tuiles[i].getLigne();
            panel.add(tuile.get(tuiles[i].getId()), gbc);
        }
        panel.setOpaque(false);
    }
    
//    public void etatTuile(int numTuile, EtatTuile etatTuile) {
//        tuile.get(numTuile).assecheeInondeeOuCouleeTuile(numTuile, etatTuile);
//    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, 0, 0, null);
    }
}

/*
int numTuile = 0;
        for (int j = 1; j <= 6; j++) {//parcourir les lignes
            for (int k = 1; k <= 6; k++) {//parcourir les colones
                if (j == 1 && k == 1 || j == 6 && k == 6
                        || j == 2 && k == 1 || j == 1 && k == 2
                        || j == 1 && k == 5 || j == 5 && k == 1
                        || j == 6 && k == 1 || j == 1 && k == 6
                        || j == 2 && k == 6 || j == 6 && k == 2
                        || j == 5 && k == 6 || j == 6 && k == 5) {//on enlève les tuiles qui sont sur les cotés
                    panel.add(new JLabel(""));
                } else {
                    
                    panel.add(tuile.get(tuiles[numTuile].getId()));
                    
                    numTuile += 1;
                }
                
            }
    panel.setOpaque(false);
*/