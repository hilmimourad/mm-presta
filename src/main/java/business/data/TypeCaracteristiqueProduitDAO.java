package business.data;


import business.model.TypeCaracteristiqueProduit;
import utilities.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * <h1>Manager des entités TypeCaracteristiqueProduit dans la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour gérer les entités de type TypeCaracteristiqueProduit
 * dans la base de données</p>
 *
 *<b>Note:</b> Cette classes est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Salim Dahiry, Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class TypeCaracteristiqueProduitDAO {

    /**
     * Cette méthode permet d'inserer un objet TypeCaracteristiqueProduit dans la base de données.
     * @param TCP business.model.TypeCaracteristiqueProduit l'objet à insérer
     * @return   l'ID de l'objet inséré en cas de succès, null en cas d'erreur.
     * @see String
     */
	public synchronized static String insertTypeCaracteristiqueProduit(TypeCaracteristiqueProduit TCP){
		try{
			DAO.getEntityManager().getTransaction().begin();
			DAO.getEntityManager().persist(TCP);
			DAO.getEntityManager().getTransaction().commit();
			return TCP.getId()+"";
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while inserting TypeCaracteristiqueProduit",e);
			return null;
		}
	}


    /**
     * Cette méthode permet de modifier un objet TypeCaracteristiqueProduit dans la base de données.
     * @param TCP business.model.TypeCaracteristiqueProduit l'objet à modifier
     * @return   l'ID de l'objet mis à jour en cas de succès, null en cas d'erreur.
     * @see String
     */
    public static String updateTypeCaracteristiqueProduit (TypeCaracteristiqueProduit TCP){
        String result = null;
        try {
            DAO.getEntityManager().getTransaction().begin();
            TypeCaracteristiqueProduit TCPU = DAO.getEntityManager().find(TypeCaracteristiqueProduit.class,TCP.getId());
            if(TCPU != null){
                 TCPU.setLibelle(TCP.getLibelle());
                 DAO.getEntityManager().persist(TCPU);
                 result = TCPU.getId()+"";
            }
            DAO.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            result = null;
            DAO.getEntityManager().getTransaction().rollback();
            ExceptionHandler.handleException("Exception while updating TypeCaracteristiqueProduit",e);
        }
        return result;
    }

    /**
     * Cette méthode permet de supprimer un objet TypeCaracteristiqueProduit de la base de données.
     * @param id long l'ID de l'objet à supprimer
     * @return   true en cas de succès, false en cas d'erreur
     * @see boolean
     */
	public synchronized static boolean deleteTypeCaracteristiqueProduit(long id){
		try{
			DAO.getEntityManager().getTransaction().begin();
			TypeCaracteristiqueProduit TCP = DAO.getEntityManager().find(TypeCaracteristiqueProduit.class, id);
			if(TCP!=null){
			  DAO.getEntityManager().remove(DAO.getEntityManager().contains(TCP)?TCP:DAO.getEntityManager().merge(TCP));
			}
			DAO.getEntityManager().getTransaction().commit();
			return true;
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while deleting TypeCaracteristiqueProduit",e);
			return false;
		}
		
	}

    /**
     * Cette méthode permet de récupérer un objet TypeCaracteristiqueProduit de la base de données par son ID.
     * @param id long l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static TypeCaracteristiqueProduit getTypeCaracteristiqueProduit(long id){
		try {
			return DAO.getEntityManager().find(TypeCaracteristiqueProduit.class,id);
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching TypeCaracteristiqueProduit by ID",e);
		    return null;
		}
	}


    /**
     * Cette méthode permet de récupérer tous les objets TypeCaracteristiqueProduit de la base de données.
     * @return   List des tous les objets, null en cas d'erreur.
     * @see List
     */
	public synchronized static List<TypeCaracteristiqueProduit> getAll(){
		try {
			return DAO.getEntityManager().createQuery("select TCP from TypeCaracteristiqueProduit TCP").getResultList();
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching all TypeCaracteristiqueProduit",e);
		    return null;
	    }
	}

    /**
     * Cette méthode permet de récupérer un objet TypeCaracteristiqueProduit de la base de données par son nom.
     * @param libelle String l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static TypeCaracteristiqueProduit find(String libelle){
		try {
			String x = libelle.toLowerCase();
			Query query =DAO.getEntityManager().createQuery("select TCP from TypeCaracteristiqueProduit TCP where lower(TCP.libelle) lIKE :x",TypeCaracteristiqueProduit.class);
			query.setParameter("x", x);
			return (TypeCaracteristiqueProduit) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching TypeCaracteristiqueProduit by name",e);
			return null;
    	}
	}
}
