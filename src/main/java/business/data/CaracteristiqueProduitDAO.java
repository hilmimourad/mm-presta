package business.data;

import business.model.CaracteristiqueProduit;
import utilities.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * <h1>Manager des entités CaracteristiqueProduit dans la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour gérer les entités de type CaracteristiqueProduit
 * dans la base de données</p>
 *
 *<b>Note:</b> Cette classes est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Salim Dahiry,Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class CaracteristiqueProduitDAO {

    /**
     * Cette méthode permet d'inserer un objet CaracteristiqueProduit dans la base de données.
     * @param CP business.model.CaracteristiqueProduit l'objet à insérer
     * @return   l'ID de l'objet inséré en cas de succès, null en cas d'erreur.
     * @see String
     */
	public synchronized static String insertCaracteristiqueProduit(CaracteristiqueProduit CP){
		try{
			DAO.getEntityManager().getTransaction().begin();
			DAO.getEntityManager().persist(CP);
			DAO.getEntityManager().getTransaction().commit();
			return CP.getId()+"";
		}catch(Exception e){
			if(DAO.getEntityManager().getTransaction().isActive()) DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while inserting CaracteristiqueProduit",e);
			return null;
		}
	}


    /**
     * Cette méthode permet de modifier un objet CaracteristiqueProduit dans la base de données.
     * @param CP business.model.CaracteristiqueProduit l'objet à modifier
     * @return   l'ID de l'objet mis à jour en cas de succès, null en cas d'erreur.
     * @see String
     */
    public static String updateCaracteristiqueProduit (CaracteristiqueProduit CP){
        try {
            DAO.getEntityManager().getTransaction().begin();
            CaracteristiqueProduit CPU = DAO.getEntityManager().find(CaracteristiqueProduit.class,CP.getId());
            CPU.setLibelle(CP.getLibelle());
            CPU.setDescription(CP.getDescription());
            CPU.setType(CP.getType());
            DAO.getEntityManager().persist(CPU);
            DAO.getEntityManager().getTransaction().commit();
            return CPU.getId()+"";
        } catch (Exception e) {
			if(DAO.getEntityManager().getTransaction().isActive()) DAO.getEntityManager().getTransaction().rollback();
            ExceptionHandler.handleException("Exception while updating CaracteristiqueProduit",e);
            return null;
        }
    }


    /**
     * Cette méthode permet de supprimer un objet CaracteristiqueProduit de la base de données.
     * @param id long l'ID de l'objet à supprimer
     * @return   true en cas de succès, false en cas d'erreur
     * @see boolean
     */
	public synchronized static boolean deleteCaracteristiqueProduit(long id){
		try{
			DAO.getEntityManager().getTransaction().begin();
			CaracteristiqueProduit CP = DAO.getEntityManager().find(CaracteristiqueProduit.class, id);
			if(CP!=null){
			  DAO.getEntityManager().remove(DAO.getEntityManager().contains(CP)?CP:DAO.getEntityManager().merge(CP));
			}
			DAO.getEntityManager().getTransaction().commit();
			return true;
		}catch(Exception e){
			if(DAO.getEntityManager().getTransaction().isActive()) DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while deleting CaracteristiqueProduit",e);
			return false;
		}
		
	}

    /**
     * Cette méthode permet de récupérer un objet CaracteristiqueProduit de la base de données par son ID.
     * @param id long l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static CaracteristiqueProduit getCaracteristiqueProduit(long id){
		try {
			return DAO.getEntityManager().find(CaracteristiqueProduit.class,id);
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching CaracteristiqueProduit by id",e);
		    return null;
		}
	}

    /**
     * Cette méthode permet de récupérer tous les objets CaracteristiqueProduit de la base de données.
     * @return   List des tous les objets, null en cas d'erreur.
     * @see List
     */
	public synchronized static List<CaracteristiqueProduit> getAll(){
		try {
			return DAO.getEntityManager().createQuery("select cp from CaracteristiqueProduit cp").getResultList();
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching all CaracteristiqueProduit",e);
		    return null;
	    }
	}

	/**
	 * Cette méthode permet de récupérer tous les objets CaracteristiqueProduit de la base de données d'un type de caracteristiques.
	 * @return   List des tous les objets, null en cas d'erreur.
	 * @see List
	 */
	public synchronized static List<CaracteristiqueProduit> getAllOfType(long id){
		try {
            Query query = DAO.getEntityManager().createQuery("select cp from CaracteristiqueProduit cp where cp.type.id = :id");
            query.setParameter("id",id);
			return query.getResultList();
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching all CaracteristiqueProduit of Type",e);
			return null;
		}
	}

    /**
     * Cette méthode permet de récupérer un objet de type CaracteristiqueProduit de la base de données par son nom.
     * @return   Objet à récupérer, null en cas de non existence ou erreur.
     */
	public synchronized static CaracteristiqueProduit find(String libelle){
		try {
			String x = libelle.toLowerCase();
			Query query =DAO.getEntityManager().createQuery("select cp from CaracteristiqueProduit cp where lower(cp.libelle) LIKE :x",CaracteristiqueProduit.class);
			query.setParameter("x", x);
			return (CaracteristiqueProduit) query.getSingleResult();
		}catch(NoResultException e){
			return null;
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching CaracteristiqueProduit by name",e);
			return null;
    	}
	}

}
