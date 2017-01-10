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
    private JPanel panelGlobale, panelCentre, panelCentre1, panelCentre2, panelBas;
    private JButton bValider, bAnnuler;
    private JLabel titre, textNbJ;
    private JComboBox nbJoueur;
    private JButton bOk;
    private JTextField joueur1, joueur2, joueur3, joueur4;

    private Font font = new Font("Arial", 0, 30);
    private Font font2 = new Font("Arial", 0, 15);
    private GridLayout gl1 = new GridLayout(2, 2);
    private GridLayout gl2 = new GridLayout(1, 2);

    public VueInscription() {
        window = new JFrame();
        window.setSize(300, 230);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setUndecorated(true);//enlève le cadre de ta fenêtre
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2 - 150, dim.height / 2 - 265);
        panelGlobale = new JPanel(new BorderLayout());

        //***haut***
        titre = new JLabel("L'île Interdite");
        titre.setFont(font);
        panelGlobale.add(titre, BorderLayout.NORTH);

        //***panel centre***
        panelCentre = new JPanel();
        //***panel centre 1 (haut du panel centre)***
        panelCentre1 = new JPanel();
        //texte "Rentrer les joueurs"
        textNbJ = new JLabel("Rentrer les joueurs :");
        textNbJ.setFont(font2);

        panelCentre1.add(textNbJ, BorderLayout.CENTER);

        panelCentre.add(panelCentre1, BorderLayout.NORTH);
        //***panel centre 2 (bas du panel centre)***
        gl1.setVgap(8);
        gl1.setHgap(8);
        panelCentre2 = new JPanel(gl1);

        //JTextField joueur 1
        joueur1 = new JTextField("Nom joueur");
        joueur1.setPreferredSize(new Dimension(120, 28));
        insiterRentrerNom(joueur1);
        panelCentre2.add(joueur1);

        //JTextField joueur 2
        joueur2 = new JTextField("Nom joueur");
        joueur2.setPreferredSize(new Dimension(120, 28));
        insiterRentrerNom(joueur2);
        panelCentre2.add(joueur2);

        //JTextField joueur 3
        joueur3 = new JTextField("Nom joueur");
        joueur3.setPreferredSize(new Dimension(120, 28));
        insiterRentrerNom(joueur3);
        panelCentre2.add(joueur3);

        //JTextField joueur 4
        joueur4 = new JTextField("Nom joueur");
        joueur4.setPreferredSize(new Dimension(120, 28));
        insiterRentrerNom(joueur4);
        panelCentre2.add(joueur4);

        panelCentre.add(panelCentre2, BorderLayout.CENTER);

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
