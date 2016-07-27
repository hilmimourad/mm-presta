package data;

import business.data.CaracteristiqueProduitDAO;
import business.data.TypeCaracteristiqueProduitDAO;
import business.model.CaracteristiqueProduit;
import business.model.TypeCaracteristiqueProduit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mourad on 7/18/2016.
 */
public class CaracteristiqueProduitTest {

    @Test
    public void test(){

        /**SETTING UP**/
        TypeCaracteristiqueProduit tcp = new TypeCaracteristiqueProduit();
        tcp.setLibelle("Mock TCP");
        Assert.assertNotNull(TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tcp));

        CaracteristiqueProduit cp = new CaracteristiqueProduit();
        cp.setLibelle("Mock CaracteristiqueProduit");
        cp.setDescription("Mock CaracteristiqueProduit Description");
        cp.setType(tcp);

        CaracteristiqueProduit cp2 = new CaracteristiqueProduit();
        cp2.setId(-1);
        cp2.setLibelle("Fake CaracteristiqueProduit");
        cp2.setDescription("Fake CaracteristiqueProduit Description");
        cp2.setType(tcp);



        /**TEST INSERTING**/
        String id = CaracteristiqueProduitDAO.insertCaracteristiqueProduit(cp);
        Assert.assertNotNull(id);

        /**TEST UPDATING**/
        cp.setLibelle("Mock CaracteristiqueProduit Updated");
        cp.setDescription("Mock CaracteristiqueProduit Description Updated");
        Assert.assertNotNull(CaracteristiqueProduitDAO.updateCaracteristiqueProduit(cp));

        cp2.setLibelle("Fake CaracteristiqueProduit Updated");
        cp2.setDescription("Fake CaracteristiqueProduit Description Updated");
        Assert.assertNull(CaracteristiqueProduitDAO.updateCaracteristiqueProduit(cp2));

        /**TEST FETCHING**/
        cp2 = CaracteristiqueProduitDAO.getCaracteristiqueProduit(cp.getId());
        Assert.assertNotNull(cp2);
        Assert.assertEquals(cp2.getLibelle(),cp.getLibelle());
        Assert.assertEquals(cp2.getDescription(),cp.getDescription());
        Assert.assertEquals(cp2.getType(),cp.getType());

        cp2 = CaracteristiqueProduitDAO.find(cp.getLibelle());
        Assert.assertNotNull(cp2);

        cp2 = CaracteristiqueProduitDAO.find(cp.getLibelle()+"nope");
        Assert.assertNull(cp2);

        Assert.assertTrue(CaracteristiqueProduitDAO.getAll().size()>0);

        /**TEST DELETING**/
        Assert.assertTrue(CaracteristiqueProduitDAO.deleteCaracteristiqueProduit(cp.getId()));


        /**DELETING SETUP**/
        Assert.assertTrue(TypeCaracteristiqueProduitDAO.deleteTypeCaracteristiqueProduit(tcp.getId()));
    }

    @Test
    public void initDatabase(){
        TypeCaracteristiqueProduit tc1 = new TypeCaracteristiqueProduit();
        tc1.setLibelle("TypeCar1");
        TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tc1);

        TypeCaracteristiqueProduit tc2 = new TypeCaracteristiqueProduit();
        tc2.setLibelle("TypeCar2");
        TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tc2);


        CaracteristiqueProduit cp1 = new CaracteristiqueProduit();
        cp1.setLibelle("carac11");
        cp1.setDescription("Description carac11");
        cp1.setType(tc1);

        CaracteristiqueProduit cp2 = new CaracteristiqueProduit();
        cp2.setLibelle("carac12");
        cp2.setDescription("Description carac12");
        cp2.setType(tc1);

        CaracteristiqueProduitDAO.insertCaracteristiqueProduit(cp1);
        CaracteristiqueProduitDAO.insertCaracteristiqueProduit(cp2);
    }

}
