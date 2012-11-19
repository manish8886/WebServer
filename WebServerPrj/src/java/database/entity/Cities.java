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
@Table(name = "cities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cities.findAll", query = "SELECT c FROM Cities c"),
    @NamedQuery(name = "Cities.findByName", query = "SELECT c FROM Cities c WHERE c.name = :name"),
    @NamedQuery(name = "Cities.findByLat", query = "SELECT c FROM Cities c WHERE c.lat = :lat"),
    @NamedQuery(name = "Cities.findByLng", query = "SELECT c FROM Cities c WHERE c.lng = :lng"),
    @NamedQuery(name = "Cities.findByCountryName", query = "SELECT c FROM Cities c WHERE c.countryName = :countryName"),
    @NamedQuery(name = "Cities.findByGeonameId", query = "SELECT c FROM Cities c WHERE c.geonameId = :geonameId"),
    @NamedQuery(name = "Cities.findByRowId", query = "SELECT c FROM Cities c WHERE c.rowId = :rowId"),
    @NamedQuery(name = "Cities.findByCapital", query = "SELECT c FROM Cities c WHERE c.capital = :capital")})
public class Cities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
    @Size(max = 512)
    @Column(name = "countryName")
    private String countryName;
    @Column(name = "geonameId")
    private Integer geonameId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RowId")
    private Integer rowId;
    @Size(max = 512)
    @Column(name = "capital")
    private String capital;

    public Cities() {
    }

    public Cities(Integer rowId) {
        this.rowId = rowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(Integer geonameId) {
        this.geonameId = geonameId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rowId != null ? rowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cities)) {
            return false;
        }
        Cities other = (Cities) object;
        if ((this.rowId == null && other.rowId != null) || (this.rowId != null && !this.rowId.equals(other.rowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Cities[ rowId=" + rowId + " ]";
    }
    
}
