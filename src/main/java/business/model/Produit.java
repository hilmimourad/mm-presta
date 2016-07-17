package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Un produit ou service</h1>
 * <p>Cette représente l'association un produit appartenant à une famille de préstations</p>
 * </p>
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-03
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class Produit implements Serializable{

    private long id;
    private String libelle;
    private String description;
    private FamillePrestation famille;
    private List<AssociationProduitCaracteristique> listAssociationProduitCaracteristique = new ArrayList<AssociationProduitCaracteristique>();

    public Produit() {
        super();
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }
    public void setId(int id) {
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

    @ManyToOne(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
    public FamillePrestation getFamille() {
        return famille;
    }
    public void setFamille(FamillePrestation famille) {
        this.famille = famille;
    }

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    public List<AssociationProduitCaracteristique> getListAssociationProduitCaracteristique() {
        return listAssociationProduitCaracteristique;
    }
    public void setListAssociationProduitCaracteristique(List<AssociationProduitCaracteristique> listAssociationProduitCaracteristique) {
        this.listAssociationProduitCaracteristique = listAssociationProduitCaracteristique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Produit produit = (Produit) o;

        return id == produit.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
