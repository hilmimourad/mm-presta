package control.services.open;

import business.data.CaracteristiqueProduitDAO;
import business.model.CaracteristiqueProduit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import utilities.ExceptionHandler;

import java.util.HashMap;
import java.util.List;

/**
 * <h1>Controleur Spring Rest pour gérer les types des caracteristiques des produits(Version Open au public sans Authentification)</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les types des caracteristiques des produits </p>
 * <b>Nécessite authentification ?</b> NON
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-20
 *
 */
@RestController
@RequestMapping("/open/caracteristiquesProduit")
public class CaracteristiquesProduit {

    /**
     * Cette méthode permet de récuperer toutes les caracteristiques
     * @return   réponse Json
     */
    @RequestMapping(value = "/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        return getService(_ALL,null);
    }

    /**
     * Cette méthode permet de récuperer une caracteristique par ID
     * @return   réponse Json
     */
    @RequestMapping(value = "/{id}/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable("id")String id){
        return getService(_ONE_BY_ID,id);
    }

    /**
     * Cette méthode permet de récuperer une caracteristique par libelle
     * @return   réponse Json
     */
    @RequestMapping(value = "/{libelle}/libelle/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOneByLibelle(@PathVariable("libelle")String libelle){
        return getService(_ONE_BY_LIBELLE,libelle);
    }

    /**
     * Cette méthode permet de récuperer toutes les caracteristiques d'un Type
     * @return   réponse Json
     */
    @RequestMapping(value = "/{type}/type/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllOfType(@PathVariable("type")String type){
        return getService(_ALL_OF_TYPE,type);
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
        List<CaracteristiqueProduit> listCaracteristiques = null;
        CaracteristiqueProduit cp = null;
        ObjectMapper om = new ObjectMapper();
        try{
            if(style == _ALL || style==_ALL_OF_TYPE){
                listCaracteristiques = style==_ALL?CaracteristiqueProduitDAO.getAll():CaracteristiqueProduitDAO.getAllOfType(Long.parseLong(value));
            }
            else if(style == _ONE_BY_ID || style == _ONE_BY_LIBELLE){
                cp  = style==_ONE_BY_ID ? CaracteristiqueProduitDAO.getCaracteristiqueProduit(Long.parseLong(value)):CaracteristiqueProduitDAO.find(value);
            }
            else{
                throw new IllegalArgumentException();
            }
            if(listCaracteristiques==null && (style==_ALL || style==_ALL_OF_TYPE)){
                HashMap<String,String> error = new HashMap<String, String>();
                error.put("reason","database exception");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(om.writeValueAsString(error));
            }
            if(cp==null&& (style==_ONE_BY_LIBELLE || style==_ONE_BY_ID)){
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.ok(om.writeValueAsString(style==_ALL || style==_ALL_OF_TYPE? listCaracteristiques:cp));
        }catch (Exception e){
            ExceptionHandler.handleException("unkown exception at open/CaracteristiquesProduit::getService",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"reason\":\"unkown exception\"}exception");
        }
    }

    private static final int _ALL = 1;
    private static final int _ONE_BY_LIBELLE=2;
    private static final int _ONE_BY_ID=3;
    private static final int _ALL_OF_TYPE=4;
}
