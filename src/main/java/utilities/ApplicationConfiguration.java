package utilities;

import org.apache.commons.configuration.PropertiesConfiguration;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * <h1>Charge et gére la configuration de l'application</h1>
 * <p>Cette classe permet de lire et mettre à jour des paramètres
 * initiaux de l'application à partir d'un fichier <b>"mm-presta.properties"</b>
 * qui doit être crée dans le répertoire racine du projet (le répératoire "resources" pour un projet Maven).
 * <br>Les paramètres doivent être de type clé-valeur (ex: password:monPass), une paire par ligne.
 * </p>
 * <b>Note:</b> Cette classe est un <b>Singleton</b>, donc il faut utiliser
 * la méthode getInstance() pour récupérer l'objet
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-17
 */
public class ApplicationConfiguration {

    private HashMap<String,String> properties = new HashMap<String, String>();
    private String propFileName = "mm-presta.properties";

    /**
     * C'est un constructeur par défaut, il initialise l'application en lisant les paramètres
     * écrites dans le fichier.<br>
     * <b>Note:</b>Il est privé pour assurer le Design Pattern <b>Singleton</b>
     */
    private ApplicationConfiguration(){
        try {

            Properties props = new Properties();


            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                props.load(inputStream);
            } else {
                ExceptionHandler.handleException("Exception occurred in Application configuration to read from file",new FileNotFoundException(propFileName+" file not found in the class path"));
                return;
            }

            for (String key : props.stringPropertyNames()) {
                String value = props.getProperty(key);
                properties.put(key,value);
            }
        } catch (IOException e) {
            ExceptionHandler.handleException("Exception occurred in Application configuration to init config",e);
        }

    }

    /**
     * Cette méthode permet de récuperer l'ensemble des pairs de paramètres
     * @return HashMap(String,String) Elle retourne une Map de paramètres (clé,valeur),de type String pour les deux.
     */
    public HashMap<String, String> getProperties() {
        return properties;
    }

    /**
     * Cette méthode permet de modifier un paramètre.
     * @param key C'est la clé du paramètre à mettre à jour
     * @param value  C'est la nouvelle valeur du paramètre
     */
    public void setProperty(String key,String value){
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(propFileName);
            config.setProperty(key,value);
            config.save();
        } catch (Exception e) {
            ExceptionHandler.handleException("Exception occurred in Application configuration to set a property",e);
        }
    }

    private static ApplicationConfiguration _INSTANCE = null;
    /**
     * Cette méthode permet de récuperer l'instance unique de la classe ApplicationConfiguration.
     */
    public static ApplicationConfiguration getInstance(){
        if(_INSTANCE!=null) return _INSTANCE;
        _INSTANCE = new ApplicationConfiguration();
        return _INSTANCE;
    }
}
