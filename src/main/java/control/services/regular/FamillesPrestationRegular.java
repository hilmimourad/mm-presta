package control.services.regular;

import business.data.FamillePrestationDAO;
import business.model.FamillePrestation;
import business.model.Produit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mourad on 8/1/2016.
 */

@RestController
@RequestMapping("/regular/famillesPrestation")
public class FamillesPrestationRegular {

    @RequestMapping(value = "/create.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOne(@RequestBody FamillePrestation famille){
        return persistService(_CREATE_ONE,famille);
    }

    @RequestMapping(value = "/update.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOne(@RequestBody FamillePrestation famille){
        return persistService(_UPDATE_ONE,famille);
    }

    @RequestMapping(value = "/delete.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteOne(@RequestBody FamillePrestation famille){
        return persistService(_DELETE_ONE,famille);
    }


    private ResponseEntity<String> persistService(int style,FamillePrestation famille){

        if(styleIsNotValid(style)){
            throw new IllegalArgumentException("Illegal 'style' argument value");
        }

        if(famille == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        if(((style==_CREATE_ONE || style==_UPDATE_ONE) && (famille.getLibelle()==null || famille.getCode()==null))
                || ((style==_DELETE_ONE || style==_UPDATE_ONE) && (famille.getId()==0))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        if((style==_CREATE_ONE || style==_UPDATE_ONE)&& (famille.getLibelle().trim().equals("")|| famille.getCode().trim().equals(""))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        FamillePrestation familleParIdOuCode = style==_CREATE_ONE || style==_UPDATE_ONE?FamillePrestationDAO.find(famille.getCode()):FamillePrestationDAO.getFamillePrestation(famille.getId());

        FamillePrestation familleParId = style==_UPDATE_ONE ? FamillePrestationDAO.getFamillePrestation(famille.getId()):null;

        if((style==_DELETE_ONE && familleParIdOuCode==null) || (style==_UPDATE_ONE && (familleParId==null))){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"not existing  resource\"}");
        }

        if(((style==_CREATE_ONE) && familleParIdOuCode!=null)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"existing resource\"}");
        }

        if(style==_UPDATE_ONE && familleParIdOuCode!=null){
            if(familleParIdOuCode.getId()!=familleParId.getId()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"existing resource\"}");
        }

        if(style==_UPDATE_ONE){
            famille.setListeSousFamille(familleParId.getListeSousFamille());
            famille.setListeProduit(familleParId.getListeProduit());
            famille.setFamillemere(familleParId.getFamillemere());
        }

        if(style==_CREATE_ONE){
            if(famille.getListeProduit()==null) famille.setListeProduit(new ArrayList<Produit>());
            if(famille.getListeSousFamille()==null) famille.setListeSousFamille(new ArrayList<FamillePrestation>());
            if(famille.getFamillemere()!=null){
                FamillePrestation familleMere = FamillePrestationDAO.getFamillePrestation(famille.getFamillemere().getId());
                if(familleMere==null){
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"insufficient information\"}");
                }
                famille.setFamillemere(familleMere);
                List<FamillePrestation> listFamilles = familleMere.getListeSousFamille();
                listFamilles.add(famille);
                familleMere.setListeSousFamille(listFamilles);
                famille = familleMere;
                style = _UPDATE_ONE;
            }
        }

        String result = style==_CREATE_ONE ? FamillePrestationDAO.insertFamillePrestation(famille):(style==_DELETE_ONE? (FamillePrestationDAO.deleteFamillePrestation(familleParIdOuCode.getId())?"ok":null):(style==_UPDATE_ONE?FamillePrestationDAO.updateFamillePrestation(famille):null));

        if(result != null) return ResponseEntity.status(HttpStatus.OK).body("");
        else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"database error\"}");
    }


    private boolean styleIsNotValid(int style){
        return style!=_CREATE_ONE && style!= _DELETE_ONE && style!= _UPDATE_ONE;
    }


    private static final int _CREATE_ONE = 1;
    private static final int _DELETE_ONE = 2;
    private static final int _UPDATE_ONE = 3;
}
