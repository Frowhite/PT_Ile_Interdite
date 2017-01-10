/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author asus
 */
public class VueCarte extends JPanel {

    private boolean possibliteAssechement = false;
    private int idCarte;
    private ImageIcon img = null;
    private JLabel carte;

    public VueCarte(VueAventurier vueAventurier) {
        this.setPreferredSize(new Dimension(100, 130));//taille

        carte = new JLabel();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (possibliteAssechement) {
                    vueAventurier.getVuePlateau().choisirCarte(idCarte);
                }
            }
        });
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//bordure
        this.add(carte);
    }

    public void mettreCarte(int idCarte) {
        this.idCarte = idCarte;
        String i = "/images/cartes/";
        if (30 <= idCarte && idCarte <= 34) {
            i += "Calice";
        } else if (35 <= idCarte && idCarte <= 39) {
            i += "Pierre";
        } else if (40 <= idCarte && idCarte <= 44) {
            i += "Zephyr";
        } else if (45 <= idCarte && idCarte <= 49) {
            i += "Cristal";
        } else if (50 <= idCarte && idCarte <= 51) {
            i += "SacsDeSable";
        } else if (52 <= idCarte && idCarte <= 54) {
            i += "Helicoptere";
        } else if (55 <= idCarte && idCarte <= 56) {
            i += "MonteeDesEaux";
        }

        i += ".png";

        img = new ImageIcon(new ImageIcon(getClass().getResource(i)).getImage().getScaledInstance(85, 115, Image.SCALE_SMOOTH));
        carte.setIcon(img);
    }

    public void supprCarte() {
        img = null;
    }

    public ImageIcon getImg() {
        return img;
    }

}
