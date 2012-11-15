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
@Table(name = "coordinates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coordinates.findAll", query = "SELECT c FROM Coordinates c"),
    @NamedQuery(name = "Coordinates.findByHasMaxLatitude", query = "SELECT c FROM Coordinates c WHERE c.hasMaxLatitude = :hasMaxLatitude"),
    @NamedQuery(name = "Coordinates.findByHasMaxLongitude", query = "SELECT c FROM Coordinates c WHERE c.hasMaxLongitude = :hasMaxLongitude"),
    @NamedQuery(name = "Coordinates.findByHasMinLatitude", query = "SELECT c FROM Coordinates c WHERE c.hasMinLatitude = :hasMinLatitude"),
    @NamedQuery(name = "Coordinates.findByHasMinLongitude", query = "SELECT c FROM Coordinates c WHERE c.hasMinLongitude = :hasMinLongitude"),
    @NamedQuery(name = "Coordinates.findByCodeISO3", query = "SELECT c FROM Coordinates c WHERE c.codeISO3 = :codeISO3")})
public class Coordinates implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "hasMaxLatitude")
    private Double hasMaxLatitude;
    @Column(name = "hasMaxLongitude")
    private Double hasMaxLongitude;
    @Column(name = "hasMinLatitude")
    private Double hasMinLatitude;
    @Column(name = "hasMinLongitude")
    private Double hasMinLongitude;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Coordinates() {
    }

    public Coordinates(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public Double getHasMaxLatitude() {
        return hasMaxLatitude;
    }

    public void setHasMaxLatitude(Double hasMaxLatitude) {
        this.hasMaxLatitude = hasMaxLatitude;
    }

    public Double getHasMaxLongitude() {
        return hasMaxLongitude;
    }

    public void setHasMaxLongitude(Double hasMaxLongitude) {
        this.hasMaxLongitude = hasMaxLongitude;
    }

    public Double getHasMinLatitude() {
        return hasMinLatitude;
    }

    public void setHasMinLatitude(Double hasMinLatitude) {
        this.hasMinLatitude = hasMinLatitude;
    }

    public Double getHasMinLongitude() {
        return hasMinLongitude;
    }

    public void setHasMinLongitude(Double hasMinLongitude) {
        this.hasMinLongitude = hasMinLongitude;
    }

    public String getCodeISO3() {
        return codeISO3;
    }

    public void setCodeISO3(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeISO3 != null ? codeISO3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coordinates)) {
            return false;
        }
        Coordinates other = (Coordinates) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Coordinates[ codeISO3=" + codeISO3 + " ]";
    }
    
}
