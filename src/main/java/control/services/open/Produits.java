package control.services.open;

import business.data.ProduitDAO;
import business.model.Produit;
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
 * <h1>Controleur Spring Rest pour gérer les produits ou services(Version Open au public sans Authentification)</h1>
 * <p>Ce controleur offre un ensemble de fonctionnalités pour gérer les produits ou services</p>
 * <b>Nécessite authentification ?</b> NON
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-21
 *
 */
@RestController
@RequestMapping("/open/produits")
public class Produits {

    /**
     * Cette méthode permet de récuperer touts les produits
     * @return   réponse Json
     */
    @RequestMapping(value = "/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        return getService(_ALL,null);
    }

    /**
     * Cette méthode permet de récuperer tous les produits d'une famille
     * @return   réponse Json
     */
    @RequestMapping(value = "/{famille}/famille/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllSuper(@PathVariable("famille")String famille){
        return getService(_ALL_OF_FAMILLE,famille);
    }

    /**
     * Cette méthode permet de récuperer un produit par ID
     * @return   réponse Json
     */
    @RequestMapping(value = "/{id}/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable("id")String id){
        return getService(_ONE_BY_ID,id);
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
        List<Produit> listProduits = null;
        Produit p = null;
        ObjectMapper om = new ObjectMapper();
        try{
            if(style == _ALL || style==_ALL_OF_FAMILLE){
                listProduits = style == _ALL ? ProduitDAO.getAll() : ProduitDAO.getAllOfFamille(value);
            }
            else if(style == _ONE_BY_ID){
                p = ProduitDAO.getProduit(Long.parseLong(value));
            }
            else{
                throw new IllegalArgumentException();
            }
            if((listProduits==null && (style==_ALL || style==_ALL_OF_FAMILLE))){
                HashMap<String,String> error = new HashMap<String, String>();
                error.put("reason","database exception");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(om.writeValueAsString(error));
            }
            if(p==null&& (style==_ONE_BY_ID)){
                return ResponseEntity.ok(null);
            }
            return ResponseEntity.ok(om.writeValueAsString(style==_ALL || style == _ALL_OF_FAMILLE ? listProduits:p));
        }catch (Exception e){
            ExceptionHandler.handleException("unkown exception at open/Produits::getService",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"reason\":\"unkown exception\"}exception");
        }
    }


    private static final int _ALL = 1;
    private static final int _ALL_OF_FAMILLE = 2;
    private static final int _ONE_BY_ID=3;


}
