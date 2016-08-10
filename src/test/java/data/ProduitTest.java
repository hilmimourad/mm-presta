package data;


import business.data.CaracteristiqueProduitDAO;
import business.data.FamillePrestationDAO;
import business.data.ProduitDAO;
import business.data.TypeCaracteristiqueProduitDAO;
import business.model.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ProduitTest {
	


	
	    @Test
	    public void initDatabase(){
	        FamillePrestation fm1 = FamillePrestationDAO.getFamillePrestation(35);

			TypeCaracteristiqueProduit type = TypeCaracteristiqueProduitDAO.getTypeCaracteristiqueProduit(49);

			CaracteristiqueProduit c1 = CaracteristiqueProduitDAO.getCaracteristiqueProduit(83);
			CaracteristiqueProduit c2 = CaracteristiqueProduitDAO.getCaracteristiqueProduit(84);


			Produit p = new Produit();
			p.setLibelle("Produit1");
			p.setFamille(fm1);
			p.setDescription("Desc p1");



			AssociationProduitCaracteristique a1 = new AssociationProduitCaracteristique();
			a1.setProduit(p);
			a1.setCaracteristiqueProduit(c1);
			a1.setTarif(100);
			a1.setValeurDefaut(10);
			a1.setValeurMax(20);
			a1.setValeurMin(5);

			List<AssociationProduitCaracteristique> list = new ArrayList<AssociationProduitCaracteristique>();
			list.add(a1);

			p.setListAssociationProduitCaracteristique(list);

			ProduitDAO.insertProduit(p);

	    }

	@Test
	public void testDelete(){
		ProduitDAO.deleteProduit(85);
	}
	    
}    
	        
				
	


