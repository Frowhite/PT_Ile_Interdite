package controler;


import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Aventurier;
import model.cases.Tuile;
import util.Utils;
import util.Utils.EtatTuile;
import util.Utils.Pion;

/**
 *
 * @author IUT2-Dept Info
 */
public class Controleur implements Observer {
    //fezfzefzefz
    //tygifrio
    
    public Controleur() {
        
        
    }

    public void Assecher(Aventurier av, Tuile tuile){
        Pion capacite;
        capacite= av.getCapacite();
        
//         //if (tuile.getEtat()== EtatTuile.ASSECHEE && tuileAdjacente()){
//             
//             
//         }
//         
//        
        
         
         
        
        
    }
    
   
    

    @Override
    public void update(Observable o, Object arg) {
    }

    private boolean TuileEstDiagonale(Tuile avTuile, Tuile tuile) {
      return ((tuile.getC()== avTuile.getC()-1 && tuile.getL()==avTuile.getL()-1) || (tuile.getC()== avTuile.getC()+1 && tuile.getL()== avTuile.getL()-1) || (tuile.getL()+1== avTuile.getL()+1 && tuile.getC()== avTuile.getC()-1) || (tuile.getC()==avTuile.getC()+1 && tuile.getL()==avTuile.getL()+1));
          
    }

}
