package control.services.regular;

import business.data.CaracteristiqueProduitDAO;
import business.data.TypeCaracteristiqueProduitDAO;
import business.model.CaracteristiqueProduit;
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
@RequestMapping("/regular/caracteristiquesProduit")
public class CaracteristiquesProduitsRegulier {

    @RequestMapping(value="/create.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createType(@RequestBody CaracteristiqueProduit caracteristiqueProduit){
        return persistService(_CREATE_ONE,caracteristiqueProduit);
    }

    @RequestMapping(value="/edit.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editType(@RequestBody CaracteristiqueProduit caracteristiqueProduit){
        return persistService(_UPDATE_ONE,caracteristiqueProduit);
    }

    @RequestMapping(value="/delete.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteType(@RequestBody CaracteristiqueProduit caracteristiqueProduit){
        return persistService(_DELETE_ONE,caracteristiqueProduit);
    }


    public ResponseEntity<String> persistService(int style, CaracteristiqueProduit caracteristiqueProduit){

        if(!isValidStyle(style)){
            throw new IllegalArgumentException("Illegal 'style' argument value");
        }

        if((style==_CREATE_ONE || style==_UPDATE_ONE) &&
                (caracteristiqueProduit.getLibelle()==null||caracteristiqueProduit.getDescription()==null || caracteristiqueProduit.getType()==null
                        ?true:caracteristiqueProduit.getLibelle().trim().equals("") || caracteristiqueProduit.getDescription().trim().equals(""))){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        if((style==_UPDATE_ONE || style==_DELETE_ONE)&& (caracteristiqueProduit.getId()<1)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        CaracteristiqueProduit caracteristiqueById = style==_UPDATE_ONE || style==_DELETE_ONE ? CaracteristiqueProduitDAO.getCaracteristiqueProduit(caracteristiqueProduit.getId()) :null;

        if((style==_UPDATE_ONE || style==_DELETE_ONE) && (caracteristiqueById==null)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"resource not found\"}");
        }



        CaracteristiqueProduit targetCaracteristique= new CaracteristiqueProduit();
        System.out.println(caracteristiqueProduit.getType());
        if(style==_CREATE_ONE){
            System.out.println("<=========>"+TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(caracteristiqueProduit.getType().getId()));
            targetCaracteristique.setLibelle(caracteristiqueProduit.getLibelle());
            targetCaracteristique.setDescription(caracteristiqueProduit.getDescription());
            targetCaracteristique.setType(TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(caracteristiqueProduit.getType().getId()));
        }
        else if(style==_UPDATE_ONE){
            targetCaracteristique = caracteristiqueById;
            targetCaracteristique.setLibelle(caracteristiqueProduit.getLibelle());
            targetCaracteristique.setDescription(caracteristiqueProduit.getDescription());
            targetCaracteristique.setType(TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(caracteristiqueProduit.getType().getId()));

        }else targetCaracteristique = caracteristiqueById;

        String result = style==_CREATE_ONE?CaracteristiqueProduitDAO.insertCaracteristiqueProduit(targetCaracteristique):(style==_UPDATE_ONE?CaracteristiqueProduitDAO.updateCaracteristiqueProduit(targetCaracteristique):(style==_DELETE_ONE?CaracteristiqueProduitDAO.deleteCaracteristiqueProduit(targetCaracteristique.getId())+"":null));

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
