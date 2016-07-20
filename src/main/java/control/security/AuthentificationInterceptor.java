package control.security;

import business.model.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utilities.ExceptionHandler;
import utilities.JWTService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>Intercepteur de requêtes pour vérifier l'authentification de l'Utilisateur</h1>
 * <p>Cette classe est un intercepteur de requêtes HTTP qui vérifie l'authentification de l'Utilisateur
 * avant d'autoriser l'accès au service demandé. L'authentification se fait en utilisant la méthode JWT (Json Web Token)</p>
 *
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-19
 *
 * @see org.springframework.web.servlet.HandlerInterceptor;
 */
public class AuthentificationInterceptor implements HandlerInterceptor{

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie auth = null;
        for(Cookie c :httpServletRequest.getCookies()){
            if(c.getName().equals(JWTService.getHeaderName())){
                System.out.println();
                System.out.println("Gotcha!!!!!!  "+c.getValue());
                System.out.println();
                auth = c;
                break;
            }
        }


        if (auth==null){
            httpServletResponse.setContentType("text/plain");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"no auth");
            return false;
        }
        String uj = null;
        try{
            uj = JWTService.parseToken(auth.getValue());
        }catch(SignatureException e){
            ExceptionHandler.handleException("Exception while parsing Token at TestInterceptor",e);
            httpServletResponse.setContentType("text/plain");
            httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"exception");
            return false;
        }

        ObjectMapper om = new ObjectMapper();
        Utilisateur u = om.readValue(uj,Utilisateur.class);

        if(u!=null){
            httpServletRequest.setAttribute("utilisateur",u);
            return true;
        }
        else {
            httpServletResponse.setContentType("text/plain");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return false;
    }


    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
