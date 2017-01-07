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

}
