package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;


/**
 * <h1>Representation des informations d'authentification d'un Utilisateur</h1>
 * <p>Cette classe represente les informations d'authentification d'un Utilisateur,
 * elle est utilisée pour récupérer les informations à partir du client via Rest/Json</p>
 *
 *<b>Note:</b> Cette classe peut être convertie en objet JSON
 *
 * @author  Mourad Hilmi
 * @version 1.0
 * @since   2016-07-17
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AuthCredentials {

    private String username;
    private String password;
    private int role;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }
    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthCredentials that = (AuthCredentials) o;

        if (getUsername() != null ? !getUsername().equals(that.getUsername()) : that.getUsername() != null)
            return false;
        return !(getPassword() != null ? !getPassword().equals(that.getPassword()) : that.getPassword() != null);

    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
