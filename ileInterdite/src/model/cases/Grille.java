 package model.cases;

import java.util.ArrayList;
import model.aventuriers.Aventurier;
import util.Utils;
import util.Utils.EtatTuile;
import util.Utils.Pion;

/**
 * Classe permettant de gérer la grille des tuiles du jeu Elle gère un unique
 * attribut : un tableau de 6 x 6 tuiles Il y a 12 tuiles null et 24 tuiles
 * réelles. Les tuiles sont donc (ligne, colonne) null null (0,2) (0,3) null
 * null null (1,1) (1,2) (1,3) (1,4) null (2,0) (2,1) (2,2) (2,3) (2,4) (2,5)
 * (3,0) (3,1) (3,2) (3,3) (3,4) (3,5) null (4,1) (4,2) (4,3) (4,4) null null
 * null (5,2) (5,3) null null
 *
 * @author IUT2-Dept Info
 */
public class Grille {

    private Tuile[][] tuiles; // Les tuiles du jeu
    private boolean competanceActiveBleu = true;

    public Grille(Tuile[] tuile) {
        this.tuiles = new Tuile[6][6];
        remplirGrille(tuile);
    }

    public void tuilesPossiblesDeplacement(Aventurier av) {

        ArrayList<Tuile> buffer = new ArrayList();

        if (av.getCapacite() == Pion.BLEU && isCompetenceActiveBleu()) {
            for (int l = 0; l < 6; l++) {
                for (int c = 0; c < 6; c++) {
                    if ((l == 0 && (c == 2 || c == 3))
                            || (l == 1 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 2 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 3 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 4 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 5 && (c == 2 || c == 3))) {
                        if (tuiles[l][c].getEtat() != Utils.EtatTuile.COULEE) {
                            av.addTuilesPossibles(tuiles[l][c]);
                            av.remTuilesPossibles(av.getPositionCourante());
                        }
                    }
                }
            }

        } else {
            if (av.getCapacite() == Pion.VIOLET) {
                //Tuile positionRelative = new Tuile(500, "Position Relative", null);
                ajoutTuileDepPlongeur(av.getPositionCourante(), buffer, 0); //ajoute les tuiles déplacement du plongeur

            }

            if (estSurLePlateau(av.getPositionCourante().getLigne(), av.getPositionCourante().getColonnes() - 1) && !buffer.contains(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() - 1])) {
                buffer.add(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() - 1]);
            }

