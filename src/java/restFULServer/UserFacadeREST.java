/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import entities.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import security.Hashing;
import security.RSACipherServer;

/**
 *
 * @author ibai Arriola
 */
@Stateless
@Path("user")
public class UserFacadeREST extends AbstractFacade<User> {

    private static final Logger LOGGER = Logger.getLogger(UserFacadeREST.class.getName());
    @PersistenceContext(unitName = "JavaGamingServerPU")
    private EntityManager em;

    public UserFacadeREST() {
        super(User.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(User entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    /**
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    @GET
    @Path("login/{login}/password/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> checkLogin(@PathParam("login") String login, @PathParam("password") String password) {
        List<User> users = null;
        try {
            LOGGER.info("Getting the login information");
            //Decipher pasword
            
            //byte[] cipherByte = DatatypeConverter.parseHexBinary("C2543B53ED3208E4C3EB42D62D52D4954A900EFA7DAAD280BF58CC18566AD96C3DF9775C6B2F67C42E5BBF3EB9C5626C8962E0BB2E0E6F319F89B3CADF9899D51BD42CD27870267134DDB5E81CFDA28BBAA0E69085E475555B1E0CDF07CFCC684B09F5A4E83B6951C99828709196A945A81BEA1ADAD42B8BD744250A15C7BDEF0E32A5B32F93380C383379B030B29124726A2FE4C1879AA6D9647AE7972D3982E1EAEF517CC7D3BCC9712C2512C69766BE8120DE1B5743B888D4B9FE1CBAB3D2A12D1BD495D5B9E0F23F2FEC953072CA07CA9B94BAF5D3CDE3FC58E040E2DD0390C5173065C78AAE711AB937ED1CDD95AD9928A1CE62F7811C71B85C6764770F");
            
            byte[] cipherByte=DatatypeConverter.parseHexBinary(password);
            
            byte[] decipheredPassword = RSACipherServer.decrypt(cipherByte);
            
            
            //Hash password       
            String hashedPassword = Hashing.getSHA256SecurePassword(new String(decipheredPassword), Hashing.SALT);
            System.out.println(hashedPassword);

            users = em.createNamedQuery("checkLogin").setParameter("login", login).setParameter("password", hashedPassword).getResultList();
            //Take all the last signins of a user to the persistance context
            //SELECT l FROM LastSignIn l WHERE l.user =(SELECT u FROM User u WHERE u.login= :login) ORDER BY l.lastSignIn ASC 
            /*List<LastSignIn> lastSignIns = new ArrayList<>();
            lastSignIns = (ArrayList) em.createNamedQuery("findByUserLogin").setParameter("user", user).getResultList();

            //If they signed in less than 10 times, a new sign in is added
            if (lastSignIns.size() < 10) {
                LastSignIn lastSignIn = new LastSignIn();
                lastSignIn.setId(null);
                lastSignIn.setLastSignIn(new Date());
                lastSignIn.setUser(user);
                em.persist(lastSignIn);
            } else {
                //If they signed in more than 10 times, the sign in with the minimum date is updated
                LastSignIn lis = lastSignIns.get(0);
                lis.setLastSignIn(new Date());
                em.merge(lis);
            }*/

        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            throw new NotAuthorizedException(e);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "UserEJB --> login():{0}", e.getLocalizedMessage());
            //Throw new read exception
        }
        return users;
    }

}
