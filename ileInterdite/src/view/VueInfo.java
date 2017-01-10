/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import util.Utils;
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

    public VueInfo(Pion pion) {

        window = new JFrame();
        window.setUndecorated(true);
        window.setAlwaysOnTop(true);//met en premier plan
        
        panelGlobal = new JPanel();
        panelGlobal.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK)); //bords noir de la fenêtre
        
        // Définit la taille de la fenêtre en pixels
        window.setSize(280, 330);
        window.setLocation(680, 50);
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
        infoPersonnage.setFont(new Font("Arial", 0, 20));
        
        panelGlobal.add(nomPersonnage);
        panelGlobal.add(infoPersonnage);
        panelGlobal.add(btnOk);
        window.add(panelGlobal);
        window.setVisible(true);

    }
    public void fermerFenetre() {
        window.dispose();
    }

}
 

        /*
        
        nomPersonnage = new JLabel("<html><u><B>Règle du morpion:</B></u></html>");//souligne et en gras
        
        
        
        nomPersonnage.setFont(new Font("Mistral", 0, 25));
        panelGlobal.add(nomPersonnage, BorderLayout.NORTH);
        //regles
        infoPersonnage = new JLabel();
        //infoAventurier.setText("<html>Les joueurs inscrivent tour à tour<br>leur symbole sur une grille. Le<br>premier qui parvient à aligner trois<br>de ses symboles horizontalement,<br> verticalement ou en diagonale<br>gagne la manche.</html>");
        infoPersonnage.setFont(new Font("Mistral", 0, 17));
        panelGlobal.add(infoPersonnage, BorderLayout.CENTER);

        window.add(panelGlobal);
        window.setVisible(true);
    }
    
    public void couleur(Pion pion){
    
         switch (pion) {
            case BLEU:
                nomPersonnage.setForeground(Color.BLUE);
                infoPersonnage.setForeground(Color.BLUE);
                numPersonnages=1;
                break;
            case JAUNE:
                nomPersonnage.setForeground(Color.YELLOW);
                infoPersonnage.setForeground(Color.YELLOW);
                numPersonnages=2;
                break;
            case ORANGE:
                nomPersonnage.setForeground(Color.ORANGE);
                infoPersonnage.setForeground(Color.ORANGE);
                numPersonnages=3;
                break;
            case ROUGE:
                nomPersonnage.setForeground(Color.RED);
                infoPersonnage.setForeground(Color.RED);
                numPersonnages=4;
                break;
            case VERT:
                nomPersonnage.setForeground(Color.GREEN);
                infoPersonnage.setForeground(Color.GREEN);
                numPersonnages=5;
                break;
            case VIOLET:
                nomPersonnage.setForeground(new Color(153, 0, 153));
                infoPersonnage.setForeground(new Color(153, 0, 153));
                numPersonnages=6;
                break;
        }
    
    
    }
    
    
}
*/
