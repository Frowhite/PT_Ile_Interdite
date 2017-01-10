package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import util.Utils;

/**
 *
 * @author IUT2-Dept Info
 */
public class VueInscription extends Observable {

    private JFrame window;
    private JPanel panelGlobale,panelHaut, panelCentre, panelCentreTF, panelBas;
    private JButton bValider, bAnnuler;
    private JLabel titre, textNbJ, textNiv;
    private JComboBox nbJoueur;
    private JButton bOk;
    private JTextField joueur1, joueur2, joueur3, joueur4;

    private Font font1 = new Font("Arial", 0, 25);
    private Font font2 = new Font("Arial", 0, 15);
    private GridLayout gl1 = new GridLayout(2, 2);
    private GridLayout gl2 = new GridLayout(1, 2);

    public VueInscription() {
        window = new JFrame();
        window.setSize(340, 430);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2 - 180, dim.height / 2 - 315);
        panelGlobale = new JPanel(new BorderLayout());

        //***haut***
        titre = new JLabel(new ImageIcon(getClass().getResource("/images/titre.png")));
        panelGlobale.add(titre, BorderLayout.NORTH);

        //***panel haut***
        //texte "Choisir niveau"
        panelHaut = new JPanel(new BorderLayout());
        textNiv = new JLabel("Choisir niveau :");
        textNiv.setFont(font1);
        panelHaut.add(textNiv, BorderLayout.NORTH);
        
        
        panelGlobale.add(panelHaut, BorderLayout.NORTH);
        //***panel centre***
        panelCentre = new JPanel();
        //***haut du panel centre***
        //texte "Rentrer les joueurs"
        textNbJ = new JLabel("Rentrer les joueurs :");
        textNbJ.setFont(font1);
        panelCentre.add(textNbJ, BorderLayout.NORTH);
        //***panel centre 2 (bas du panel centre)***
        gl1.setVgap(8);
        gl1.setHgap(8);
        panelCentreTF = new JPanel(gl1);

        //JTextField joueur 1
        joueur1 = new JTextField("Nom joueur");
        joueur1.setPreferredSize(new Dimension(130, 30));
        joueur1.setFont(font2);
        insiterRentrerNom(joueur1);
        panelCentreTF.add(joueur1);

        //JTextField joueur 2
        joueur2 = new JTextField("Nom joueur");
        joueur2.setPreferredSize(new Dimension(130, 30));
        joueur2.setFont(font2);
        insiterRentrerNom(joueur2);
        panelCentreTF.add(joueur2);

        //JTextField joueur 3
        joueur3 = new JTextField("Nom joueur");
        joueur3.setPreferredSize(new Dimension(130, 30));
        joueur3.setFont(font2);
        insiterRentrerNom(joueur3);
        panelCentreTF.add(joueur3);

        //JTextField joueur 4
        joueur4 = new JTextField("Nom joueur");
        joueur4.setPreferredSize(new Dimension(130, 30));
        joueur4.setFont(font2);
        insiterRentrerNom(joueur4);
        panelCentreTF.add(joueur4);

        panelCentre.add(panelCentreTF, BorderLayout.CENTER);

        panelGlobale.add(panelCentre, BorderLayout.CENTER);
        //***panel Bas***
        gl2.setHgap(50);
        panelBas = new JPanel(gl2);
        //boutton annuler
        bAnnuler = new JButton("Annuler");
        bAnnuler.setFont(font2);
        bAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.ANNULER);
                clearChanged();
            }
        });
        //boutton valider
        bValider = new JButton("Valider");
        bValider.setFont(font2);
        bValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setChanged();
                notifyObservers(Utils.Commandes.VALIDER_JOUEURS);
                clearChanged();
            }
        });

        panelBas.add(bValider);
        panelBas.add(bAnnuler);

        panelGlobale.add(panelBas, BorderLayout.SOUTH);

        window.add(panelGlobale);
        window.setVisible(true);
    }

    //le texte "Nom" s'affiche avant de sésire un texte
    public void insiterRentrerNom(JTextField jtf) {

        jtf.getFont().deriveFont(Font.ITALIC);
        jtf.setForeground(Color.gray);
        jtf.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField texteField = ((JTextField) e.getSource());
                texteField.setText("");
                texteField.getFont().deriveFont(Font.PLAIN);
                texteField.setForeground(Color.black);
                texteField.removeMouseListener(this);
            }
        });

    }

    public void fermerFenetre() {
        window.dispose();
    }

    public String nomJoueur1() {
        return joueur1.getText();
    }

    public String nomJoueur2() {
        return joueur2.getText();
    }

    public String nomJoueur3() {
        return joueur3.getText();
    }

    public String nomJoueur4() {
        return joueur4.getText();
    }
    
    public void donnerNomJoueur1(String nom){
        joueur1.setText(nom);
    }
    
    public void donnerNomJoueur2(String nom){
        joueur2.setText(nom);
    }
    
    public void donnerNomJoueur3(String nom){
        joueur3.setText(nom);
    }
    
    public void donnerNomJoueur4(String nom){
        joueur4.setText(nom);
    }

}
