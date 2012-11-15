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
@Table(name = "hdi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hdi.findAll", query = "SELECT h FROM Hdi h"),
    @NamedQuery(name = "Hdi.findByNotes", query = "SELECT h FROM Hdi h WHERE h.notes = :notes"),
    @NamedQuery(name = "Hdi.findByTotal", query = "SELECT h FROM Hdi h WHERE h.total = :total"),
    @NamedQuery(name = "Hdi.findByYear", query = "SELECT h FROM Hdi h WHERE h.year = :year"),
    @NamedQuery(name = "Hdi.findByCodeISO3", query = "SELECT h FROM Hdi h WHERE h.codeISO3 = :codeISO3")})
public class Hdi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "Notes")
    private String notes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private Double total;
    @Column(name = "Year")
    private Integer year;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Hdi() {
    }

    public Hdi(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        if (!(object instanceof Hdi)) {
            return false;
        }
        Hdi other = (Hdi) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Hdi[ codeISO3=" + codeISO3 + " ]";
    }
    
}