            if (estSurLePlateau(av.getPositionCourante().getLigne(), av.getPositionCourante().getColonnes() + 1) && !buffer.contains(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() + 1])) {
                buffer.add(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() + 1]);
            }

            if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes()) && !buffer.contains(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes()])) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes()]);
            }

            if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes()) && !buffer.contains(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes()])) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes()]);
            }

            if (av.getCapacite() == Pion.VERT) {

                if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes() - 1)) {
                    buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes() - 1]);
                }
                if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes() + 1)) {
                    buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes() + 1]);
                }
                if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes() + 1)) {
                    buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes() + 1]);
                }
                if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes() - 1)) {
                    buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes() - 1]);
                }

            }
            buffer.remove(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes()]);

                for (Tuile t : buffer) {
                    if (t != null && t.getEtat() != Utils.EtatTuile.COULEE) {
                        av.addTuilesPossibles(t);
                    }
                }
            

        }
    }

    public void ajoutTuileDepPlongeur(Tuile t, ArrayList<Tuile> tDep, int i) {

        if (estSurLePlateau(t.getLigne(), t.getColonnes() - 1) && tuiles[t.getLigne()][t.getColonnes() - 1] != null) {
            if (tuiles[t.getLigne()][t.getColonnes() - 1].getEtat() != EtatTuile.ASSECHEE && !tDep.contains(tuiles[t.getLigne()][t.getColonnes() - 1])) {
                tDep.add(tuiles[t.getLigne()][t.getColonnes() - 1]);
            }
        }

        if (estSurLePlateau(t.getLigne(), t.getColonnes() + 1) && tuiles[t.getLigne()][t.getColonnes() + 1] != null) {
            if (tuiles[t.getLigne()][t.getColonnes() + 1].getEtat() != EtatTuile.ASSECHEE && !tDep.contains(tuiles[t.getLigne()][t.getColonnes() + 1])) {
                tDep.add(tuiles[t.getLigne()][t.getColonnes() + 1]);
            }
        }
        if (estSurLePlateau(t.getLigne() - 1, t.getColonnes()) && tuiles[t.getLigne() - 1][t.getColonnes()] != null) {
            if (tuiles[t.getLigne() - 1][t.getColonnes()].getEtat() != EtatTuile.ASSECHEE && !tDep.contains(tuiles[t.getLigne() - 1][t.getColonnes()])) {
                tDep.add(tuiles[t.getLigne() - 1][t.getColonnes()]);
            }
        }
        if (estSurLePlateau(t.getLigne() + 1, t.getColonnes()) && tuiles[t.getLigne() + 1][t.getColonnes()] != null) {
            if (tuiles[t.getLigne() + 1][t.getColonnes()].getEtat() != EtatTuile.ASSECHEE && !tDep.contains(tuiles[t.getLigne() + 1][t.getColonnes()])) {
                tDep.add(tuiles[t.getLigne() + 1][t.getColonnes()]);
            }
        }
        if (i != tDep.size()) {
            ajoutTuileDepPlongeur(tDep.get(i), tDep, i + 1);
        }
    }

  
    public void remplirGrille(Tuile[] tuile) {
        int i = 0;
        for (int l = 0; l < 6; l++) {
            for (int c = 0; c < 6; c++) {
                if ((l == 0 && (c == 2 || c == 3))
                        || (l == 1 && (c == 1 || c == 2 || c == 3 || c == 4))
                        || (l == 2 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                        || (l == 3 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                        || (l == 4 && (c == 1 || c == 2 || c == 3 || c == 4))
                        || (l == 5 && (c == 2 || c == 3))) {
                    tuiles[l][c] = tuile[i];
                    tuile[i].setLigne(l);
                    tuile[i].setColonnes(c);
                    i++;
                }
            }
        }
    }

    public boolean estSurLePlateau(int l, int c) {
        if ((l == -1) || (l == 6) || (c == -1) || (c == 6)) {
            return false;
        } else {
            return true;
        }
    }

    public void tuilesPossiblesAssechement(Aventurier av) {

        ArrayList<Tuile> buffer = new ArrayList();

        buffer.add(av.getPositionCourante());

        if (estSurLePlateau(av.getPositionCourante().getLigne(), av.getPositionCourante().getColonnes() - 1)) {
            buffer.add(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() - 1]);
        }

        if (estSurLePlateau(av.getPositionCourante().getLigne(), av.getPositionCourante().getColonnes() + 1)) {
            buffer.add(tuiles[av.getPositionCourante().getLigne()][av.getPositionCourante().getColonnes() + 1]);
        }

        if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes())) {
            buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes()]);
        }

        if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes())) {
            buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes()]);
        }

        if (av.getCapacite() == Pion.VERT) {

            if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes() - 1)) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes() - 1]);
            }
            if (estSurLePlateau(av.getPositionCourante().getLigne() + 1, av.getPositionCourante().getColonnes() + 1)) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() + 1][av.getPositionCourante().getColonnes() + 1]);
            }
            if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes() + 1)) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes() + 1]);
            }
            if (estSurLePlateau(av.getPositionCourante().getLigne() - 1, av.getPositionCourante().getColonnes() - 1)) {
                buffer.add(tuiles[av.getPositionCourante().getLigne() - 1][av.getPositionCourante().getColonnes() - 1]);
            }

        }
        for (Tuile t : buffer) {
            if (t != null && t.getEtat() == Utils.EtatTuile.INONDEE) {
                av.addTuilesPossiblesAssechement(t);

            }
        }
    }
    
    public ArrayList<Tuile> CasesInonder(){
        ArrayList<Tuile> casesInonde = new ArrayList();
        
        for (int l = 0; l < 6; l++) {
                for (int c = 0; c < 6; c++) {
                    if ((l == 0 && (c == 2 || c == 3))
                            || (l == 1 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 2 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 3 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 4 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 5 && (c == 2 || c == 3))) {
                        if (tuiles[l][c].getEtat() == Utils.EtatTuile.INONDEE) {
                            casesInonde.add(tuiles[l][c]);
                        }
                    }
                }
            }
        return casesInonde;
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////GETTEURS&SETTEURS////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public boolean isCompetenceActiveBleu() {
        return competanceActiveBleu;
    }

    public void setCompetenceActiveBleu(boolean competanceActitiveBleu) {
        this.competanceActiveBleu = competanceActitiveBleu;
    }

}
