package data;

import business.data.UtilisateurDAO;
import business.model.Utilisateur;
import org.junit.Assert;
import org.junit.Test;
import utilities.Encryptor;

/**
 * Created by mourad on 7/18/2016.
 */
public class UtilisateurTest {

    @Test
    public void test(){

        /**SETTING UP**/
        Utilisateur u = new Utilisateur();
        u.setUsername("mockUser");
        u.setPassword(Encryptor.encrypte("mockPassword"));
        u.setRole(Utilisateur._ADMIN);

        Utilisateur u2 = new Utilisateur();
        u.setUsername("fakeUser");
        u.setPassword(Encryptor.encrypte("fakePassword"));
        u.setRole(Utilisateur._USER);

        /**TEST INSERTING**/
        Assert.assertNotNull(UtilisateurDAO.insertUtilisateur(u));

        /**TEST UPDATING**/
        u.setRole(Utilisateur._USER);
        Assert.assertNotNull(UtilisateurDAO.UpdateUtilisateur(u));

        Assert.assertNull(UtilisateurDAO.UpdateUtilisateur(u2));

        /**TEST FETCHING**/
        u2 = UtilisateurDAO.getUtilisateur(u.getUsername());
        Assert.assertNotNull(u2);
        Assert.assertEquals(u2.getPassword(),u.getPassword());
        Assert.assertEquals(u2.getUsername(),u.getUsername());
        Assert.assertEquals(u2.getRole(),u.getRole());

        u2 = UtilisateurDAO.getUtilisateur(u.getUsername()+"nope");
        Assert.assertNull(u2);

        Assert.assertTrue(UtilisateurDAO.getAll().size()>0);


        /**TEST DELETING**/
        Assert.assertTrue(UtilisateurDAO.deleteUtilisateur(u.getUsername()));



    }
}
