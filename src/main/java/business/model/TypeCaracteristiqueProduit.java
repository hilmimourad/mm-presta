package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <h1>Type d'une caracteristique de produit</h1>
 * <p>Cette classe représente le type d'une caractéristique de produit</p>
 *
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-02
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class TypeCaracteristiqueProduit implements Serializable {


	private long id;
	private String libelle;

	public TypeCaracteristiqueProduit() {
		super();
	}

    @Id
    @GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

    @Column
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeCaracteristiqueProduit that = (TypeCaracteristiqueProduit) o;

        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "TypeCaracteristiqueProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}

