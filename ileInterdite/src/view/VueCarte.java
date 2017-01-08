/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import util.Utils;

/**
 *
 * @author asus
 */
public class VueCarte extends JPanel {
    
    private ImageIcon img=null;
    private JLabel carte;

    public VueCarte() {
        this.setPreferredSize(new Dimension(100, 110));//taille

        carte = new JLabel();
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));//bordure
        this.add(carte);
    }

    public void mettreCarte(int numCarte) {
        String i = "/images/cartes/";
        if (30 <= numCarte && numCarte <= 34) {
            i += "Calice";
        } else if (35 <= numCarte && numCarte <= 39) {
            i += "Pierre";
        } else if (40 <= numCarte && numCarte <= 44) {
            i += "Zephyr";
        } else if (45 <= numCarte && numCarte <= 49) {
            i += "Cristal";
        } else if (50 <= numCarte && numCarte <= 51) {
            i += "SacsDeSable";
        } else if (52 <= numCarte && numCarte <= 54) {
            i += "Helicoptere";
        } else if (55 <= numCarte && numCarte <= 56) {
            i += "MonteeDesEaux";
        }

        i += ".png";

        img = new ImageIcon(new ImageIcon(getClass().getResource(i)).getImage().getScaledInstance(80, 110, Image.SCALE_DEFAULT));
        carte.setIcon(img);
    }
    
    public void supprCarte(){
        img = null;
    }

    public ImageIcon getImg() {
        return img;
    }

}
