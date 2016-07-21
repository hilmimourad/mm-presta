package data;

import business.data.FamillePrestationDAO;
import business.model.FamillePrestation;
import org.junit.Test;

/**
 * Created by mourad on 7/21/2016.
 */
public class FamillesPrestation {

    @Test
    public void initDatabase(){
        FamillePrestation fm1 = new FamillePrestation();
        FamillePrestation fm2 = new FamillePrestation();

        fm1.setLibelle("Famille Mere 1");
        fm1.setCode("fm1");
        FamillePrestationDAO.insertFamillePrestation(fm1);


        fm2.setLibelle("Famille Mere 2");
        fm2.setCode("fm2");
        FamillePrestationDAO.insertFamillePrestation(fm2);

        FamillePrestation f11 = new FamillePrestation();
        f11.setLibelle("Famille Fille 1-1");
        f11.setCode("ff11");
        f11.setFamillemere(fm1);
        FamillePrestationDAO.insertFamillePrestation(f11);

        FamillePrestation f12 = new FamillePrestation();
        f12.setLibelle("Famille Fille 1-2");
        f12.setCode("ff12");
        f12.setFamillemere(fm1);
        FamillePrestationDAO.insertFamillePrestation(f12);


        FamillePrestation f21 = new FamillePrestation();
        f21.setLibelle("Famille Fille 2-1");
        f21.setCode("ff21");
        f21.setFamillemere(fm2);
        FamillePrestationDAO.insertFamillePrestation(f21);
    }
}
