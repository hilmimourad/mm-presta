package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <h1>Association entre un produit et une caracteristique</h1>
 * <p>Cette classe représente l'association entre un produit et une caraceristique tout en précisant
 * des informations additionnelles liées au produit en question</p>
 *
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-04
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class AssociationProduitCaracteristique implements Serializable{

	private long  id;
	private float valeurMax;
	private float valeurMin;
	private float valeurDefaut;
	private float tarif;
	private Produit produit;
	private CaracteristiqueProduit caracteristiqueProduit;

	public AssociationProduitCaracteristique() {
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
	public float getValeurMax() {
		return valeurMax;
	}
	public void setValeurMax(float valeurMax) {
		this.valeurMax = valeurMax;
	}

	@Column
	public float getValeurMin() {
		return valeurMin;
	}
	public void setValeurMin(float valeurMin) {
		this.valeurMin = valeurMin;
	}

	@Column
	public float getValeurDefaut() {
		return valeurDefaut;
	}
	public void setValeurDefaut(float valeurDefaut) {
		this.valeurDefaut = valeurDefaut;
	}

	@Column
	public float getTarif() {
		return tarif;
	}
	public void setTarif(float tarif) {
		this.tarif = tarif;
	}

	@ManyToOne(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
	public CaracteristiqueProduit getCaracteristiqueProduit() {
		return caracteristiqueProduit;
	}
	public void setCaracteristiqueProduit(CaracteristiqueProduit caracteristiqueProduit) {
		this.caracteristiqueProduit = caracteristiqueProduit;
	}

	@ManyToOne(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AssociationProduitCaracteristique))
			return false;
		AssociationProduitCaracteristique other = (AssociationProduitCaracteristique) obj;
        return id==other.id;
	}
	
	
	

}
