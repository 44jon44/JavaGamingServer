/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import entities.Client;
import entities.Game;
import entities.IdPurchase;
import entities.Purchase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author ibai Arriola
 */
@Stateless
@Path("purchase")
public class PurchaseFacadeREST extends AbstractFacade<Purchase> {

    private static final Logger LOG = Logger.getLogger(PurchaseFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "JavaGamingServerPU")
    private EntityManager em;

    private IdPurchase getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;idGame=idGameValue;idClient=idClientValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entities.IdPurchase key = new entities.IdPurchase();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> idGame = map.get("idGame");
        if (idGame != null && !idGame.isEmpty()) {
            key.setIdGame(new java.lang.Integer(idGame.get(0)));
        }
        java.util.List<String> idClient = map.get("idClient");
        if (idClient != null && !idClient.isEmpty()) {
            key.setIdClient(new java.lang.Integer(idClient.get(0)));
        }
        return key;
    }

    public PurchaseFacadeREST() {
        super(Purchase.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Purchase entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, Purchase entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entities.IdPurchase key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Purchase find(@PathParam("id") PathSegment id) {
        entities.IdPurchase key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("find/id/{idClient}/{idGame}")
    @Produces({MediaType.APPLICATION_XML})
    public Purchase findPurchaseById(@PathParam("idClient") Integer idClient,@PathParam("idGame") Integer idGame) {
        Purchase purchaseById;
        purchaseById = (Purchase) em.createNamedQuery("findPurchaseById").setParameter("idClient", idClient).setParameter("idGame", idGame).getResultList().get(0);
        return purchaseById;
    }
    
    @GET
    @Path("find/idClient/{idClient}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByClientId(@PathParam("idClient") Integer idClient) {
        List<Purchase>  purchasesByClientId;
        purchasesByClientId = em.createNamedQuery("findPurchasesByClientId").setParameter("idClient", idClient).getResultList();
        return purchasesByClientId;
    }
    
    @GET
    @Path("find/date/{purchaseDate}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByPurchaseDate(@PathParam("purchaseDate") String purchaseDate) {
        List<Purchase>  purchasesByPurchaseDate = null;
        try {
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            
            purchasesByPurchaseDate = em.createNamedQuery("findPurchasesByPurchaseDate").setParameter("purchaseDate", purDate).getResultList();
            
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchasesByPurchaseDate;
    }
    
    @GET
    @Path("find/price/{price}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByPrice(@PathParam("price") Float price) {
        LOG.info("findPurchasesByPrice(); IN");
        List<Purchase>  purchasesByPrice;  
        
        purchasesByPrice = em.createNamedQuery("findPurchasesByPrice").setParameter("price", price).getResultList();
        LOG.log(Level.INFO, "Purchases find: {0}", purchasesByPrice.size());
        return purchasesByPrice;
    }
    
    @GET
    @Path("find/{idClient}/{purchaseDate}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByClientAndPurchaseDate(@PathParam("idClient") Integer idClient, @PathParam("purchaseDate") String purchaseDate) {
        List<Purchase>  purchasesByClientAndPurchaseDate = null;
        try {
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            purchasesByClientAndPurchaseDate = findPurchasesByClientId(idClient).stream().filter(p -> p.getPurchaseDate().compareTo(purDate) == 0).collect(Collectors.toList());         
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchasesByClientAndPurchaseDate;
    }
    
    @GET
    @Path("findPurchases/{idClient}/{minPrice}/{maxPrice}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByClientAndPriceRange(@PathParam("idClient") Integer idClient, @PathParam("minPrice") Float minPrice, @PathParam("maxPrice") Float maxPrice) {
        List<Purchase>  purchasesByClientAndPriceRange;
        purchasesByClientAndPriceRange = findPurchasesByClientId(idClient).stream().filter(p -> p.getGame().getPrice() >= minPrice && p.getGame().getPrice() <= maxPrice).collect(Collectors.toList());         
        return purchasesByClientAndPriceRange;
    }
    
    @GET
    @Path("find/{purchaseDate}/{minPrice}/{maxPrice}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByPurDateAndPriceRange(@PathParam("purchaseDate") String purchaseDate, @PathParam("minPrice") Float minPrice, @PathParam("maxPrice") Float maxPrice) {
        List<Purchase>  purchasesByPurDateAndPriceRange = null;
        try {
            
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            purchasesByPurDateAndPriceRange = findAll().stream().filter(p -> p.getPurchaseDate().compareTo(purDate) == 0 && p.getGame().getPrice() >= minPrice && p.getGame().getPrice() <= maxPrice).collect(Collectors.toList());
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchasesByPurDateAndPriceRange;
    }
    
    @GET
    @Path("find/idClient/{idClient}/{purchaseDate}/{minPrice}/{maxPrice}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Purchase> findPurchasesByClientAndPurDateAndPriceRange(@PathParam("idClient") Integer idClient,@PathParam("purchaseDate") String purchaseDate, @PathParam("minPrice") Float minPrice, @PathParam("maxPrice") Float maxPrice) {
        List<Purchase>  purchasesByClientAndPurDateAndPriceRange = null;
        try {
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            purchasesByClientAndPurDateAndPriceRange = findPurchasesByClientId(idClient).stream().filter(p -> p.getPurchaseDate().compareTo(purDate) == 0 && p.getGame().getPrice() >= minPrice && p.getGame().getPrice() <= maxPrice).collect(Collectors.toList());
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchasesByClientAndPurDateAndPriceRange;
    }
    
    @DELETE
    @Path("deletePurchase/{idClient}/{idGame}")
    public void deletePurchase(@PathParam("idClient") Integer idClient, @PathParam("idGame") Integer idGame) {
        LOG.info("Borrando compra...");
        //por las claves foraneas primero debemos borrar las entradas en las tablas g5reto2.client_purchase y g5reto2.game_purchase
        String deleteClientPurchaseQuery = String.format("DELETE FROM g5reto2.client_purchase WHERE purchases_client_idUser = %d AND purchases_game_idGame = %d",idClient,idGame);
        em.createNativeQuery(deleteClientPurchaseQuery).executeUpdate();
        String deleteGamePurchaseQuery = String.format("DELETE FROM g5reto2.game_purchase WHERE purchases_client_idUser = %d AND purchases_game_idGame = %d",idClient,idGame);
        em.createNativeQuery(deleteGamePurchaseQuery).executeUpdate();
        em.createNamedQuery("deletePurchase").setParameter("idClient", idClient).setParameter("idGame", idGame).executeUpdate();
    }
    
    @GET
    @Path("updatePurchase/{idClient}/{idGame}/{purchaseDate}")
    public Purchase updatePurchase(@PathParam("idClient") Integer idClient, @PathParam("idGame")Integer idGame,@PathParam("purchaseDate") String purchaseDate){
        Purchase purchase = null;
        try {
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            purchase = findPurchaseById(idClient, idGame);
            purchase.setPurchaseDate(purDate);
            em.merge(purchase);
            em.flush();
        } catch (ParseException ex) {
            Logger.getLogger(PurchaseFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return purchase;
    }
    
    @GET
    @Path("createPurchase/{idClient}/{idGame}/{purchaseDate}")
    @Consumes({MediaType.APPLICATION_XML})
    public Purchase createPurchase(@PathParam("idClient") Integer idClient, @PathParam("idGame")Integer idGame,@PathParam("purchaseDate") String purchaseDate) {
        Purchase purchase = null;
        try
        {
            purchase = new Purchase();
            Client client = em.find(Client.class, idClient);
            Game game = em.find(Game.class, idGame);
            Date purDate=new SimpleDateFormat("yyyy-MM-dd").parse(purchaseDate);
            purchase.setClient(client);
            purchase.setGame(game);
            purchase.setPurchaseDate(purDate);
            purchase.setIdPurchase(new IdPurchase(idClient, idGame));
            super.create(purchase);
        } catch (ParseException ex)
        {
            LOG.log(Level.SEVERE, "La fecha no tiene el formato adecuado. El formato correcto es: yyyy-MM-dd", ex);
        }
        return purchase;
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
