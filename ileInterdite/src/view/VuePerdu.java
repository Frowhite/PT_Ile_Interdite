/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import util.Utils;

/**
 *
 * @author pierreju
 */
public class VuePerdu {
    private JFrame window;
    private JPanel panel,panelSud,panelCentre;
    private JLabel message;
    //private JButton Fin = new JButton("OK");
    private ImageIcon img;
    
    public VuePerdu(){
     window = new JFrame("Perdu");
        window.setSize(600,600);
        window.setAlwaysOnTop(true);//met en premier plan
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension dim = kit.getScreenSize();
        window.setLocation(dim.width / 2-250, dim.height / 2-200 );
       
        img = new ImageIcon(new ImageIcon(getClass().getResource("/images/perdu.jpg"))
                .getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        JLabel label = new JLabel();
        label.setIcon(img);

        
        window.add(label);
        
        JOptionPane.showMessageDialog(window, "Vous avez perdu", "Nonnnnnnnn!!!!!!", JOptionPane.INFORMATION_MESSAGE);
        
        
//        panel = new JPanel(new BorderLayout());
//        panelSud = new JPanel(new GridLayout(1,3));
//        message = new JLabel("Vous avez PERDU!!!!!!!!! #Atlantide");
//        panelCentre = new JPanel();
//        panelCentre.add(message);
//        
//        
//        
//        Fin.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.exit(0);
//            }
//        });
//        for(int i = 0; i<3;i++){
//            if(i == 2){
//                panelSud.add(Fin);
//            }else{
//                panelSud.add(new JLabel(""));
//            }
//        }
//       
    
        
        window.setVisible(true);
        
    }

}
