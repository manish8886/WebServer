/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
public class countryXML {
    @XmlElement(name = "uncode")
    private String unCode;
    @XmlElement(name = "name")
    private String nameEN;
    @XmlElement(name = "capital")
    private String nameCapital;
    @XmlElement(name = "population")
    private String population;
    @XmlElement(name = "gdptotal")
    private String gdpTotal;
    @XmlElement(name = "landarea")
    private String landarea;
    @XmlElement(name = "hdi")
    private String hdi;
    public String getUnCode() {
        return unCode;
    }

    public String getNameEn() {
        return nameEN;
    }

    public String getCapitalName() {
        return nameCapital;
    }

    public String getPopulation() {
        return population;
    }

    public String getGdpTotal() {
        return gdpTotal;
    }

    public String getLandArea() {
        return landarea;
    }

    public String getHDI() {
        return hdi;
    }
    public countryXML SetUnCode(String Code) {
        this.unCode = Code;
        return this;
    }

    public countryXML SetNameEn(String nameEn) {
        this.nameEN = nameEn;
        return this;
    }

    public countryXML SetCapitalName(String nameCapital) {
        this.nameCapital= nameCapital;
        return this;
    }

    public countryXML SetPopulation(String population) {
        this.population = population;
        return this;
    }

    public countryXML SetGDP(String gdp) {
        this.gdpTotal = gdp;
        return this;
    }

    public countryXML SetLandArea(String landArea) {
        this.landarea = landArea;
        return this;
    }

    public countryXML SetHDI(String HDI) {
        this.hdi = HDI;
        return this;
    }
}
