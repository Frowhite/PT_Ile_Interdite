package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import util.Utils;
import util.Utils.EtatTuile;
import util.Utils.Pion;

public class VueTuile extends JPanel {

    private VueGrille vueGrille;
    private ImageIcon img;
    private ImageIcon img2;
    private JLabel tuile;
    private EtatTuile etatTuile = EtatTuile.ASSECHEE;
    int x=0;
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();
    private Pion pion = null;
    

    public VueTuile(VueGrille vueGrille) {
        this.vueGrille=vueGrille;
        //taille des tuiles qui s'adapte à l'écran
        this.setPreferredSize(new Dimension(dim.height / 6 - 25, dim.height / 6 - 25));
        tuile = new JLabel();
        tuile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                vueGrille.getVuePlateau().test();
                
            }
        });
        this.add(tuile);
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//bordure
    }

    public void etatDeLaTuile(String image) {
        img = new ImageIcon(new ImageIcon(getClass().getResource(image))
                .getImage().getScaledInstance(dim.height / 6 - 32, dim.height / 6 - 32, Image.SCALE_DEFAULT));
        
    }
    
    public void mettrePion(Pion p){
        pion=p;
        String i = "/images/pions/";
        switch (p) {
            case BLEU:
                i+="pionBleu";
                break;
            case JAUNE:
                i+="pionJaune";
                break;
            case ORANGE:
                i+="pionBronze";
                break;
            case ROUGE:
                i+="pionRouge";
                break;
            case VERT:
                i+="pionVert";
                break;
            case VIOLET:
                i+="pionViolet";
                break;
        }
        i+=".png";
        
        
        img2 = new ImageIcon(new ImageIcon(getClass().getResource(i))
                .getImage().getScaledInstance(dim.height / 6 - 100, dim.height / 6 - 100, Image.SCALE_DEFAULT));
        tuile.setIcon(img2);
        
    }
    
    void enlevePion() {
       pion=null;
       img2=new ImageIcon(new ImageIcon(getClass().getResource(""))
                .getImage().getScaledInstance(dim.height / 6 - 100, dim.height / 6 - 100, Image.SCALE_DEFAULT));
       tuile.setIcon(img2);
    }
    
    
    //dessin le fond
    public void paintComponent(Graphics g) {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        g.drawImage(img.getImage(), 0, 0, null);
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

        etatDeLaTuile(img);
    }

    public Pion getPion() {
        return pion;
    }

    public void setPion(Pion pion) {
        this.pion = pion;
    }    
    
}
