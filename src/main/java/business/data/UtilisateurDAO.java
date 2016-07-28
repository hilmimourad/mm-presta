package business.data;


import business.model.Utilisateur;
import utilities.ExceptionHandler;

import java.util.List;


/**
 * <h1>Manager des entités Utilisateur dans la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour gérer les entités de type Utilisateur
 * dans la base de données</p>
 *
 *<b>Note:</b> Cette classes est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Salim Dahiry, Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class UtilisateurDAO {

    /**
     * Cette méthode permet d'inserer un objet Utilisateur dans la base de données.
     * @param U business.model.Utilisateur l'objet à insérer
     * @return   l'ID de l'objet inséré en cas de succès, null en cas d'erreur.
     * @see String
     */
	public static String insertUtilisateur(Utilisateur U){
		try{
			DAO.getEntityManager().getTransaction().begin();
			DAO.getEntityManager().persist(U);
			DAO.getEntityManager().getTransaction().commit();
			return U.getUsername()+"";
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while inserting Utilisateur",e);
			return null;
		}
	}

    /**
     * Cette méthode permet de modifier un objet Utilisateur dans la base de données.
     * @param U business.model.TypeCaracteristiqueProduit l'objet à modifier
     * @return   l'ID de l'objet mis à jour en cas de succès, null en cas d'erreur.
     * @see String
     */
    public static String updateUtilisateur (Utilisateur U){
        try {
            DAO.getEntityManager().getTransaction().begin();
            Utilisateur UU = DAO.getEntityManager().find(Utilisateur.class,U.getUsername());
            UU.setPassword(U.getPassword());
            UU.setRole(U.getRole());
            DAO.getEntityManager().persist(UU);
            DAO.getEntityManager().getTransaction().commit();
            return UU.getUsername();
        } catch (Exception e) {
            DAO.getEntityManager().getTransaction().rollback();
            ExceptionHandler.handleException("Exception while updating Utilisateur",e);
            return null;		}
    }

    /**
     * Cette méthode permet de supprimer un objet Utilisateur de la base de données.
     * @param Username String l'id de l'objet à supprimer
     * @return   true en cas de succès, false en cas d'erreur
     * @see boolean
     */
	public static boolean deleteUtilisateur(String Username){
		try{
			DAO.getEntityManager().getTransaction().begin();
			Utilisateur U = DAO.getEntityManager().find(Utilisateur.class, Username);
			if(U!=null){
			  DAO.getEntityManager().remove(DAO.getEntityManager().contains(U)?U:DAO.getEntityManager().merge(U));
			}
			DAO.getEntityManager().getTransaction().commit();
			return true;
		}catch(Exception e){
			DAO.getEntityManager().getTransaction().rollback();
			ExceptionHandler.handleException("Exception while deleting User",e);
			return false;
		}
		
	}

    /**
     * Cette méthode permet de récupérer un objet Utilisateur de la base de données par son ID.
     * @param Username String l'ID de l'objet à récupérer
     * @return   l'objet à récupérer, null en cas d'erreur
     */
	public static Utilisateur getUtilisateur(String Username){
		try {
			return DAO.getEntityManager().find(Utilisateur.class,Username);
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching Utilisateur by username",e);
		    return null;
		}
	}

    /**
     * Cette méthode permet de récupérer tous les objets Utilisateur de la base de données.
     * @return   List des tous les objets, null en cas d'erreur.
     * @see List
     */
	public static List<Utilisateur> getAll(){
		try {
			return DAO.getEntityManager().createQuery("select U from Utilisateur U").getResultList();
		} catch (Exception e) {
		    ExceptionHandler.handleException("Exception while fetching all Utilisateur",e);
		    return null;
	    }
	}

}
