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

    public countryXML() {
        unCode = "Not Found";
        nameEN = "Not Found";
        nameCapital = "Not Found";
        population = "Not Found";
        gdpTotal = "Not Found";
        landarea = "Not Found";
        hdi = "Not Found";
    }

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

    public countryXML SetUnCode(Integer Code) {
        if (Code == null) {
            this.unCode = "";

        } else {
            this.unCode = Code.toString();
        }
        return this;
    }

    public countryXML SetNameEn(String nameEn) {
        this.nameEN = nameEn;
        return this;
    }

    public countryXML SetCapitalName(String nameCapital) {
        this.nameCapital = nameCapital;
        return this;
    }

    public countryXML SetPopulation(Double population) {

        if (population == null) {
            this.population = "";

        } else {
            this.population = population.toString();
        }

        return this;
    }

    public countryXML SetGDP(Double gdp) {
        if (gdp == null) {
            this.gdpTotal = "";

        } else {
            this.gdpTotal = gdp.toString();
        }
        return this;
    }

    public countryXML SetLandArea(Double landArea) {
        if (landArea == null) {
            this.landarea = "";

        } else {
            this.landarea = landArea.toString();
        }

        return this;
    }

    public countryXML SetHDI(Double HDI) {

        if (HDI == null) {
            this.hdi = "";

        } else {
            this.hdi = HDI.toString();
        }


        return this;
    }
}
