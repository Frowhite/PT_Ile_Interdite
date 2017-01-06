package controler;


import java.util.Observable;
import java.util.Observer;
import model.aventuriers.Aventurier;
import model.cases.Tuile;
import util.Utils;
import util.Utils.EtatTuile;
import static util.Utils.EtatTuile.ASSECHEE;
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

    
       private boolean tuileEstDiagonale(Tuile avTuile, Tuile tuile) {
      return ((tuile.getC()== avTuile.getC()-1 && tuile.getL()==avTuile.getL()-1) || (tuile.getC()== avTuile.getC()+1 && tuile.getL()== avTuile.getL()-1) || (tuile.getL()+1== avTuile.getL()+1 && tuile.getC()== avTuile.getC()-1) || (tuile.getC()==avTuile.getC()+1 && tuile.getL()==avTuile.getL()+1));
          
    }
    
    private boolean tuileEstAdjacente(Tuile avTuile, Tuile tuile){
        
       return ((tuile.getC()==avTuile.getC() && tuile.getL()== avTuile.getL()-1) ||(tuile.getC()==avTuile.getC()+1 && tuile.getL()==avTuile.getL()) || (tuile.getC()==avTuile.getC() && tuile.getL()==avTuile.getL()+1) || (tuile.getC()==avTuile.getC()-1 && tuile.getL()==avTuile.getL()));
    }
   
    
    public void Assecher(Aventurier av, Tuile tuile){
        
        if (tuile.getEtat()== EtatTuile.INONDEE && tuileEstAdjacente(av.getPositionCourante(), tuile)){
             
             tuile.setEtat(ASSECHEE);
             
             
             
        if (tuile.getEtat()== EtatTuile.INONDEE && tuileEstDiagonale(av.getPositionCourante(), tuile) && av.getCapacite()== Pion.VERT)     
             
             tuile.setEtat(ASSECHEE);
         }
         
         
    }
    
 
    

    @Override
    public void update(Observable o, Object arg) {
    }

    

}
