package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VueGrille extends JPanel {

    private HashMap<Integer, VueTuile> tuile = new HashMap<>();

    private Image img;
    private JPanel panelGlobale;
    private GridLayout gl = new GridLayout(6, 6);

    public VueGrille(Image img) {
        this.img = img;
        this.setPreferredSize(new Dimension(600, 600));
        gl.setVgap(10);
        gl.setHgap(10);
        panelGlobale = new JPanel(gl);

        for (int i = 1; i <= 24; i++) {
            VueTuile t = new VueTuile();
            tuile.put(i, t);
        }
        affichePlateau(panelGlobale);
        this.add(panelGlobale);
    }

    //dessin le fond
    public void paintComponent(Graphics g) {
        
        Toolkit kit =  Toolkit.getDefaultToolkit(); 
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, dim.width/2-400, 0, null);
    }

    public void affichePlateau(JPanel panel) {
        int numTuile = 1;
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
                    panel.add(tuile.get(numTuile));
                    numTuile += 1;
                }
            }
        }
        panel.setOpaque(false);

    }
}
