package utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * Created by mourad on 7/19/2016.
 */
public abstract class JWTService {

    private static final String _KEY = "MyKeyNeedToBeConfiguredAndCiphered";

    public static String generateToken(String subject) throws NullPointerException{

        if(subject==null) throw new NullPointerException();
        String compactJws = Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, getKey())
                .compact();
        return compactJws;
    }

    public static String parseToken(String token)throws SignatureException{
       return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody().getSubject();
    }

    private static String getKey(){
        return _KEY;
    }

    public static final String getHeaderName(){return "mmpresta-authtoken";}
}
