package business.data;

import business.model.FamillePrestation;
import utilities.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


/**
 * <h1>Manager des entités FamillePrestation dans la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour gérer les entités de type FamillePrestation
 * dans la base de données</p>
 *
 *<b>Note:</b> Cette classes est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Salim Dahiry, Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class FamillePrestationDAO {

    /**
     * Cette méthode permet d'inserer un objet FamillePrestation dans la base de données.
     * @param FP business.model.FamillePrestation l'objet à insérer
     * @return   l'ID de l'objet inséré en cas de succès, null en cas d'erreur.
     * @see String
     */
	public synchronized static String insertFamillePrestation(FamillePrestation FP){
		try{
			DAO.getEntityManager().getTransaction().begin();
			DAO.getEntityManager().persist(FP);
			DAO.getEntityManager().getTransaction().commit();
			return FP.getId()+"";
		}catch(Exception e){
			if(DAO.getEntityManager().getTransaction().isActive()){
				DAO.getEntityManager().getTransaction().rollback();
			}

			ExceptionHandler.handleException("Exception while inserting FamillePrestation",e);
			return null;
		}
	}


    /**
     * Cette méthode permet de modifier un objet FamillePrestation dans la base de données.
     * @param FP business.model.FamillePrestation l'objet à modifier
     * @return   l'ID de l'objet mis à jour en cas de succès, null en cas d'erreur.
     * @see String
     */
	public synchronized static String updateFamillePrestation (FamillePrestation FP){
		try {
			DAO.getEntityManager().getTransaction().begin();
			FamillePrestation FPU = DAO.getEntityManager().find(FamillePrestation.class,FP.getId());
			FPU.setCode(FP.getCode());
            FPU.setLibelle(FP.getLibelle());
            FPU.setFamillemere(FP.getFamillemere());
            FPU.setListeProduit(FP.getListeProduit());
            FPU.setListeSousFamille(FP.getListeSousFamille());
            DAO.getEntityManager().persist(FPU);
            DAO.getEntityManager().getTransaction().commit();
            return FPU.getId()+"";
		} catch (Exception e) {
			if(DAO.getEntityManager().getTransaction().isActive()){
				DAO.getEntityManager().getTransaction().rollback();
			}
	        ExceptionHandler.handleException("Exception while updating FamillePrestation",e);
	        return null;		}
	}


    /**
     * Cette méthode permet de supprimer un objet FamillePrestation de la base de données.
     * @param id long l'ID de l'objet à supprimer
     * @return   true en cas de succès, false en cas d'erreur
     * @see boolean
     */
	public synchronized static boolean deleteFamillePrestation(long id){
		try{
			DAO.getEntityManager().getTransaction().begin();
			FamillePrestation FP = DAO.getEntityManager().find(FamillePrestation.class, id);
			if(FP!=null){
			  if(FP.getFamillemere()!=null){
				  	FP.setFamillemere(null);
					DAO.getEntityManager().persist(FP);
					DAO.getEntityManager().getTransaction().commit();
					DAO.getEntityManager().getTransaction().begin();
				}
				if(DAO.getEntityManager().contains(FP))  DAO.getEntityManager().remove(FP);
			}
			DAO.getEntityManager().getTransaction().commit();
			return true;
		}catch(Exception e){
			if(DAO.getEntityManager().getTransaction().isActive()) DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while deleting FamillePrestation",e);
			return false;
		}
		
	}


    /**
     * Cette méthode permet de récupérer un objet FamillePrestation de la base de données par son ID.
     * @param id long l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static FamillePrestation getFamillePrestation(long id){
		try {
			return DAO.getEntityManager().find(FamillePrestation.class,id);
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching FamillePrestation by id",e);
		    return null;
		}
	}


    /**
     * Cette méthode permet de récupérer tous les objets FamillePrestation de la base de données.
     * @return   List des tous les objets, null en cas d'erreur.
     * @see List
     */
	public synchronized static List<FamillePrestation> getAll(){
		try {
			return DAO.getEntityManager().createQuery("select FP from FamillePrestation FP").getResultList();
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching all objects",e);
		    return null;
	    }
	}

	/**
	 * Cette méthode permet de récupérer tous les objets FamillePrestation de la base de données qui n'ont pas de famille mère.
	 * @return   List des tous les objets, null en cas d'erreur.
	 * @see List
	 */
	public synchronized static List<FamillePrestation> getAllSuper(){
		try {
			return DAO.getEntityManager().createQuery("select FP from FamillePrestation FP where FP.famillemere is null").getResultList();
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching all super objects",e);
			return null;
		}
	}

    /**
     * Cette méthode permet de récupérer un objet FamillePrestation de la base de données par son code.
     * @param Code String l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public synchronized static FamillePrestation find(String Code){
		try {
			String x = Code.toLowerCase();
			Query query =DAO.getEntityManager().createQuery("select FP from FamillePrestation FP where lower(FP.code) lIKE :x",FamillePrestation.class);
			query.setParameter("x", x);
			return (FamillePrestation)query.getSingleResult();
		}catch(NoResultException e){
			return null;
		} catch (Exception e) {
			ExceptionHandler.handleException("Exception while fetching FamillePrestation by code",e);
			return null;
    	}
	}
}


