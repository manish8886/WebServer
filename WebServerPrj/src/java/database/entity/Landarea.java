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
@Table(name = "landarea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Landarea.findAll", query = "SELECT l FROM Landarea l"),
    @NamedQuery(name = "Landarea.findByLandAreaNotes", query = "SELECT l FROM Landarea l WHERE l.landAreaNotes = :landAreaNotes"),
    @NamedQuery(name = "Landarea.findByLandAreaTotal", query = "SELECT l FROM Landarea l WHERE l.landAreaTotal = :landAreaTotal"),
    @NamedQuery(name = "Landarea.findByLandAreaUnit", query = "SELECT l FROM Landarea l WHERE l.landAreaUnit = :landAreaUnit"),
    @NamedQuery(name = "Landarea.findByLandAreaYear", query = "SELECT l FROM Landarea l WHERE l.landAreaYear = :landAreaYear"),
    @NamedQuery(name = "Landarea.findByCodeISO3", query = "SELECT l FROM Landarea l WHERE l.codeISO3 = :codeISO3")})
public class Landarea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "landAreaNotes")
    private String landAreaNotes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "landAreaTotal")
    private Double landAreaTotal;
    @Size(max = 512)
    @Column(name = "landAreaUnit")
    private String landAreaUnit;
    @Column(name = "landAreaYear")
    private Integer landAreaYear;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Landarea() {
    }

    public Landarea(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getLandAreaNotes() {
        return landAreaNotes;
    }

    public void setLandAreaNotes(String landAreaNotes) {
        this.landAreaNotes = landAreaNotes;
    }

    public Double getLandAreaTotal() {
        return landAreaTotal;
    }

    public void setLandAreaTotal(Double landAreaTotal) {
        this.landAreaTotal = landAreaTotal;
    }

    public String getLandAreaUnit() {
        return landAreaUnit;
    }

    public void setLandAreaUnit(String landAreaUnit) {
        this.landAreaUnit = landAreaUnit;
    }

    public Integer getLandAreaYear() {
        return landAreaYear;
    }

    public void setLandAreaYear(Integer landAreaYear) {
        this.landAreaYear = landAreaYear;
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
        if (!(object instanceof Landarea)) {
            return false;
        }
        Landarea other = (Landarea) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Landarea[ codeISO3=" + codeISO3 + " ]";
    }
    
}
