package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import util.Utils.EtatTuile;
import util.Utils.Pion;

public class VueTuile extends JPanel {

    private int test;
    private ImageIcon img;
    private ImageIcon img2;
    private ArrayList<JLabel> labelPion = new ArrayList<>();
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();
    private ArrayList<Pion> pion = new ArrayList<>();
    private boolean possibliteDeplacement = false;
    private boolean possibliteAssechement = false;

    public VueTuile(int idTuile, VueGrille vueGrille) {
        //taille des tuiles qui s'adapte à l'écran
        this.setPreferredSize(new Dimension(dim.height / 6 - 25, dim.height / 6 - 25));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (possibliteDeplacement) {
                    vueGrille.getVuePlateau().choisirTuileDeplacement(idTuile);
                }
                if (possibliteAssechement) {
                    vueGrille.getVuePlateau().choisirTuileAssechement(idTuile);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (possibliteDeplacement) {
                    actionAvecMouseListenerDeplace(true);
                } else if (possibliteAssechement) {
                    actionAvecMouseListenerAsseche(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (possibliteDeplacement) {
                    actionAvecMouseListenerDeplace(false);
                } else if (possibliteAssechement) {
                    actionAvecMouseListenerAsseche(false);
                }
            }
        });

        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//bordure
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
    }

    public void actionAvecMouseListenerDeplace(boolean b) {
        if (b) {
            this.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(255,153,51)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255,153,51)));
        }
        
    }  

    public void actionAvecMouseListenerAsseche(boolean b) {
        if (b) {
            this.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(255,255,102)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255,255,102)));
        }

    }

    public void imageTuile(String image) {
        img = new ImageIcon(new ImageIcon(getClass().getResource(image))
                .getImage().getScaledInstance(dim.height / 6 - 32, dim.height / 6 - 32, Image.SCALE_SMOOTH));

    }

    public void tuilePossibleDeplacement() {
        possibliteDeplacement = true;
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255,153,51)));
    }

    public void tuilePossibleAssechement() {
        possibliteAssechement = true;
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255,255,102)));
    }

    public void mettrePion(Pion p) {
        //prend en compte si il y a plusieur joueur sur la même tuile
        pion.add(p);
        int x = pion.size();//nombre de pion sur la case

        //efface les pions sur la tuile
        for (int i = 0; i < labelPion.size(); i++) {
            img2 = null;
            labelPion.get(i).setIcon(img2);
        }

        labelPion.clear();
        //dessine les pions sur la tuile
        for (int i = 0; i < pion.size(); i++) {

            String s = "/images/pions/";
            switch (pion.get(i)) {
                case BLEU:
                    s += "pionBleu";
                    break;
                case JAUNE:
                    s += "pionJaune";
                    break;
                case ORANGE:
                    s += "pionBronze";
                    break;
                case ROUGE:
                    s += "pionRouge";
                    break;
                case VERT:
                    s += "pionVert";
                    break;
                case VIOLET:
                    s += "pionViolet";
                    break;
            }
            s += ".png";

            img2 = new ImageIcon(new ImageIcon(getClass().getResource(s))
                    .getImage().getScaledInstance(dim.height / 6 - 90 - (x * 10), dim.height / 6 - 90 - (x * 10), Image.SCALE_SMOOTH));
            JLabel l = new JLabel();
            l.setIcon(img2);
            labelPion.add(l);
        }
        for (int i = 0; i < labelPion.size(); i++) {
            this.add(labelPion.get(i));
        }

    }

    void enlevePion(Pion p) {
        int x = 0;
        for (int i = 0; i < pion.size() && pion.get(i) != p; i++) {
            x++;
        }
        pion.remove(p);
        img2 = null;
        labelPion.get(x).setIcon(img2);
        labelPion.remove(x);
    }

    //dessin le fond
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img.getImage(), 3 + 10 * test, 3, null);
    }

    public void assecheeInondeeOuCouleeTuile(int idTuile, EtatTuile etatTuile) {
        String img = "/images/tuiles/";
        if (etatTuile == EtatTuile.COULEE) {
            img += "Tuile_Coulee.jpg";
        } else {
            switch (idTuile) {
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

        imageTuile(img);
    }

    public ArrayList<Pion> getPion() {
        return pion;
    }

    public void setPion(ArrayList<Pion> pion) {
        this.pion = pion;
    }

    public void setPossibliteDeplacement(boolean possibliteDeplacement) {
        this.possibliteDeplacement = possibliteDeplacement;
    }

    public void setPossibliteAssechement(boolean possibliteAssechement) {
        this.possibliteAssechement = possibliteAssechement;
    }

}
