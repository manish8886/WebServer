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
@Table(name = "uncode")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uncode.findAll", query = "SELECT u FROM Uncode u"),
    @NamedQuery(name = "Uncode.findByCodeUN", query = "SELECT u FROM Uncode u WHERE u.codeUN = :codeUN"),
    @NamedQuery(name = "Uncode.findByCodeISO3", query = "SELECT u FROM Uncode u WHERE u.codeISO3 = :codeISO3")})
public class Uncode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "codeUN")
    private Integer codeUN;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Uncode() {
    }

    public Uncode(String codeISO3) {
        this.codeISO3 = codeISO3;
    }

    public Integer getCodeUN() {
        return codeUN;
    }

    public void setCodeUN(Integer codeUN) {
        this.codeUN = codeUN;
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
        if (!(object instanceof Uncode)) {
            return false;
        }
        Uncode other = (Uncode) object;
        if ((this.codeISO3 == null && other.codeISO3 != null) || (this.codeISO3 != null && !this.codeISO3.equals(other.codeISO3))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Uncode[ codeISO3=" + codeISO3 + " ]";
    }
    
}
