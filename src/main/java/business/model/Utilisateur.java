package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>Un utilisateur de l'application</h1>
 * <p>Cette classe représente un utilisateur de l'application</p>
 *
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-01
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Utilisateur implements Serializable{

    /**
     * Constante correspondant au role administrateur.
     */
    @Transient
    public static final int _ADMIN = 1;
    /**
     * Constante correspondant au role Utilisateur normal.
     */
    @Transient
    public static final int _USER = 2;
    /**
     * Une map des roles associés à leurs noms respectifs.
     */
    @Transient
    public static final Map<Integer, String> _ROLES = new HashMap<Integer, String>();
 
    static{
        _ROLES.put(_ADMIN, "Administrateur");
        _ROLES.put(_USER,  "Utilisateur");
    }

    private String username;
    private String password;
    /**
     * Variable representant le role de l'utilisateur doit être initialisé en utilisant les constantes fournies pas la classe
     */
    private int role;
     public Utilisateur() {
        super();
    }

    @Id
    @GeneratedValue
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role==_ADMIN || role==_USER ? role:_USER;
        this.role = role;
    }

    /**
     * Cette méthode permet de récupérer un String correspondant au role de l'utilisateur.
     * @return   nom du role de l'utilisateur
     * @see String
     */
    @Transient
    public String getRoleName(){
        return _ROLES.get(role);
     }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur that = (Utilisateur) o;

        return getUsername().equals(that.getUsername());

    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
