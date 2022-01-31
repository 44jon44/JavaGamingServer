/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import entities.User;
import entities.UserPrivilege;
import exception.EmailNotFoundException;
import exception.WrongPasswordException;
import java.time.Instant;
import java.util.Date;
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
import mail.MailSender;
import mail.MailType;
import security.Hashing;
import security.RSACipher;
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
    @Path("login/{login}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByLogin(@PathParam("login") String login) {
        List<User> users = findAll(); 
        User user = null;
        user = users.stream().filter(u -> u.getLogin().equals(login)).findFirst().get();
        return user;
    }
    
    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public User findUserByEmail(@PathParam("email") String email) throws EmailNotFoundException {
        List<User> users = findAll(); 
        User user = users.stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
        if(user != null)
            return user;
        else
           throw new EmailNotFoundException();
    }
    
    @GET
    @Path("resetPasswd/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public User resetPassword(@PathParam("email") String email) throws EmailNotFoundException{
        User user = findUserByEmail(email);
        LOGGER.info(user.toString());
        MailSender.sendEmail(email, MailType.PASS_RESET);
        String tempPass = MailSender.getGeneratedPasswd();
        user.setPassword(Hashing.getSHA256SecurePassword(tempPass, Hashing.SALT));
        //edit(user.getIdUser(), user);
        em.merge(user);
        em.flush();
        return null;
    }
    
    @GET
    @Path("changePasswd/{email}/{oldPass}/{newPass}")
    @Produces({MediaType.APPLICATION_XML})
    public User changePassword(@PathParam("email") String email, @PathParam("oldPass") String oldPass, @PathParam("newPass") String newPass) throws WrongPasswordException, EmailNotFoundException{
        User user = findUserByEmail(email);
        if(user != null){
            //la contraseña antigua y la nueva llegan cifradas de forma asimétrica desde el cliente
            //primero desencriptamos las contraseñas antigua y nueva con la clave privada del servidor
            String oldPassStr = new String(RSACipher.decrypt(DatatypeConverter.parseHexBinary(oldPass)));
            String newPassStr = new String(RSACipher.decrypt(DatatypeConverter.parseHexBinary(newPass)));
            //Hasheamos la vieja contraseña del usuario y luego la comparamos con la recuperamos de user
            String oldPassHash = Hashing.getSHA256SecurePassword(oldPassStr, Hashing.SALT);
            if(oldPassHash.equals(user.getPassword())){
                //si la contraseña vieja y la que está almacenada en la BBDD son las mismas actualizamos la contraseña
                user.setPassword(Hashing.getSHA256SecurePassword(newPassStr, Hashing.SALT));
                //actualizamos la fecha en la que se ha cambiado la contraseña
                user.setLastPasswordChange(Date.from(Instant.now()));
                //edit(user.getIdUser(), user);
                em.merge(user);
                em.flush();
                //informamos al usuario de que ha cambiado su contraseña
                MailSender.sendEmail(email, MailType.PASS_CHANGE);
            }else{
                //si no coinciden las contraseñas lanzamos una excepción para informar al usuario
                throw new WrongPasswordException();
            }
        }
        return null;
    }

    @GET
    @Path("login/{login}/password/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<User> checkLogin(@PathParam("login") String login, @PathParam("password") String password) {
        List<User> users = null;
        try {
            LOGGER.info("Getting the login information");
            //Decipher pasword
          
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
