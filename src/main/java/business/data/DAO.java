package business.data;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * <h1>Creation de EntityManager pour se connecter à la base de données</h1>
 * <p>Cette classe offre des fonctionnalités pour créer une instance unique de type EntityManager</p>
 *
 *<b>Note:</b> Cette classe est abstraite donc ne peut jamais être instancié. En revenche toutes les méthodes sont
 * statiques.
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-04
 */
public abstract class DAO {

    /**
     * Constante representant le nom d'unité de persistence à utiliser (déclarée dans le fichier persistence.xml)
     **/
	private static final String _PERSISTENCE_UNIT = "MarsaMaroc";

	/**
	 * Instance unique de type EntityManager
	 **/
	private static EntityManager _EM = null;

	/**
	 * Cette méthode permet d'initialiser et récupérer l'instance unique de type EntityManager.
	 * @return   instance EntityManager
	 * @see EntityManager
	 */
	public synchronized static EntityManager getEntityManager(){
		if(_EM!=null) return _EM;
		_EM = Persistence.createEntityManagerFactory(_PERSISTENCE_UNIT).createEntityManager();
		return _EM;
	}
}
