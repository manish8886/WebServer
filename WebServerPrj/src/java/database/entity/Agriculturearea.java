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
@Table(name = "agriculturearea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agriculturearea.findAll", query = "SELECT a FROM Agriculturearea a"),
    @NamedQuery(name = "Agriculturearea.findByAgriculturalAreaNotes", query = "SELECT a FROM Agriculturearea a WHERE a.agriculturalAreaNotes = :agriculturalAreaNotes"),
    @NamedQuery(name = "Agriculturearea.findByAgriculturalAreaTotal", query = "SELECT a FROM Agriculturearea a WHERE a.agriculturalAreaTotal = :agriculturalAreaTotal"),
    @NamedQuery(name = "Agriculturearea.findByAgriculturalAreaUnit", query = "SELECT a FROM Agriculturearea a WHERE a.agriculturalAreaUnit = :agriculturalAreaUnit"),
    @NamedQuery(name = "Agriculturearea.findByAgriculturalAreaYear", query = "SELECT a FROM Agriculturearea a WHERE a.agriculturalAreaYear = :agriculturalAreaYear"),
    @NamedQuery(name = "Agriculturearea.findByCodeISO3", query = "SELECT a FROM Agriculturearea a WHERE a.codeISO3 = :codeISO3")})
public class Agriculturearea implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "agriculturalAreaNotes")
    private String agriculturalAreaNotes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "agriculturalAreaTotal")
    private Double agriculturalAreaTotal;
    @Size(max = 512)
    @Column(name = "agriculturalAreaUnit")
    private String agriculturalAreaUnit;
    @Column(name = "agriculturalAreaYear")
    private Integer agriculturalAreaYear;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Agriculturearea() {
    }

    public Agriculturearea(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getAgriculturalAreaNotes() {
        return agriculturalAreaNotes;
    }

    public void setAgriculturalAreaNotes(String agriculturalAreaNotes) {
        this.agriculturalAreaNotes = agriculturalAreaNotes;
    }

    public Double getAgriculturalAreaTotal() {
        return agriculturalAreaTotal;
    }

    public void setAgriculturalAreaTotal(Double agriculturalAreaTotal) {
        this.agriculturalAreaTotal = agriculturalAreaTotal;
    }

    public String getAgriculturalAreaUnit() {
        return agriculturalAreaUnit;
    }

    public void setAgriculturalAreaUnit(String agriculturalAreaUnit) {
        this.agriculturalAreaUnit = agriculturalAreaUnit;
    }

    public Integer getAgriculturalAreaYear() {
        return agriculturalAreaYear;
    }

    public void setAgriculturalAreaYear(Integer agriculturalAreaYear) {
        this.agriculturalAreaYear = agriculturalAreaYear;
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
        if (!(object instanceof Agriculturearea)) {
            return false;
        }
        Agriculturearea other = (Agriculturearea) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Agriculturearea[ codeISO3=" + codeISO3 + " ]";
    }
    
}
