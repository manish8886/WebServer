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
@Table(name = "populations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Populations.findAll", query = "SELECT p FROM Populations p"),
    @NamedQuery(name = "Populations.findByPopulationNotes", query = "SELECT p FROM Populations p WHERE p.populationNotes = :populationNotes"),
    @NamedQuery(name = "Populations.findByPopulationTotal", query = "SELECT p FROM Populations p WHERE p.populationTotal = :populationTotal"),
    @NamedQuery(name = "Populations.findByPopulationUnit", query = "SELECT p FROM Populations p WHERE p.populationUnit = :populationUnit"),
    @NamedQuery(name = "Populations.findByPopulationYear", query = "SELECT p FROM Populations p WHERE p.populationYear = :populationYear"),
    @NamedQuery(name = "Populations.findByCodeISO3", query = "SELECT p FROM Populations p WHERE p.codeISO3 = :codeISO3")})
public class Populations implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "populationNotes")
    private String populationNotes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "populationTotal")
    private Double populationTotal;
    @Size(max = 512)
    @Column(name = "populationUnit")
    private String populationUnit;
    @Column(name = "populationYear")
    private Integer populationYear;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Populations() {
    }

    public Populations(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getPopulationNotes() {
        return populationNotes;
    }

    public void setPopulationNotes(String populationNotes) {
        this.populationNotes = populationNotes;
    }

    public Double getPopulationTotal() {
        return populationTotal;
    }

    public void setPopulationTotal(Double populationTotal) {
        this.populationTotal = populationTotal;
    }

    public String getPopulationUnit() {
        return populationUnit;
    }

    public void setPopulationUnit(String populationUnit) {
        this.populationUnit = populationUnit;
    }

    public Integer getPopulationYear() {
        return populationYear;
    }

    public void setPopulationYear(Integer populationYear) {
        this.populationYear = populationYear;
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
        if (!(object instanceof Populations)) {
            return false;
        }
        Populations other = (Populations) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Populations[ codeISO3=" + codeISO3 + " ]";
    }
    
}
