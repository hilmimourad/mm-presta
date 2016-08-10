package business.data;


import business.model.Produit;
import utilities.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * <h1>Manager des entités Produit dans la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour gérer les entités de type Produit
 * dans la base de données</p>
 *
 *<b>Note:</b> Cette classes est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Salim Dahiry, Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class ProduitDAO {

    /**
     * Cette méthode permet d'inserer un objet Produit dans la base de données.
     * @param P business.model.Produit l'objet à insérer
     * @return   l'ID de l'objet inséré en cas de succès, null en cas d'erreur.
     * @see String
     */
    public synchronized static String insertProduit(Produit P){
		try{
			DAO.getEntityManager().getTransaction().begin();
			DAO.getEntityManager().persist(P);
			DAO.getEntityManager().getTransaction().commit();
			return P.getId()+"";
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while inserting Produit",e);
			return null;
		}
	}


    /**
     * Cette méthode permet de modifier un objet Produit dans la base de données.
     * @param P business.model.Produit l'objet à modifier
     * @return   l'ID de l'objet mis à jour en cas de succès, null en cas d'erreur.
     * @see String
     */
	public synchronized static String updateProduit (Produit P){
		try {
			DAO.getEntityManager().getTransaction().begin();
			Produit PU = DAO.getEntityManager().find(Produit.class,P.getId());
            PU.setLibelle(P.getLibelle());
            PU.setFamille(P.getFamille());
            PU.setDescription(P.getDescription());
            PU.setListAssociationProduitCaracteristique(P.getListAssociationProduitCaracteristique());
            DAO.getEntityManager().persist(PU);
            DAO.getEntityManager().getTransaction().commit();
            return PU.getId()+"";
		} catch (Exception e) {
			DAO.getEntityManager().getTransaction().rollback();
	        ExceptionHandler.handleException("Exception while updating Produit",e);
	        return null;		}
	}

    /**
     * Cette méthode permet de supprimer un objet Produit de la base de données.
     * @param id long l'ID de l'objet à supprimer
     * @return   true en cas de succès, false en cas d'erreur
     * @see boolean
     */
	public synchronized static boolean deleteProduit(long id){
		try{
			DAO.getEntityManager().getTransaction().begin();
			Produit P = DAO.getEntityManager().find(Produit.class, id);
			if(P!=null){
			  DAO.getEntityManager().remove(DAO.getEntityManager().contains(P)?P:DAO.getEntityManager().merge(P));
			}
			DAO.getEntityManager().getTransaction().commit();
			return true;
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while deleting Produit",e);
			return false;
		}
		
	}

    /**
     * Cette méthode permet de récupérer un objet Produit de la base de données par son ID.
     * @param id long l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static Produit getProduit(long id){
		try {
			return DAO.getEntityManager().find(Produit.class,id);
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching Produit By id",e);
		    return null;
		}
	}

    /**
     * Cette méthode permet de récupérer tous les objets Produit de la base de données.
     * @return   List des tous les objets, null en cas d'erreur.
     * @see List
     */
	public synchronized static List<Produit> getAll(){
		try {
			return DAO.getEntityManager().createQuery("select P from Produit P").getResultList();
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching all Produit",e);
		    return null;
	    }
	}

	/**
	 * Cette méthode permet de récupérer tous les objets Produit d'une famille de la base de données.
	 * @param   famille l'ID de la famille en question
	 * @return   List des tous les objets, null en cas d'erreur.
	 * @see List
	 */
	public synchronized static List<Produit> getAllOfFamille(String famille){
		try {
			Query query = DAO.getEntityManager().createQuery("select P from Produit P where P.famille.id = :id",Produit.class);
			query.setParameter("id",Long.parseLong(famille));
			return query.getResultList();
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching all Produit of Famille",e);
			return null;
		}
	}


    /**
     * Cette méthode permet de récupérer les objets Produit de la base de données par nom.
     * @return   List des objets par nom, null en cas d'erreur.
     * @see List
     */
    public synchronized static List<Produit> find(String libelle){
    	
			try {
				String x = libelle.toLowerCase();
				Query query =DAO.getEntityManager().createQuery("select P from Produit P where lower(P.libelle) lIKE :x",Produit.class);
				query.setParameter("x", "%"+x+"%");
				return query.getResultList();
			}catch(NoResultException e){
				return null;
			} catch (Exception e) {
				ExceptionHandler.handleException("Exception while fetching list of Produit by name",e);
				return null;
	    	}
		}
	
    }



