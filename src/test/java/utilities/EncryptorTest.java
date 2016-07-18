package utilities;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mourad on 7/18/2016.
 */
public class EncryptorTest {

    @Test
    public void test(){
        /**SETTING UP**/
        String plain = "word to cipher";

        /**TEST CIPHERING**/
        String cipher = Encryptor.encrypte(plain);
        String cipher2 = Encryptor.encrypte(plain);
        String cipher3 = Encryptor.encrypte(plain+"nope");

        Assert.assertNotNull(cipher);
        Assert.assertNotNull(cipher2);

        /**TEST EQUALS**/
        Assert.assertEquals(cipher,cipher2);
        Assert.assertNotEquals(cipher,cipher3);

    }
}
