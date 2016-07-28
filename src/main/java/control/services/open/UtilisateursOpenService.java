package control.services.open;

import business.data.UtilisateurDAO;
import business.model.AuthCredentials;
import business.model.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import control.services.security.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utilities.Encryptor;
import utilities.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <h1>Controleur Spring Rest pour gérer les utilisateurs(Version Open au public sans Authentification)</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les utilisateurs ainsi que pour s'authentifier</p>
 * <b>Nécessite authentification ?</b> NON
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-19
 *
 */
@RestController
@RequestMapping("/open/utilisateurs")
public class UtilisateursOpenService
{
    @RequestMapping(value = "/login.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity login(@RequestBody AuthCredentials credentials, HttpServletRequest request, HttpServletResponse response){
        if(credentials==null) return new ResponseEntity("no data", HttpStatus.NO_CONTENT);
        if(credentials.getUsername()==null || credentials.getPassword()==null) return new ResponseEntity("no data", HttpStatus.BAD_REQUEST);
        if(credentials.getUsername().trim().equals("") || credentials.getPassword().trim().equals("")) return new ResponseEntity("no data", HttpStatus.BAD_REQUEST);

        Utilisateur u = UtilisateurDAO.getUtilisateur(credentials.getUsername());

        if(u==null) return new ResponseEntity("no user", HttpStatus.UNAUTHORIZED);

        if(!u.getPassword().equals(Encryptor.encrypte(credentials.getPassword()))) return new ResponseEntity("no user", HttpStatus.UNAUTHORIZED);

        String uj  = null;
        ObjectMapper om = new ObjectMapper();
        try {
            uj = om.writeValueAsString(u);
        } catch (JsonProcessingException e) {
            ExceptionHandler.handleException("Error while parsing Utilisateur to Json at UtilisateurService::login",e);
            return new ResponseEntity("exception", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity re = new ResponseEntity("connected", HttpStatus.OK);
        Cookie c = new Cookie(JWTService.getHeaderName(),JWTService.generateToken(uj));
        c.setMaxAge(5*60*60);//5 hours
        response.addCookie(c);
        return re;
    }


    /**
     * Cette méthode permet de récuperer touts les roles possibles pour un utilisateur
     * @return   réponse Json
     */
    @RequestMapping(value = "/roles/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        ObjectMapper om  = new ObjectMapper();
        try {
            return ResponseEntity.ok(om.writeValueAsString(Utilisateur._ROLES));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("\"message\":\"Ops! Quelque chose a mal passé\"");
        }

    }


}
