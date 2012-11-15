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
@Table(name = "bordercountries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bordercountries.findAll", query = "SELECT b FROM Bordercountries b"),
    @NamedQuery(name = "Bordercountries.findByRowId", query = "SELECT b FROM Bordercountries b WHERE b.rowId = :rowId"),
    @NamedQuery(name = "Bordercountries.findByNameListEN", query = "SELECT b FROM Bordercountries b WHERE b.nameListEN = :nameListEN"),
    @NamedQuery(name = "Bordercountries.findByCodeISO3", query = "SELECT b FROM Bordercountries b WHERE b.codeISO3 = :codeISO3")})
public class Bordercountries implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RowId")
    private Integer rowId;
    @Size(max = 512)
    @Column(name = "nameListEN")
    private String nameListEN;
    @Size(max = 512)
    @Column(name = "codeISO3")
    private String codeISO3;

    public Bordercountries() {
    }

    public Bordercountries(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getNameListEN() {
        return nameListEN;
    }

    public void setNameListEN(String nameListEN) {
        this.nameListEN = nameListEN;
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
        hash += (rowId != null ? rowId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bordercountries)) {
            return false;
        }
        Bordercountries other = (Bordercountries) object;
        if ((this.rowId == null && other.rowId != null) || (this.rowId != null && !this.rowId.equals(other.rowId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.entity.Bordercountries[ rowId=" + rowId + " ]";
    }
    
}
