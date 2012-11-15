package service;

import database.entity.Countries;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
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
}
