package control.services.regular;

import business.data.ProduitDAO;
import business.model.Produit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mourad on 8/8/2016.
 */
@RestController
@RequestMapping("/regulier/produits")
public class ProduitsRegulier {


    @RequestMapping(value = "/create.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOne(@RequestBody Produit produit){
        return persistService(_CREATE_ONE,produit);
    }

    @RequestMapping(value = "/update.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateOne(@RequestBody Produit produit){
        return persistService(_UPDATE_ONE,produit);
    }

    @RequestMapping(value = "/delete.do",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteOne(@RequestBody Produit produit){
        return persistService(_DELETE_ONE,produit);
    }


    private ResponseEntity<String> persistService(int style,Produit produit){

        if(styleIsNotValid(style)){
            throw new IllegalArgumentException("Illegal 'style' argument value");
        }

        if(produit == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information\"}");
        }

        if(((style==_CREATE_ONE || style==_UPDATE_ONE) && (produit.getLibelle()==null || produit.getDescription()==null || produit.getListAssociationProduitCaracteristique()==null))
                || ((style==_DELETE_ONE || style==_UPDATE_ONE) && (produit.getId()==0))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information2\"}");
        }

        if((style==_CREATE_ONE || style==_UPDATE_ONE)&& (produit.getLibelle().trim().equals("")|| produit.getListAssociationProduitCaracteristique().isEmpty())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"insufficient information3\"}");
        }


        Produit produitById = style==_UPDATE_ONE || style==_DELETE_ONE ? ProduitDAO.getProduit(produit.getId()):null;

        if((style==_UPDATE_ONE || style==_DELETE_ONE) && (produitById==null)){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"not existing  resource\"}");
        }




        String result = style==_CREATE_ONE ? ProduitDAO.insertProduit(produit):(style==_DELETE_ONE? (ProduitDAO.deleteProduit(produitById.getId())?"ok":null):(style==_UPDATE_ONE?ProduitDAO.updateProduit(produit):null));

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
