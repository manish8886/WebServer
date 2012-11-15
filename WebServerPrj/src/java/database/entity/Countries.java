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
@Table(name = "countries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Countries.findAll", query = "SELECT c FROM Countries c"),
    @NamedQuery(name = "Countries.findByCodeISO3", query = "SELECT c FROM Countries c WHERE c.codeISO3 = :codeISO3"),
    @NamedQuery(name = "Countries.findByCodeISO2", query = "SELECT c FROM Countries c WHERE c.codeISO2 = :codeISO2"),
    @NamedQuery(name = "Countries.findByNameOfficialEN", query = "SELECT c FROM Countries c WHERE c.nameOfficialEN = :nameOfficialEN"),
    @NamedQuery(name = "Countries.findByNameShortEN", query = "SELECT c FROM Countries c WHERE c.nameShortEN = :nameShortEN"),
    @NamedQuery(name = "Countries.findByNameListEN", query = "SELECT c FROM Countries c WHERE c.nameListEN = :nameListEN")})
public class Countries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;
    @Size(max = 512)
    @Column(name = "codeISO2")
    private String codeISO2;
    @Size(max = 512)
    @Column(name = "nameOfficialEN")
    private String nameOfficialEN;
    @Size(max = 512)
    @Column(name = "nameShortEN")
    private String nameShortEN;
    @Size(max = 512)
    @Column(name = "nameListEN")
    private String nameListEN;

    public Countries() {
    }

    public Countries(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getCodeISO3() {
        return codeISO3;
    }

    public void setCodeISO3(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public String getCodeISO2() {
        return codeISO2;
    }

    public void setCodeISO2(String codeISO2) {
        this.codeISO2 = codeISO2;
    }

    public String getNameOfficialEN() {
        return nameOfficialEN;
    }

    public void setNameOfficialEN(String nameOfficialEN) {
        this.nameOfficialEN = nameOfficialEN;
    }

    public String getNameShortEN() {
        return nameShortEN;
    }

    public void setNameShortEN(String nameShortEN) {
        this.nameShortEN = nameShortEN;
    }

    public String getNameListEN() {
        return nameListEN;
    }

    public void setNameListEN(String nameListEN) {
        this.nameListEN = nameListEN;
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
        if (!(object instanceof Countries)) {
            return false;
        }
        Countries other = (Countries) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Countries[ codeISO3=" + codeISO3 + " ]";
    }
    
}
