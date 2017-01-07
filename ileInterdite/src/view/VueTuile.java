package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.EtatTuile;

public class VueTuile extends JPanel {

    private ImageIcon img;
    private JLabel tuile;
    private EtatTuile etatTuile = EtatTuile.ASSECHEE;

    public VueTuile() {
        //taille des tuiles qui s'adapte à l'écran
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setPreferredSize(new Dimension(dim.height / 6 - 25, dim.height / 6 - 25));
        tuile = new JLabel();
        this.add(tuile);
    }

    public void etatDeLaTuile(String image) {
        img = new ImageIcon(new ImageIcon(getClass().getResource(image))
                .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        tuile.setIcon(img);
    }
    
    public void assecheeInondeeOuCouleeTuile(int numTuile, EtatTuile etatTuile) {
        System.out.println("dfvhio");
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

        etatDeLaTuile(img);
    }

}
