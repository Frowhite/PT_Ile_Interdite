package view;
 
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.EtatTuile;

public class VueTuile extends JPanel {
    private int ligne, colone;
    
    private ImageIcon img;
    private JLabel tuile;
    private EtatTuile etatTuile=EtatTuile.ASSECHEE;
    public VueTuile(int idTuile){
        setLigne(ligne);
        setColone(colone);
        
        this.setPreferredSize(new Dimension(50, 50));
        img = new ImageIcon(getClass().getResource("/images/titre.png"));
        tuile = new JLabel(img);
        this.add(tuile);
    }

    public void setLigne(int ligne) {
        this.ligne = ligne;
    }

    public void setColone(int colone) {
        this.colone = colone;
    }

    public int getLigne() {
        return ligne;
    }

    public int getColone() {
        return colone;
    }
    
    
}