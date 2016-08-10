package business.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Une famille de préstations</h1>
 * <p>Cette représente une famille de prestations qui peut posséder des sous familles de préstations</p>
 *
 *<b>Note:</b> Cette classes est une entité dans la base des données, et peut être convertie en objet JSON
 *
 * @author  Salim Dahiry
 * @version 1.0
 * @since   2016-07-02
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity
public class FamillePrestation implements Serializable {

    /**
     * Le code c'est le nom unique de la famille connue par les collaborateurs de l'organisme
     * **/
    private String code;
    private long id;
    private String libelle;
    private FamillePrestation famillemere;
    @JsonIgnore
    private List<FamillePrestation> listeSousFamille = new ArrayList<FamillePrestation>();
    @JsonIgnore
    private List<Produit> listeProduit = new ArrayList<Produit>();
    public FamillePrestation() {
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


    @Column(unique=true)
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    @Column
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.DETACH},fetch = FetchType.EAGER)
    public FamillePrestation getFamillemere() {
        return famillemere;
    }
    public void setFamillemere(FamillePrestation famillemere) {
        this.famillemere = famillemere;
    }

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER,mappedBy = "famillemere")
    public List<FamillePrestation> getListeSousFamille() {
        return listeSousFamille;
    }
    public void setListeSousFamille(List<FamillePrestation> listeSousFamille) {
        this.listeSousFamille = listeSousFamille;
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn
    public List<Produit> getListeProduit() {
        return listeProduit;
    }
    public void setListeProduit(List<Produit> listeProduit) {
        this.listeProduit = listeProduit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FamillePrestation that = (FamillePrestation) o;
        return getId() != that.getId()&& getCode().equals(that.getCode());

    }

    @Override
    public int hashCode() {
        int result = getCode().hashCode();
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "FamillePrestation{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", libelle='" + libelle + '\'' +
                ", famillemere=" + famillemere +
                ", listeSousFamille=" + listeSousFamille +
                ", listeProduit=" + listeProduit +
                '}';
    }
}
