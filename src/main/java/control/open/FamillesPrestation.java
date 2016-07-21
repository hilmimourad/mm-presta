package control.open;

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


/**
 * Created by mourad on 7/21/2016.
 */
@RestController
@RequestMapping("/open/famillesPrestation")
public class FamillesPrestation {



    @RequestMapping(value = "/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAll(){
        return getService(_ALL,null);
    }

    @RequestMapping(value = "/meres/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllSuper(){
        return getService(_SUPERS,null);
    }

    @RequestMapping(value = "/{id}/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOne(@PathVariable("id")String id){
        return getService(_ONE_BY_ID,id);
    }

    @RequestMapping(value = "/{code}/code/action.do",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOneByCode(@PathVariable("code")String code){
        return getService(_ONE_BY_CODE,code);
    }






    /**UTILITY Methodes**/
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
                HashMap<String,String> error = new HashMap<String, String>();
                error.put("reason","no data found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(om.writeValueAsString(error));
            }

            return ResponseEntity.ok(om.writeValueAsString(style==_ALL || style == _SUPERS ? listFamilles:fp));
        }catch (Exception e){
            ExceptionHandler.handleException("unkown exception at open/FamillesPrestation::getService",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"reason\":\"unkown exception\"}exception");
        }
    }

    private static final int _ALL = 1;
    private static final int _SUPERS = 2;
    private static final int _ONE_BY_ID=3;
    private static final int _ONE_BY_CODE=4;


}
