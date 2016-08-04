package control.services.regular;

import business.data.TypeCaracteristiqueProduitDAO;
import business.model.TypeCaracteristiqueProduit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mourad on 8/3/2016.
 */
@RestController
@RequestMapping("/regular/typesCaracteristiqueProduit")
public class TypesCaracteristiquesProduitsRegulier {

    @RequestMapping(value="/create.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createType(@RequestBody TypeCaracteristiqueProduit typeCaracteristiqueProduit){
        return persistService(_CREATE_ONE,typeCaracteristiqueProduit);
    }

    @RequestMapping(value="/edit.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editType(@RequestBody TypeCaracteristiqueProduit typeCaracteristiqueProduit){
        return persistService(_UPDATE_ONE,typeCaracteristiqueProduit);
    }

    @RequestMapping(value="/delete.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteType(@RequestBody TypeCaracteristiqueProduit typeCaracteristiqueProduit){
        return persistService(_DELETE_ONE,typeCaracteristiqueProduit);
    }


    public ResponseEntity<String> persistService(int style, TypeCaracteristiqueProduit typeCaracteristiqueProduit){

        if(!isValidStyle(style)){
            throw new IllegalArgumentException("Illegal 'style' argument value");
        }

        if((style==_CREATE_ONE || style==_UPDATE_ONE) &&
                (typeCaracteristiqueProduit.getLibelle()==null?true:typeCaracteristiqueProduit.getLibelle().trim().equals(""))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        if((style==_UPDATE_ONE || style==_DELETE_ONE)&& (typeCaracteristiqueProduit.getId()<1)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        TypeCaracteristiqueProduit typeById = style==_UPDATE_ONE || style==_DELETE_ONE ? TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(typeCaracteristiqueProduit.getId()) :null;
        TypeCaracteristiqueProduit typeByLibelle = style==_UPDATE_ONE || style==_CREATE_ONE ? TypeCaracteristiqueProduitDAO.find(typeCaracteristiqueProduit.getLibelle()) :null;

        if((style==_CREATE_ONE || style==_UPDATE_ONE)&& (typeByLibelle!=null)){
            if(style==_CREATE_ONE || (style==_UPDATE_ONE && typeByLibelle.getId()!=typeCaracteristiqueProduit.getId()))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"resource already exists\"}");
        }

        if((style==_UPDATE_ONE || style==_DELETE_ONE) && (typeById==null)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"resource not found\"}");
        }



        TypeCaracteristiqueProduit targetType= new TypeCaracteristiqueProduit();
        if(style==_CREATE_ONE){
            targetType.setLibelle(typeCaracteristiqueProduit.getLibelle());
        }
        else if(style==_UPDATE_ONE){
            targetType = typeById;
            targetType.setLibelle(typeCaracteristiqueProduit.getLibelle());
        }else targetType = typeById;

        String result = style==_CREATE_ONE?TypeCaracteristiqueProduitDAO.insertTypeCaracteristiqueProduit(targetType):(style==_UPDATE_ONE?TypeCaracteristiqueProduitDAO.updateTypeCaracteristiqueProduit(targetType):(style==_DELETE_ONE?TypeCaracteristiqueProduitDAO.deleteTypeCaracteristiqueProduit(targetType.getId())+"":null));

        if(result==null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"database error\"}");
        }

        return ResponseEntity.ok("");
    }


    private boolean isValidStyle(int style){
        return style==_CREATE_ONE || style==_UPDATE_ONE || style==_DELETE_ONE;
    }


    private static final int _CREATE_ONE  = 1;
    private static final int _UPDATE_ONE  = 2;
    private static final int _DELETE_ONE  = 3;
}
