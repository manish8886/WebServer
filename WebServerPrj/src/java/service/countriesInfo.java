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
import database.entity.Cities;
import database.entity.Coordinates;
import database.entity.Countries;
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

    @GET
    @Path("neighbourscapitals/{lat}/{lon}")
    @Produces("application/xml")
    public List<countryXML> GetNeighbourCapitalsInfo(@PathParam("lat") double latitude, @PathParam("lon") double longitude) {
               
        List<countryXML>list= GetBorderCountriesFromPosOfaCity(latitude, longitude);
        if(list.isEmpty()){
            countryXML emptyOb = new countryXML();
            list.add(emptyOb);
            return list;
        }
    
        return list;
    }
     @GET
    @Path("closestcapital/{lat}/{lon}")
    @Produces("application/xml")
    public countryXML GetClosestCapital(@PathParam("lat") double latitude, @PathParam("lon") double longitude) {
                return findClosestCapital(latitude, longitude);
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
    private countryXML findClosestCapital(double lat, double lon){
        List<Countries> countryList = GetListofBorderCountries(GetISO3FrmCoOrdinates(lat, lon));
        try {
            javax.persistence.Query q;
            double minDist = 0;
            int index = 0;
            for (int i = 0; i < countryList.size(); i++) {
                Countries countryOb = countryList.get(i);
                q = getEntityManager().createNamedQuery("Fullcountries.findByIsoAlpha3", Fullcountries.class).setParameter("isoAlpha3", countryOb.getCodeISO3());
                Fullcountries CountryFull = (Fullcountries) q.getSingleResult();
                q = getEntityManager().createNamedQuery("Cities.findByCapital", Cities.class).setParameter("capital", CountryFull.getCapital());
                Cities city = (Cities) q.getSingleResult();
                if (i == 0) {
                    minDist = distance(lat, lon, city.getLat(), city.getLng(), 'K');
                } else {
                    double dist = distance(lat, lon, city.getLat(), city.getLng(), 'K');
                    if (dist < minDist) {
                        minDist = dist;
                        index = i;
                    }
                }

            }
            return GetXMLFromISO3(countryList.get(index).getCodeISO3());
        } catch (NoResultException E) {
            countryXML xmlob = new countryXML();
            System.out.println("There are no results from the query in Function AreBorderCountries" + E);
            return xmlob;
        }
    }
    private List<countryXML> GetBorderCountriesFromPosOfaCity(double lat, double lon) {
        List<countryXML> countriesXML = new ArrayList<countryXML>();
        try {
            String codeIS03 = GetISO3FrmCoOrdinates(lat, lon);
            javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByIsoAlpha3", Fullcountries.class).setParameter("isoAlpha3", codeIS03);
            Fullcountries CountryFull = (Fullcountries)q.getSingleResult();
            List<Countries>borderCountries= GetListofBorderCountries(CountryFull.getCountryName());
            Iterator<Countries> iter = borderCountries.iterator();
            while (iter.hasNext()) {
                Countries country = iter.next();
                countryXML xmlOb = GetXMLFromISO3(country.getCodeISO3());
                countriesXML.add(xmlOb);
            }
            return countriesXML;
        } catch (NoResultException E) {
            System.out.println("There are no results from the query in Function AreBorderCountries" + E);
            return countriesXML;
        }
    }
    private List<Countries> GetListofBorderCountries(String nameEn1) {
        List<Countries>countriesInCountryTbl = new ArrayList<Countries>();
        try {
            javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn1);
            Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();

            q = getEntityManager().createNamedQuery("Bordercountries.findByCodeISO3", Bordercountries.class).setParameter("codeISO3", FullcountryOb1.getIsoAlpha3());
            List<Bordercountries>CountriesInBorderTbl = q.getResultList();
            
            Iterator<Bordercountries> iter = CountriesInBorderTbl.iterator();
            while (iter.hasNext()) {
                Bordercountries country = iter.next();
                q = getEntityManager().createNamedQuery("Countries.findByNameListEN", Countries.class).setParameter("nameListEN", country.getNameListEN());
                Countries CountryOb1 = (Countries) q.getSingleResult();
                countriesInCountryTbl.add(CountryOb1);
            }
            return countriesInCountryTbl;
           
        } catch (NoResultException E) {
            System.out.println("There are no results from the query in Function AreBorderCountries" + E);
            return countriesInCountryTbl;
        }
        
    }
    private boolean AreBorderCountries(String nameEn1, String nameEn2) {
        //First get code corresponding to the country name from fullCountryTable as user gives smaal names and counntry table has big names
        try {
            javax.persistence.Query q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn1);
            Fullcountries FullcountryOb1 = (Fullcountries) q.getSingleResult();
            List<Countries>BorderTonameEn1 = GetListofBorderCountries(nameEn1);

            q = getEntityManager().createNamedQuery("Fullcountries.findByCountryName", Fullcountries.class).setParameter("countryName", nameEn2);
            Fullcountries FullcountryOb2 = (Fullcountries) q.getSingleResult();

            Iterator<Countries> iter = BorderTonameEn1.iterator();
            while (iter.hasNext()) {
                Countries inCountryTable = (Countries) iter.next();
                if (inCountryTable.getCodeISO3().equalsIgnoreCase(FullcountryOb2.getIsoAlpha3())) {
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
            xmlOb = xmlOb.SetUnCode(unCodeOb.getCodeUN()).SetNameEn(fullCountryInfoOb.getCountryName()).SetCapitalName(fullCountryInfoOb.getCapital()).SetPopulation(populationOb.getPopulationTotal());
            xmlOb.SetGDP(gdpOb.getGDPTotalInCurrentPrices()).SetLandArea(landArea.getLandAreaTotal()).SetHDI(hdiOb.getTotal());
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
    
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

}
