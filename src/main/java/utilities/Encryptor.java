package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <h1>Chiffrement des String</h1>
 * <p>Cette classe permet de chiffrer des String en utilisant l'algorithme <b>MD5</b>
 * </p>
 * <b>Note:</b> Cette classe est <b>Abstract</b>, donc ne peut jamais être instancié. En revenche toutes les méthodes
 * sont statique.
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-17
 */
public abstract class Encryptor {

    /**
     * <p>Permet de chiffrer un String avec l'algorithme <b>MD5</b></p>
     *
     * @param word String l'information à chiffrer
     * @return   l'information chiffrée
     * @see String
     */
    public static String encrypte(String word){
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(word.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(byte b : bytes){
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            ExceptionHandler.handleException("Exception while encrypting",e);
        }

        return result;

    }
}
