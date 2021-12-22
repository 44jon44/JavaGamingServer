/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import entities.Game;
import entities.Platform;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;

/**
 *
 * @author ibai Arriola
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    /**
     * Este metodo busca por nombre todas las plataformas
     * @param name
     * @return list de platform
     * @throws Exception 
     */
    List<Platform> findPlatformsByName(String name) throws Exception {
        LOGGER.log(Level.INFO, "Metodo getSectorsByName de la clase AbstractFacade");
        try {
            return getEntityManager().createNamedQuery("findPlatformsByName")
                    .setParameter("name", name)
                    .getResultList();
        } catch (Exception e) {
            throw new Exception("Error when trying to get platforms by name");
        }
    }
}
