package service;

import database.entity.Bordercountries;
import database.entity.Coordinates;
import database.entity.Countries;
import database.entity.Fullcountries;
import database.entity.Gdp;
import database.entity.Hdi;
import database.entity.Landarea;
import database.entity.Populations;
import database.entity.Uncode;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author dell
 */
@Stateless
@Path("generic")
public class countriesInfo {

    @PersistenceContext(unitName = "WebServerPrjPU")
    private EntityManager em;

    /**
     * Creates a new instance of countriesInfo
     */
    public countriesInfo() {
    }

    /**
     * Retrieves representation of an instance of database.entity.countriesInfo
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of countriesInfo
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("population")
    @Produces("application/xml")
    public List<Countries> GetCountries1() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Countries.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(10);
        q.setFirstResult(1);
        return q.getResultList();
    }
    @GET
    @Path("countryinfo/{id}")
    @Produces("application/xml")
    public countryXML getCountryInfo(@PathParam("id") String id) {
        if (id == "") {
            return null;
        }
        String codeISO3 = GetISO3FrmCountryName(id);
        if (codeISO3 == "") {
            return null;
        }
        return GetXMLFromISO3(codeISO3);
    }
    @GET
    @Path("checkborder/{country1}/{country2}")
    @Produces("text/plain")
    public String CheckBorderCountries(@PathParam("country1") String con1,@PathParam("country2") String con2) {
        if(AreBorderCountries(con1, con2)){
            return "Both are border Countries";
        }else{
            return "Countries are not border countries";
        }
    }
    @GET
    @Path("frompos/{lat}/{lon}")
    @Produces("application/xml")
    public countryXML GetCountryFrmPosition(@PathParam("lat") double latitude, @PathParam("lon") double longitude) {
         String codeIso3 =GetISO3FrmCoOrdinates(latitude, longitude);
         return GetXMLFromISO3(codeIso3);
    }

    private String GetISO3FrmCountryName(String name) {
//        javax.persistence.Query q = getEntityManager().createNamedQuery("Countries.findByNameListEN", Countries.class).setParameter("nameListEN", name);
//        Countries countryOb = (Countries) q.getSingleResult();
//        return countryOb.getCodeISO3();
         //First get code corresponding to the country name from fullCountryTable as user gives smaal names and counntry table has big names
        javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", name);
        Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();
        
        return FullcountryOb1.getIsoAlpha3();
        
    }
    
    private boolean AreBorderCountries(String nameEn1, String nameEn2){
        //First get code corresponding to the country name from fullCountryTable as user gives smaal names and counntry table has big names
        javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn1);
        Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn2);
        Fullcountries FullcountryOb2 = (Fullcountries) q.getSingleResult();
        
        q = getEntityManager().createNamedQuery("Bordercountries.findByNameListEN", Bordercountries.class).setParameter("nameListEN", nameEn1);
        List<Bordercountries> borderCountryOb1 =q.getResultList();
        Iterator<Bordercountries>iter= borderCountryOb1.iterator();
        while(iter.hasNext()){
            Bordercountries inBorderTable = (Bordercountries)iter.next();
            if(inBorderTable.getCodeISO3().equalsIgnoreCase(FullcountryOb2.getIsoAlpha3())){
                return true;
            }
        }
        
          return false;
    }
    private String GetISO3FrmCoOrdinates(double lat, double lon) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Coordinates> cq = cb.createQuery(Coordinates.class);
        javax.persistence.criteria.Root<Coordinates> mRoot = cq.from(Coordinates.class);
        javax.persistence.criteria.Predicate pValid1 = cb.lessThanOrEqualTo(mRoot.<Double>get("hasMinLongitude"), lon);
        javax.persistence.criteria.Predicate pValid2 = cb.greaterThanOrEqualTo(mRoot.<Double>get("hasMaxLongitude"), lon);
        javax.persistence.criteria.Predicate pValid3 = cb.lessThanOrEqualTo(mRoot.<Double>get("hasMinLatitude"), lat);
        javax.persistence.criteria.Predicate pValid4 = cb.greaterThanOrEqualTo(mRoot.<Double>get("hasMaxLatitude"), lat);
        javax.persistence.Query finalQuery = getEntityManager().createQuery(cq.select(mRoot).where(cb.and(pValid1, pValid2, pValid3, pValid4)));
        finalQuery.setMaxResults(1);
        Coordinates ob = (Coordinates) finalQuery.getSingleResult();
        if (ob == null) {
            return "";
        }
        return ob.getCodeISO3();
    }

    private countryXML GetXMLFromISO3(String iso3Code) {
        javax.persistence.Query q = getEntityManager().createNamedQuery("Uncode.findByCodeISO3", Uncode.class).setParameter("codeISO3", iso3Code);
        Uncode unCodeOb = (Uncode) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Fullcountries.findByIsoAlpha3", Fullcountries.class).setParameter("isoAlpha3", iso3Code);
        Fullcountries fullCountryInfoOb = (Fullcountries) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Populations.findByCodeISO3", Populations.class).setParameter("codeISO3", iso3Code);
        Populations populationOb = (Populations) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Gdp.findByCodeISO3", Gdp.class).setParameter("codeISO3", iso3Code);
        Gdp gdpOb = (Gdp) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Landarea.findByCodeISO3", Landarea.class).setParameter("codeISO3", iso3Code);
        Landarea landArea = (Landarea) q.getSingleResult();

        q = getEntityManager().createNamedQuery("Hdi.findByCodeISO3", Hdi.class).setParameter("codeISO3", iso3Code);
        Hdi hdiOb = (Hdi) q.getSingleResult();

        countryXML xmlOb = new countryXML();
        xmlOb = xmlOb.SetUnCode(unCodeOb.getCodeUN().toString()).SetNameEn(fullCountryInfoOb.getCountryName()).SetCapitalName(fullCountryInfoOb.getCapital()).SetPopulation(populationOb.getPopulationTotal().toString());
        xmlOb.SetGDP(gdpOb.getGDPTotalInCurrentPrices().toString()).SetLandArea(landArea.getLandAreaTotal().toString()).SetHDI(hdiOb.getTotal().toString());
        return xmlOb;
    }
}
