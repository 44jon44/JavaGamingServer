/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import entities.Game;
import java.util.List;

import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
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
@Path("game")
public class GameFacadeREST extends AbstractFacade<Game> {

    private static final Logger LOGGER = Logger.getLogger(GameFacadeREST.class.getName());

    @PersistenceContext(unitName = "JavaGamingServerPU")
    private EntityManager em;

    public GameFacadeREST() {
        super(Game.class);
    }
//aqui empiezan los diferntes metodos

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Game entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Game entity) {
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
    public Game find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Game> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Game> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

////////////////////////////////////////////////////////////////////////////////
    @GET
    @Path("genre/{genre}/{name}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Game> findGamebyGenre(@PathParam("genre") String genre,@PathParam("name") String name) {
        List<Game> gamesbyGenre = null;
        try {
            LOGGER.info("filtrado por genero");
            gamesbyGenre = em.createNamedQuery("findGamebyGenre")
                    .setParameter("genre", genre).setParameter("name", name).getResultList();
        } catch (Exception ex) {
            LOGGER.severe("error al listar juegos por genero."
                    + ex.getLocalizedMessage());
            throw new InternalServerErrorException(ex);
        }
        return gamesbyGenre;
    }

    @GET
    @Path("pegi/{pegi}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Game> findGamebyPegi(@PathParam("pegi") Integer pegi) {
        List<Game> gamesbyPegi = null;
        try {
            LOGGER.info("filtrado por genero");
            gamesbyPegi = em.createNamedQuery("findGamebyPegi")
                    .setParameter("pegi", pegi).getResultList();
        } catch (Exception ex) {
            LOGGER.severe("error al listar juegos por genero."
                    + ex.getLocalizedMessage());
            throw new InternalServerErrorException(ex);
        }
        return gamesbyPegi;
    }

////////////////////////////////////////////////////////////////////////////////
}
