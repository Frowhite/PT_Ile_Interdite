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

    private ImageIcon img;
    private ImageIcon img2;
    private ArrayList<Pion> pion = new ArrayList<>();
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();
    private boolean possibliteDeplacement = false;
    private boolean possibliteAssechement = false;

    public VueTuile(int idTuile, VueGrille vueGrille) {
        //taille des tuiles qui s'adapte à l'écran
        this.setPreferredSize(new Dimension(dim.height / 6 - 25, dim.height / 6 - 25));

        this.addMouseListener(new MouseAdapter() {
            //envoie l'information au controleur que le joueur a cliqué sur une tuile
            @Override
            public void mouseClicked(MouseEvent e) {
                if (possibliteDeplacement) {
                    vueGrille.getVuePlateau().choisirTuileDeplacement(idTuile);
                }
                if (possibliteAssechement) {
                    vueGrille.getVuePlateau().choisirTuileAssechement(idTuile);
                }
            }

            //quand on met la souris dessus: change les bordures
            @Override
            public void mouseEntered(MouseEvent e) {
                if (possibliteDeplacement) {
                    actionAvecMouseListenerDeplace(true);
                } else if (possibliteAssechement) {
                    actionAvecMouseListenerAsseche(true);
                }
            }

            //quand on enlève la souris : remet les bordures dans leur etat initial
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

//////////////////////////////////////BORDER////////////////////////////////////
    public void actionAvecMouseListenerDeplace(boolean b) {
        if (b) {
            this.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(255, 153, 51)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255, 153, 51)));
        }

    }

    public void actionAvecMouseListenerAsseche(boolean b) {
        if (b) {
            this.setBorder(BorderFactory.createMatteBorder(7, 7, 7, 7, new Color(255, 255, 102)));
        } else {
            this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255, 255, 102)));
        }

    }

    public void tuilePossibleDeplacement() {
        possibliteDeplacement = true;//rend la tuile cliquable
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255, 153, 51)));
    }

    public void tuilePossibleAssechement() {
        possibliteAssechement = true;//rend la tuile cliquable
        this.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255, 255, 102)));
    }

///////////////////////////////////////PION////////////////////////////////////
    //ajoute un pion à la tuile
    public void mettrePion(Pion p) {
        pion.add(p);
    }

    //enlève un pion à la tuile
    void enlevePion(Pion p) {
        pion.remove(p);
    }
    
    //donne l'image au pion
    public void dessinerPion(Pion p) {
        String s = "/images/pions/";
        switch (p) {
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
                .getImage().getScaledInstance(dim.height / 6 - 90, dim.height / 6 - 90, Image.SCALE_SMOOTH));

    }

/////////////////////////////////////TUILE//////////////////////////////////////
    
    //donne l'image à la tuile
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

//////////////////////////////////IMAGE FOND////////////////////////////////////
    //dessin le fond
    @Override
    public void paintComponent(Graphics g) {
        //dessine la tuile
        g.drawImage(img.getImage(), 3, 3, null);

        //dessine les joueurs en fonction du nombre de joueur qu'il y a sur la tuile
        if (pion.size() == 1) {
            dessinerPion(pion.get(0));
            g.drawImage(img2.getImage(), 43, 20, null);
        } else {
            for (int i = 0; i < pion.size(); i++) {
                switch (i) {
                    case 0:
                        dessinerPion(pion.get(i));
                        g.drawImage(img2.getImage(), 10, 10, null);
                        break;
                    case 1:
                        dessinerPion(pion.get(i));
                        g.drawImage(img2.getImage(), 66, 10, null);
                        break;
                    case 2:
                        dessinerPion(pion.get(i));
                        g.drawImage(img2.getImage(), 28, 50, null);
                        break;
                    case 3:
                        dessinerPion(pion.get(i));
                        g.drawImage(img2.getImage(), 84, 50, null);
                        break;
                }

            }
        }
    }

    public void imageTuile(String image) {
        img = new ImageIcon(new ImageIcon(getClass().getResource(image))
                .getImage().getScaledInstance(dim.height / 6 - 32, dim.height / 6 - 32, Image.SCALE_SMOOTH));

    }
    
    
    /////////////////GETTEURS&SETTEURS////////////////// 

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
