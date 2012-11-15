/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dell
 */
@Entity
@Table(name = "fullcountries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fullcountries.findAll", query = "SELECT f FROM Fullcountries f"),
    @NamedQuery(name = "Fullcountries.findByIsoAlpha3", query = "SELECT f FROM Fullcountries f WHERE f.isoAlpha3 = :isoAlpha3"),
    @NamedQuery(name = "Fullcountries.findByCountryName", query = "SELECT f FROM Fullcountries f WHERE f.countryName = :countryName"),
    @NamedQuery(name = "Fullcountries.findByContinentName", query = "SELECT f FROM Fullcountries f WHERE f.continentName = :continentName"),
    @NamedQuery(name = "Fullcountries.findByCapital", query = "SELECT f FROM Fullcountries f WHERE f.capital = :capital"),
    @NamedQuery(name = "Fullcountries.findByPopulation", query = "SELECT f FROM Fullcountries f WHERE f.population = :population")})
public class Fullcountries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "isoAlpha3")
    private String isoAlpha3;
    @Size(max = 512)
    @Column(name = "countryName")
    private String countryName;
    @Size(max = 512)
    @Column(name = "continentName")
    private String continentName;
    @Size(max = 512)
    @Column(name = "capital")
    private String capital;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "population")
    private Double population;

    public Fullcountries() {
    }

    public Fullcountries(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Double getPopulation() {
        return population;
    }

    public void setPopulation(Double population) {
        this.population = population;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isoAlpha3 != null ? isoAlpha3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fullcountries)) {
            return false;
        }
        Fullcountries other = (Fullcountries) object;
        if ((this.isoAlpha3 == null && other.isoAlpha3 != null) || (this.isoAlpha3 != null && !this.isoAlpha3.equals(other.isoAlpha3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Fullcountries[ isoAlpha3=" + isoAlpha3 + " ]";
    }
    
}
