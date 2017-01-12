package model;

/**
 *
 * @author IUT2-Dept Info
 */
public class ObjetIdentifie {

    private static Integer lastId = 1;
    protected Integer id;

    public ObjetIdentifie() {
        this.id = ObjetIdentifie.getNextId();
    }

    public ObjetIdentifie(Integer id) {
        this.id = id;
    }

    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////GETTERS&SETTERS//////////////////////////
    ////////////////////////////////////////////////////////////////////////////    
    
    public Integer getId() {
        return this.id;
    }

    public static Integer getNextId() {
        return lastId++;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
