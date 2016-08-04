package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <h1>Une caracteristique type des produits</h1>
 * <p>Cette classe représente une caracéristique type pour les produits.</p>
 *
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-03
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class CaracteristiqueProduit implements Serializable{

	private long id;
	private String libelle;
	private String description;
	private TypeCaracteristiqueProduit type;

	public CaracteristiqueProduit() {
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

    @Column
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.DETACH},fetch = FetchType.EAGER)
	public TypeCaracteristiqueProduit getType() {
		return type;
	}
	public void setType(TypeCaracteristiqueProduit type) {
		this.type = type;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CaracteristiqueProduit that = (CaracteristiqueProduit) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "CaracteristiqueProduit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}
