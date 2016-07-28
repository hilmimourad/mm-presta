package control.services.security;

import business.model.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utilities.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mourad on 7/27/2016.
 */
public class AdminAuthentificationInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Cookie auth = null;
        for(Cookie c :httpServletRequest.getCookies()){
            if(c.getName().equals(JWTService.getHeaderName())){
                auth = c;
                break;
            }
        }


        String[] urlParts = httpServletRequest.getContextPath().split("[/]");


        int i=0;
        for(String s :urlParts){
            System.out.println(i+"===="+s);
            i++;
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
