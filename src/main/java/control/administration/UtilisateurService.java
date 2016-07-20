package control.administration;

import business.data.UtilisateurDAO;
import business.model.Utilisateur;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utilities.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * <h1>Controleur Spring Rest pour gérer les utilisateurs</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les utilisateurs ainsi que pour s'authentifier</p>
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-19
 *
 */
@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurService {

    /**
     * Cette méthode est accessible via "${serviceUrl}/utilisateurs", et permet récupérer la liste de tous les utilisateurs
     * @return   la liste de tous les utilisateurs le Media Type est <b>application/json</b>
     */
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public String getAll(HttpServletRequest request){
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(UtilisateurDAO.getAll());
        } catch (Exception e) {
            ExceptionHandler.handleException("Exception while parsing list of Utilisateur to JSON",e);
        }
        return null;
    }

    @RequestMapping(value = "/fetchById/{username}",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.GET)
    public String getUtilisateur(@PathVariable("username")String username){
        ObjectMapper om = new ObjectMapper();
        Utilisateur u = UtilisateurDAO.getUtilisateur(username.trim());
        if(u == null) return "No such User";
        try {
            return om.writeValueAsString(u);
        } catch (JsonProcessingException e) {
            ExceptionHandler.handleException("Exception while parsing list of Utilisateur to JSON",e);
        }
        return null;
    }



}
