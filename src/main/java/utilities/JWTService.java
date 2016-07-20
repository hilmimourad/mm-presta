package utilities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

/**
 * Created by mourad on 7/19/2016.
 */
public abstract class JWTService {

    private static Key _KEY = null;

    public static String generateToken(String subject) throws NullPointerException{

        if(subject==null) throw new NullPointerException();
        Key key = getKey();

        String compactJws = Jwts.builder()
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return compactJws;
    }

    public static String parseToken(String token)throws SignatureException{
       return Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody().getSubject();
    }

    private static Key getKey(){
        if(_KEY==null) _KEY = MacProvider.generateKey();
        return _KEY;
    }

    public static final String getHeaderName(){return "auth-token";}
}
