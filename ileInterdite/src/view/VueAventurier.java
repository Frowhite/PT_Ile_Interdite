package view;

import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class VueAventurier extends JPanel {
    private JLabel donnerNomJoueur, carte;
    private String nomJoueur;
    private JPanel panelGlobale,panelCentre;
    private GridLayout gl = new GridLayout(3, 3);
    
    public VueAventurier(){
        //***haut***
        donnerNomJoueur=new JLabel("Information " + nomJoueur);
        panelGlobale.add(donnerNomJoueur);
        //***panel centre***
        panelCentre=new JPanel(gl);
        
        panelGlobale.add(panelCentre);
        
        
        
        this.add(panelGlobale);
    }
    
    public void ajouterCarte(int carte){
        panelCentre.add(new JLabel(new ImageIcon(getClass().getResource("/images/titre.png"))));
    
    
    }
}