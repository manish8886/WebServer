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
@Table(name = "gdp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gdp.findAll", query = "SELECT g FROM Gdp g"),
    @NamedQuery(name = "Gdp.findByGDPNotes", query = "SELECT g FROM Gdp g WHERE g.gDPNotes = :gDPNotes"),
    @NamedQuery(name = "Gdp.findByGDPTotalInCurrentPrices", query = "SELECT g FROM Gdp g WHERE g.gDPTotalInCurrentPrices = :gDPTotalInCurrentPrices"),
    @NamedQuery(name = "Gdp.findByGDPUnit", query = "SELECT g FROM Gdp g WHERE g.gDPUnit = :gDPUnit"),
    @NamedQuery(name = "Gdp.findByGDPYear", query = "SELECT g FROM Gdp g WHERE g.gDPYear = :gDPYear"),
    @NamedQuery(name = "Gdp.findByCodeISO3", query = "SELECT g FROM Gdp g WHERE g.codeISO3 = :codeISO3")})
public class Gdp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 512)
    @Column(name = "GDPNotes")
    private String gDPNotes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "GDPTotalInCurrentPrices")
    private Double gDPTotalInCurrentPrices;
    @Size(max = 512)
    @Column(name = "GDPUnit")
    private String gDPUnit;
    @Column(name = "GDPYear")
    private Integer gDPYear;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Gdp() {
    }

    public Gdp(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getGDPNotes() {
        return gDPNotes;
    }

    public void setGDPNotes(String gDPNotes) {
        this.gDPNotes = gDPNotes;
    }

    public Double getGDPTotalInCurrentPrices() {
        return gDPTotalInCurrentPrices;
    }

    public void setGDPTotalInCurrentPrices(Double gDPTotalInCurrentPrices) {
        this.gDPTotalInCurrentPrices = gDPTotalInCurrentPrices;
    }

    public String getGDPUnit() {
        return gDPUnit;
    }

    public void setGDPUnit(String gDPUnit) {
        this.gDPUnit = gDPUnit;
    }

    public Integer getGDPYear() {
        return gDPYear;
    }

    public void setGDPYear(Integer gDPYear) {
        this.gDPYear = gDPYear;
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
        if (!(object instanceof Gdp)) {
            return false;
        }
        Gdp other = (Gdp) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Gdp[ codeISO3=" + codeISO3 + " ]";
    }
    
}
