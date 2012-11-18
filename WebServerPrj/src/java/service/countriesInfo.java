package service;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import database.entity.Bordercountries;
import database.entity.Coordinates;
import database.entity.Fullcountries;
import database.entity.Gdp;
import database.entity.Hdi;
import database.entity.Landarea;
import database.entity.Populations;
import database.entity.Uncode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    @Path("countryinfo/{id}")
    @Produces("application/xml")
    public countryXML getCountryInfo(@PathParam("id") String id) {
        countryXML emptyOb = new countryXML();
        if (id == "") {
            return emptyOb;
        }
        String codeISO3 = GetISO3FrmCountryName(id);
        if (codeISO3 == "") {
            return emptyOb;
        }

        return GetXMLFromISO3(codeISO3);
    }
    @GET
    @Path("orderBy/{criteria}/{order}")
    @Produces("application/xml")
    public List<countryXML> GetConutryInfoByCriteria(@PathParam("criteria") String criteria, @PathParam("order")int ascending){
        boolean bAsc= true;
        if(ascending==0){
            bAsc = false;
        }
        if(criteria.equalsIgnoreCase("population")){
            return GetSortedCountryOnPoputaltion(bAsc);
        }else if(criteria.equalsIgnoreCase("gdp")){
            return GetSortedCountryOnGDP(bAsc);
        }else if(criteria.equalsIgnoreCase("landarea")){
            return GetSortedCountryOnLandArea(bAsc);
        }else if(criteria.equalsIgnoreCase("hdi")){
            return GetSortedCountryOnHDI(bAsc);
        }else{
            List<countryXML>list = new ArrayList<countryXML>();
            countryXML emptyOb = new countryXML();
            list.add(emptyOb);
            return list;
        }
    }
    
    @GET
    @Path("checkborder/{country1}/{country2}")
    @Produces("text/plain")
    public String CheckBorderCountries(@PathParam("country1") String con1, @PathParam("country2") String con2) {
        if (AreBorderCountries(con1, con2)) {
            return "Both are border Countries";
        } else {
            return "Countries are not border countries";
        }
    }

    @GET
    @Path("frompos/{lat}/{lon}")
    @Produces("application/xml")
    public countryXML GetCountryFrmPosition(@PathParam("lat") double latitude, @PathParam("lon") double longitude) {
        String codeIso3 = GetISO3FrmCoOrdinates(latitude, longitude);
        return GetXMLFromISO3(codeIso3);
    }
    private String GetISO3FrmCountryName(String name) {
//        javax.persistence.Query q = getEntityManager().createNamedQuery("Countries.findByNameListEN", Countries.class).setParameter("nameListEN", name);
//        Countries countryOb = (Countries) q.getSingleResult();
//        return countryOb.getCodeISO3();
        //First get code corresponding to the country name from fullCountryTable as user gives smaal names and counntry table has big names
        try {
            javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", name);
            Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();
            return FullcountryOb1.getIsoAlpha3();
        }catch (NoResultException E) {
            System.out.println("There are no results from the query in Function GetISO3FrmCountryName" + E);
            return "";
        }

    }

    private boolean AreBorderCountries(String nameEn1, String nameEn2) {
        //First get code corresponding to the country name from fullCountryTable as user gives smaal names and counntry table has big names
        try {
            javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn1);
            Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();

            q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn2);
            Fullcountries FullcountryOb2 = (Fullcountries) q.getSingleResult();

            q = getEntityManager().createNamedQuery("Bordercountries.findByNameListEN", Bordercountries.class).setParameter("nameListEN", nameEn1);
            List<Bordercountries> borderCountryOb1 = q.getResultList();
            Iterator<Bordercountries> iter = borderCountryOb1.iterator();
            while (iter.hasNext()) {
                Bordercountries inBorderTable = (Bordercountries) iter.next();
                if (inBorderTable.getCodeISO3().equalsIgnoreCase(FullcountryOb2.getIsoAlpha3())) {
                    return true;
                }
            }

            return false;
        } catch (NoResultException E) {
            System.out.println("There are no results from the query in Function AreBorderCountries" + E);
            return false;
        }
    }

    private String GetISO3FrmCoOrdinates(double lat, double lon) {
        try {
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
            return ob.getCodeISO3();
        } catch (NoResultException E) {
            System.out.println("There are no results from the query in FUnction GetISO3FrmCoOrdinates" + E);
            return "";
        }
    }

    private countryXML GetXMLFromISO3(String iso3Code) {
        try {
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
        } catch (NoResultException E) {
            System.out.println("There are no results from the query in FUnction GetXMLFromISO3" + E);
            countryXML emptyOb = new countryXML();
            return emptyOb;
        }
    }
    
    private List<countryXML> GetSortedCountryOnPoputaltion(boolean asc) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Populations> cq = cb.createQuery(Populations.class);
        javax.persistence.criteria.Root<Populations> mRoot = cq.from(Populations.class);
        if (asc) {
            cq = cq.select(mRoot).orderBy(cb.asc(mRoot.<Double>get("populationTotal")));
        } else {
            cq = cq.select(mRoot).orderBy(cb.desc(mRoot.<Double>get("populationTotal")));
        }
        javax.persistence.Query finalQuery = getEntityManager().createQuery(cq);
        finalQuery.setMaxResults(10);
        List<Populations> list = finalQuery.getResultList();
        List<countryXML>listResult = new ArrayList<countryXML>();
        Iterator<Populations>iter = list.iterator();
        while(iter.hasNext()){
            Populations rowOb = iter.next();
            listResult.add(GetXMLFromISO3(rowOb.getCodeISO3()));
        }
        return listResult;
    }
      private List<countryXML> GetSortedCountryOnGDP(boolean asc) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Gdp> cq = cb.createQuery(Gdp.class);
        javax.persistence.criteria.Root<Gdp> mRoot = cq.from(Gdp.class);
        if (asc) {
            cq = cq.select(mRoot).orderBy(cb.asc(mRoot.<Double>get("gDPTotalInCurrentPrices")));
        } else {
            cq = cq.select(mRoot).orderBy(cb.desc(mRoot.<Double>get("gDPTotalInCurrentPrices")));
        }
        javax.persistence.Query finalQuery = getEntityManager().createQuery(cq);
        finalQuery.setMaxResults(10);
        List<Gdp> list = finalQuery.getResultList();
        List<countryXML>listResult = new ArrayList<countryXML>();
        Iterator<Gdp>iter = list.iterator();
        while(iter.hasNext()){
            Gdp rowOb = iter.next();
            listResult.add(GetXMLFromISO3(rowOb.getCodeISO3()));
        }
        return listResult;
    }
     private List<countryXML> GetSortedCountryOnLandArea(boolean asc) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Landarea> cq = cb.createQuery(Landarea.class);
        javax.persistence.criteria.Root<Landarea> mRoot = cq.from(Landarea.class);
        if (asc) {
            cq = cq.select(mRoot).orderBy(cb.asc(mRoot.<Double>get("landAreaTotal")));
        } else {
            cq = cq.select(mRoot).orderBy(cb.desc(mRoot.<Double>get("landAreaTotal")));
        }
        javax.persistence.Query finalQuery = getEntityManager().createQuery(cq);
        finalQuery.setMaxResults(10);
        List<Landarea> list = finalQuery.getResultList();
        List<countryXML> listResult = new ArrayList<countryXML>();
        Iterator<Landarea> iter = list.iterator();
        while (iter.hasNext()) {
            Landarea rowOb = iter.next();
            listResult.add(GetXMLFromISO3(rowOb.getCodeISO3()));
        }
        return listResult;
    }
    private List<countryXML> GetSortedCountryOnHDI(boolean asc) {
        javax.persistence.criteria.CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<Hdi> cq = cb.createQuery(Hdi.class);
        javax.persistence.criteria.Root<Hdi> mRoot = cq.from(Hdi.class);
        if (asc) {
            cq = cq.select(mRoot).orderBy(cb.asc(mRoot.<Double>get("total")));
        } else {
            cq = cq.select(mRoot).orderBy(cb.desc(mRoot.<Double>get("total")));
        }
        javax.persistence.Query finalQuery = getEntityManager().createQuery(cq);
        finalQuery.setMaxResults(10);
        List<Hdi> list = finalQuery.getResultList();
        List<countryXML> listResult = new ArrayList<countryXML>();
        Iterator<Hdi> iter = list.iterator();
        while (iter.hasNext()) {
            Hdi rowOb = iter.next();
            listResult.add(GetXMLFromISO3(rowOb.getCodeISO3()));
        }
        return listResult;
    }
}
  