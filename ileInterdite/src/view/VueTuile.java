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
    
    public VueTuile(){
        //taille des tuiles qui s'adapte à l'écran
        Toolkit kit =  Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        this.setPreferredSize(new Dimension(dim.height/6-25, dim.height/6-25));
        img = new ImageIcon(new ImageIcon(getClass().getResource("/images/titre.png"))
                .getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        tuile = new JLabel(img);
        this.add(tuile);
    }
    
}