package view;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.EtatTuile;

public class VueGrille extends JPanel {

    private ArrayList<VueTuile> tuile = new ArrayList<>();

    private JPanel panelGlobale;
    private GridLayout gl = new GridLayout(6, 6);

    public VueGrille() {
        gl.setVgap(5);
        gl.setHgap(5);
        panelGlobale = new JPanel(gl);

        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile(0);
            tuile.add(t);
        }
        affichePlateau(panelGlobale);
        this.add(panelGlobale);

    }

    public void affichePlateau(JPanel panel) {
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
                    panel.add(tuile.get(numTuile));
                    numTuile += 1;
                }
            }
        }

    }

}
