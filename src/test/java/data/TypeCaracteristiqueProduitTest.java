package data;

import business.data.TypeCaracteristiqueProduitDAO;
import business.model.TypeCaracteristiqueProduit;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mourad on 7/18/2016.
 */
public class TypeCaracteristiqueProduitTest {

    @Test
    public void test(){

        TypeCaracteristiqueProduit tpc = new TypeCaracteristiqueProduit();
        tpc.setLibelle("Mock TypeCaracteristiqueProduit");

        TypeCaracteristiqueProduit tpc2 = new TypeCaracteristiqueProduit();
        tpc2.setId(-1);
        tpc2.setLibelle("Fake TypeCaracteristiqueProduit");


        /**TEST INSERTING**/

        String id = TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tpc);
        Assert.assertNotNull(id);


        /**TEST UPDATING**/
        tpc.setLibelle("Mock TypeCaracteristiqueProduit Updated");
        Assert.assertNotNull(TypeCaracteristiqueProduitDAO.updateTypeCaracteristiqueProduit(tpc));
        Assert.assertNull(TypeCaracteristiqueProduitDAO.updateTypeCaracteristiqueProduit(tpc2));

        /**TEST FETCHING**/
        tpc2 = TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(tpc.getId());
        Assert.assertNotNull(tpc2);
        Assert.assertEquals(tpc2.getLibelle(),tpc.getLibelle());

        tpc2 = TypeCaracteristiqueProduitDAO.find(tpc.getLibelle());
        Assert.assertNotNull(tpc2);
        tpc2 = TypeCaracteristiqueProduitDAO.find(tpc.getLibelle()+"nope");
        Assert.assertNull(tpc2);

        Assert.assertNotNull(TypeCaracteristiqueProduitDAO.getAll());
        Assert.assertTrue(TypeCaracteristiqueProduitDAO.getAll().size()>0);

        /**TEST DELETING**/
        Assert.assertTrue(TypeCaracteristiqueProduitDAO.deleteTypeCaracteristiqueProduit(tpc.getId()));

    }

    @Test
    public void initDatabase(){

        TypeCaracteristiqueProduit tc1 = new TypeCaracteristiqueProduit();
        tc1.setLibelle("type1");
        TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tc1);

        TypeCaracteristiqueProduit tc2 = new TypeCaracteristiqueProduit();
        tc2.setLibelle("type2");
        TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tc2);

        TypeCaracteristiqueProduit tc3 = new TypeCaracteristiqueProduit();
        tc3.setLibelle("type3");
        TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(tc3);

    }
}
