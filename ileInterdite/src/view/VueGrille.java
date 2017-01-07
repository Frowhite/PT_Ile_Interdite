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

        for (int i = 0; i < 24; i++) {
            VueTuile t = new VueTuile();
            tuile.put(i, t);
            assecheeInondeeOuCouleeTuile(i, EtatTuile.ASSECHEE);
        }
        affichePlateau(panelGlobale);
        this.add(panelGlobale);
    }

    //dessin le fond
    public void paintComponent(Graphics g) {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img, 0, 0, null);
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
        panel.setOpaque(false);

    }

    public void assecheeInondeeOuCouleeTuile(int numTuile, EtatTuile etatTuile) {
        String img = "/images/tuiles/";
        if (etatTuile == EtatTuile.COULEE) {
            img = "";
        } else {
            switch (numTuile) {
                case 0:
                    img += "Heliport";
                    break;
                case 1:
                    img += "LaCarverneDesOmbres";
                    break;
                case 2:
                    img += "LaCarverneDuBrasier";
                    break;
                case 3:
                    img += "LaForetPourpre";
                    break;
                case 4:
                    img += "LaPorteDeBronze";
                    break;
                case 5:
                    img += "LaPorteDeCuivre";
                    break;
                case 6:
                    img += "LaPorteDeFer";
                    break;
                case 7:
                    img += "LaPortedArgent";
                    break;
                case 8:
                    img += "LaPortedOr";
                    break;
                case 9:
                    img += "LaTourDuGuet";
                    break;
                case 10:
                    img += "LeJardinDesHurlements";
                    break;
                case 11:
                    img += "LeJardinDesMurmures";
                    break;
                case 12:
                    img += "LeLagonPerdu";
                    break;
                case 13:
                    img += "LeMaraisBrumeux";
                    break;
                case 14:
                    img += "LePalaisDeCorail";
                    break;
                case 15:
                    img += "LePalaisDesMarees";
                    break;
                case 16:
                    img += "LePontDesAbimes";
                    break;
                case 17:
                    img += "LeRocherFantome";
                    break;
                case 18:
                    img += "LeTempleDeLaLune";
                    break;
                case 19:
                    img += "LeTempleDuSoleil";
                    break;
                case 20:
                    img += "LeValDuCrepuscule";
                    break;
                case 21:
                    img += "LesDunesDeLIllusion";
                    break;
                case 22:
                    img += "LesFalaisesDeLOubli";
                    break;
                case 23:
                    img += "Observatoire";
                    break;

            }
            if (etatTuile == EtatTuile.INONDEE) {
                img += "_Inonde";
            }
            img += ".png";
        }

        tuile.get(numTuile).etatDeLaTuile(img);
    }

}
