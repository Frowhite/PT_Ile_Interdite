package model.cases;

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

    Tuile[][] tuiles; // Les tuiles du jeu

    public Grille(Tuile[] tuile) {
        this.tuiles = new Tuile[6][6];
        remplirGrille(tuile);
    }

    public void remplirGrille(Tuile[] tuile) {
        int i = 0;
        for (int l = 0; l < 6; l++){
            for (int c = 0; c < 6; c++){
                    if      ((l == 0 && (c == 2 || c == 3))
                            || (l == 1 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 2 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 3 && (c == 0 || c == 1 || c == 2 || c == 3 || c == 4 || c == 5))
                            || (l == 4 && (c == 1 || c == 2 || c == 3 || c == 4))
                            || (l == 5 && (c == 2 || c == 3))) {
                        tuiles[l][c] = tuile[i];
                        tuile[i].setLigne(l);
                        tuile[i].setColonnes(c);
                        System.out.println("Nom : " + tuile[i].getNomTuile() + ", Ligne : " + tuile[i].getLigne() + ", Colonne : " + tuile[i].getColonnes());
                        i++;
                    }
            }
        }
    }
}

        
        
    


