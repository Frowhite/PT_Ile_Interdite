package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.cases.Tuile;
import util.Utils;
import util.Utils.EtatTuile;

public class VueGrille extends JPanel {

    private HashMap<Integer, VueTuile> tuile = new HashMap<>();

    private Image img;
    private JPanel panelGlobale;
    private GridLayout gl = new GridLayout(6, 6);

    public VueGrille(Image img) {
        this.img = img;
        this.setPreferredSize(new Dimension(600, 600));
        gl.setVgap(10);//espace entre les tuiles
        gl.setHgap(10);

        panelGlobale = new JPanel(gl);

        this.add(panelGlobale);
    }

    public void initialiserPlateau(Tuile[] tuile) {
        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile();
            t.assecheeInondeeOuCouleeTuile(i, EtatTuile.ASSECHEE);// il calasse du plus petit au plus grand
            this.tuile.put(tuile[i].getId(), t);
        }
        affichePlateau(panelGlobale);
    }

    public void affichePlateau(JPanel panel) {
        System.out.println("3");
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
                    int x = 0;
                    for (HashMap.Entry<Integer, VueTuile> e : tuile.entrySet()) {
                        if (x == numTuile) {
                            panel.add(e.getValue());
                        }
                        x++;
                    }

                    numTuile += 1;
                }
            }
        }
        panel.setOpaque(false);
    }

    public void etatTuile(int numTuile, EtatTuile etatTuile){
        tuile.get(numTuile).assecheeInondeeOuCouleeTuile(numTuile, etatTuile);
    
    
    }
    
    
    //dessin le fond
    public void paintComponent(Graphics g) {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, 0, 0, null);
    }
}
