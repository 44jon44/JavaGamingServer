/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import entities.Client;
import entities.UserPrivilege;
import entities.UserStatus;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ibai Arriola
 */
@Stateless
@Path("client")
public class ClientFacadeREST extends AbstractFacade<Client> {

    private static final Logger LOG = Logger.getLogger(ClientFacadeREST.class.getName());

    @PersistenceContext(unitName = "JavaGamingServerPU")
    private EntityManager em;

    public ClientFacadeREST() {
        super(Client.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Client entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Client entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Client find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Client> findAll() {
        LOG.info("Cargando clientes");
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Client> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    //Consultas personalizadas
    @GET
    @Path("find/fullName/{fullName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Client> findClientByFullName(@PathParam("fullName") String fullName) {
        List<Client>  clientsByFullName = null;
        clientsByFullName = em.createNamedQuery("findClientByFullName").setParameter("fullName", fullName).setParameter("privilege", UserPrivilege
        .CLIENT).getResultList();
        return clientsByFullName;
    }
    
    @GET
    @Path("find/id/{idClient}")
    @Produces({MediaType.APPLICATION_XML})
    public Client findClientById(@PathParam("idClient") Integer idClient) {
        Client client;
        client = (Client)em.createNamedQuery("findClientById").setParameter("idClient", idClient).setParameter("privilege", UserPrivilege.CLIENT).getResultList().get(0);
        return client;
    }
    
    @GET
    @Path("find/login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public Client findClientByLogin(@PathParam("login") String login) {
        Client client;
        client = (Client)em.createNamedQuery("findClientByLogin").setParameter("login", login).setParameter("privilege", UserPrivilege.CLIENT).getResultList().get(0);
        return client;
    }
    
    @GET
    @Path("find/email/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public Client findClientByEmail(@PathParam("email") String email) {
        Client client;
        client = (Client)em.createNamedQuery("findClientByEmail").setParameter("email", email).setParameter("privilege", UserPrivilege.CLIENT).getResultList().get(0);
        return client;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
