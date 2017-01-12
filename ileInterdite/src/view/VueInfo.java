/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils.Commandes;
import util.Utils.Pion;

/**
 *
 * @author dandelr
 */
public class VueInfo extends Observable{

    private final JFrame window;
    private JPanel panelGlobal;
    private JLabel nomPersonnage, infoPersonnage;
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private Dimension dim = kit.getScreenSize();
        

    public VueInfo(Pion pion) {

        window = new JFrame();
        window.setUndecorated(true);
        window.setAlwaysOnTop(true);//met en premier plan
        
        panelGlobal = new JPanel(new BorderLayout());
        panelGlobal.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK)); //bords noir de la fenêtre
        // Définit la taille de la fenêtre en pixels
        window.setSize(dim.width *4/24, dim.height / 4);
        window.setLocation(dim.width*45 / 100, dim.height*34 / 100);
        //titre
        nomPersonnage = new JLabel();
        infoPersonnage = new JLabel();
        
        JButton btnOk = new JButton("Ok");

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Commandes.OK_Info);
                clearChanged();
            }
        });
        
      
        
        //donne des informations suivant le joueur
        switch (pion) {
            case BLEU:
                panelGlobal.setBackground(new Color(51,153,255));
                
                nomPersonnage.setText("<html><u><B>Le Pilote:</B></u></html>");
                infoPersonnage.setText("<html>Le Pilote peut, une fois <br>par tour, voler jusqu'à <br>n'importe quelle tuile de<br> l'île pour une action.</html>");
                break;
            case JAUNE:
                panelGlobal.setBackground(new Color(255,255,102));
                
                nomPersonnage.setText("<html><u><B>Le Navigateur:</B></u></html>");
                infoPersonnage.setText("<html>Le Navigateur a 4 actions</html>");
                break;
            case ORANGE:
                panelGlobal.setBackground(new Color(255,178,102));
                
                nomPersonnage.setText("<html><u><B>Le Messager:</B></u></html>");
                infoPersonnage.setText("<html>Le Messager peut donner<br>des cartes Trésor à un <br>autre joueur n'importe où <br>sur l'île pour 1 action<br>par carte.</html>");
                break;
            case ROUGE:
                panelGlobal.setBackground(new Color(255,102,102));
                
                nomPersonnage.setText("<html><u><B>L’Ingénieur:</B></u></html>");
                infoPersonnage.setText("<html>L'Ingénieur peut assécher<br>2 tuiles pour une action.</html>");
                break;
            case VERT:
                panelGlobal.setBackground(new Color(153,255, 153));
                
                nomPersonnage.setText("<html><u><B>L’Explorateur:</B></u></html>");
                infoPersonnage.setText("<html>L'Explorateur peut se <br>déplacer et assécher<br>en diagonale.</html>");
                break;
            case VIOLET:
                panelGlobal.setBackground(new Color(178,102, 255));
                
                nomPersonnage.setText("<html><u><B>Le Plongeur</B></u></html>");
                infoPersonnage.setText("<html>Le Plongeur peut se<br>déplacer au travers<br>d’une ou plusieurs tuiles<br>adjacentes manquantes<br>et/ou inondées pour 1<br>action.</html>");
                break;
        }
        nomPersonnage.setFont(new Font("Arial", 0, 30));
        nomPersonnage.setHorizontalAlignment(JLabel.CENTER);
        infoPersonnage.setFont(new Font("Arial", 0, 20));
        infoPersonnage.setHorizontalAlignment(JLabel.CENTER);
        
        panelGlobal.add(nomPersonnage, BorderLayout.NORTH);
        panelGlobal.add(infoPersonnage, BorderLayout.CENTER);
        panelGlobal.add(btnOk,BorderLayout.SOUTH);
        window.add(panelGlobal);
        window.setVisible(true);

    }
    public void fermerFenetre() {
        window.dispose();
    }

}