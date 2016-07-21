package utilities;

/**
 * Created by mourad on 7/21/2016.
 */
public class MiscToolBox {

    /** BY: Quirin Schweigert [StackOverflow]**/
    public static String getRandomString(int length){
       String r = "";
       for(int i = 0; i < length; i++) {
                r += (char)(Math.random() * 26 + 97);
       }
       return r;
    }
}
