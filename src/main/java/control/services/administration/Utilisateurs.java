package control.services.administration;

import business.data.UtilisateurDAO;
import business.model.AuthCredentials;
import business.model.Utilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utilities.Encryptor;
import utilities.ExceptionHandler;

import java.util.HashMap;
import java.util.List;


/**
 * <h1>Controleur Spring Rest pour gérer les utilisateurs</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les produits ou services</p>
 * <b>Nécessite authentification ?</b> OUI, niveau <b style="color:red">Administrateur</b>
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-27
 *
 */
@RestController
@RequestMapping("/admin/utilisateurs")
public class Utilisateurs {

    /**
     * Cette méthode permet de récuperer touts les utilisateurs
     * @return   réponse Json
     */
    @RequestMapping(value = "/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        return getService(_ALL,null);
    }


    /**
     * Cette méthode permet de récupérer un utilisateur par username
     * @return   réponse Json
     */
    @RequestMapping(value = "/{username}/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable("username")String id){
        return getService(_ONE_BY_ID,id);
    }

    /**
     * Cette méthode permet de créer un utilisateur
     * @return   réponse Json
     */
    @RequestMapping(value = "/action.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createUser(@RequestBody AuthCredentials credentials){
        return persistService(_NEW_ONE,credentials);
    }

    /**
     * Cette méthode permet de modifier le mot de passe d'un utilisateur
     * @return   réponse Json
     */
    @RequestMapping(value = "/password/action.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePassword(@RequestBody AuthCredentials credentials){
       return persistService(_UPDATE_PASSWORD,credentials);
    }

    /**
     * Cette méthode permet de modifier le mot de passe d'un utilisateur
     * @return   réponse Json
     */
    @RequestMapping(value = "/role/action.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRole(@RequestBody AuthCredentials credentials){
        return persistService(_UPDATE_ROLE,credentials);
    }

    /**
     * Cette méthode permet de modifier le mot de passe d'un utilisateur
     * @return   réponse Json
     */
    @RequestMapping(value = "/delete/action.do",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteUser(@RequestBody AuthCredentials credentials){
        return persistService(_DELETE_ONE,credentials);
    }



    /**
     * Méthode appelée par les méthodes du Controleur, utilisée pour éliminer la redonance du code. Inspiré du Design pattern : Template Method
     * @param   style entier permettant de spécifier le type de recherche.
     * @param   value variable facultative pour spécifier une valeur utilisée dans la recherche (par exemple l'ID de l'entité)
     * @return   ResponseEntity(String)
     * @throws IllegalArgumentException
     * @see IllegalArgumentException
     *
     * <b>Note:</b> Il faut utiliser les constantes fournies par la classe pour spécifier <b>style</b> sinon une exception sera lancé par
     * la méthode
     */
    private ResponseEntity<String> getService(int style,String value){
        List<Utilisateur> listUtilisateurs = null;
        Utilisateur u = null;
        ObjectMapper om = new ObjectMapper();
        try{
            if(style == _ALL){
                listUtilisateurs = UtilisateurDAO.getAll();
            }
            else if(style == _ONE_BY_ID){
                u = UtilisateurDAO.getUtilisateur(value);
            }
            else{
                throw new IllegalArgumentException();
            }
            if((listUtilisateurs==null && (style==_ALL))){
                HashMap<String,String> error = new HashMap<String, String>();
                error.put("reason","database exception");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(om.writeValueAsString(error));
            }
            if(u==null&& (style==_ONE_BY_ID)){
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.ok(om.writeValueAsString(style==_ALL? listUtilisateurs:u));
        }catch (Exception e){
            ExceptionHandler.handleException("unkown exception at admin/Utilisateurs::getService",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"reason\":\"unkown exception\"}exception");
        }
    }


    /**
     * Méthode appelée par les méthodes du Controleur responsables, utilisée pour éliminer la redonance du code. Inspiré du Design pattern : Template Method
     * @param   style entier permettant de spécifier le type de recherche.
     * @param   credentials variable  pour spécifier les informations d'un Utilisateur
     * @return   ResponseEntity(String)
     * @throws IllegalArgumentException
     * @see IllegalArgumentException
     *
     * <b>Note:</b> Il faut utiliser les constantes fournies par la classe pour spécifier <b>style</b> sinon une exception sera lancé par
     * la méthode
     */
    private ResponseEntity<String> persistService(int style,AuthCredentials credentials){

        if (style!=_NEW_ONE && style!=_UPDATE_ROLE && style!=_UPDATE_PASSWORD && style!= _DELETE_ONE){
            throw new IllegalArgumentException("Illegal 'style' argument value");
        }

        Utilisateur u = UtilisateurDAO.getUtilisateur(credentials.getUsername());

        if(u!=null && style==_NEW_ONE){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"existing resource\"}");
        }

        if(u==null && (style==_UPDATE_PASSWORD || style==_UPDATE_ROLE || style == _DELETE_ONE)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"no resource\"}");
        }

        if(style == _DELETE_ONE){
            if(UtilisateurDAO.deleteUtilisateur(credentials.getUsername())){
                return ResponseEntity.ok("");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"exception\"}");
            }
        }

        if(style==_NEW_ONE) u = new Utilisateur();
        u.setUsername(credentials.getUsername());
        if(style==_NEW_ONE || style==_UPDATE_PASSWORD) u.setPassword(Encryptor.encrypte(credentials.getPassword()));
        if(style==_NEW_ONE || style==_UPDATE_ROLE) u.setRole(credentials.getRole());

        String result;
        if(style==_NEW_ONE) result = UtilisateurDAO.insertUtilisateur(u);
        else result = UtilisateurDAO.updateUtilisateur(u);

        if(result!=null){
            return ResponseEntity.ok("");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"exception\"}");
        }

    }


    private static final int _ALL = 1;
    private static final int _ONE_BY_ID=3;

    private static final int _NEW_ONE=2;
    private static final int _UPDATE_PASSWORD=4;
    private static final int _UPDATE_ROLE=5;
    private static final int _DELETE_ONE=6;


}
