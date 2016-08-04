package control.services.open;

import business.data.FamillePrestationDAO;
import business.model.FamillePrestation;
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
import java.util.Map;


/**
 * <h1>Controleur Spring Rest pour gérer les familles des préstations(Version Open au public sans Authentification)</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les familles de préstations</p>
 * <b>Nécessite authentification ?</b> NON
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-19
 *
 */
@RestController
@RequestMapping("/open/famillesPrestation")
public class FamillesPrestation {

    /**
     * Cette méthode permet de récuperer toutes les familles
     * @return   réponse Json
     */
    @RequestMapping(value = "/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        return getService(_ALL,null);
    }

    /**
     * Cette méthode permet de récuperer toutes la familles mères seulements
     * @return   réponse Json
     */
    @RequestMapping(value = "/meres/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllSuper(){
        return getService(_SUPERS,null);
    }

    /**
     * Cette méthode permet de récuperer une famille par ID
     * @return   réponse Json
     */
    @RequestMapping(value = "/{id}/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable("id")String id){
        return getService(_ONE_BY_ID,id);
    }

    /**
     * Cette méthode permet de récuperer une famille par Code
     * @return   réponse Json
     */
    @RequestMapping(value = "/{code}/code/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOneByCode(@PathVariable("code")String code){
        return getService(_ONE_BY_CODE,code);
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
        List<FamillePrestation> listFamilles = null;
        FamillePrestation fp = null;
        ObjectMapper om = new ObjectMapper();
        try{
            if(style == _ALL || style==_SUPERS){
                listFamilles = style == _ALL ? FamillePrestationDAO.getAll() : FamillePrestationDAO.getAllSuper();
            }
            else if(style==_ONE_BY_CODE || style == _ONE_BY_ID){
                fp  = style==_ONE_BY_ID ? FamillePrestationDAO.getFamillePrestation(Long.parseLong(value)):FamillePrestationDAO.find(value);
            }
            else{
                throw new IllegalArgumentException();
            }
            if((listFamilles==null && (style==_ALL || style==_SUPERS))){
                HashMap<String,String> error = new HashMap<String, String>();
                error.put("reason","database exception");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(om.writeValueAsString(error));
            }
            if(fp==null&& (style==_ONE_BY_CODE || style==_ONE_BY_ID)){
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\"error\":\"no resource\"}");
            }
            if(style==_ALL || style == _SUPERS){
                return ResponseEntity.ok(om.writeValueAsString(listFamilles));
            }else {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("famille",fp);
                map.put("sousFamilles",fp.getListeSousFamille());
                return ResponseEntity.ok(om.writeValueAsString(map));
            }

        }catch (Exception e){
            ExceptionHandler.handleException("unkown exception at open/FamillesPrestation::getService",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"unkown exception\"}");
        }
    }


    private static final int _ALL = 1;
    private static final int _SUPERS = 2;
    private static final int _ONE_BY_ID=3;
    private static final int _ONE_BY_CODE=4;


}
