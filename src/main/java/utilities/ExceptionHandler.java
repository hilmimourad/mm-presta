package utilities;

/**
 * <h1>Traite les exceptions</h1>
 * <p>Cette classe permet de traiter les <b>Exceptions</b> de façon personnalisée.
 *  L'utilisation de cette classe permet de centraliser la logique de traitement des exceptions. Donc pour
 *  changer la façon dont on gére les exceptions dans l'application il suffit de retoucher cette classe seulement
 * </p>
 *
 * <b>Note:</b> Cette classe est abstraite donc on ne peut l'instancier. En revanche toutes
 * les méthodes de cette classe sont statiques.
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-17
 */
public abstract class ExceptionHandler {
    /**
     * Cette méthode de classe permet de traiter les exceptions de façon personnalisé
     * @param explication C'est un commentaire ou un titre à donner à l'exception pour une meilleur compréhension
     * @param e  C'est l'objet de type <b>Exception</b> à traiter
     */
    public static void handleException(String explication,Exception e){
        e.printStackTrace();
    }
}

