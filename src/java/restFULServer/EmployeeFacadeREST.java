/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restFULServer;

import entities.Employee;
import entities.UserPrivilege;
import java.util.List;
import java.util.logging.Level;
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
@Path("employee")
public class EmployeeFacadeREST extends AbstractFacade<Employee> {
    
    private static final Logger LOGGER = Logger.getLogger(EmployeeFacadeREST.class.getName());
    
    @PersistenceContext(unitName = "JavaGamingServerPU")
    private EntityManager em;

    public EmployeeFacadeREST() {
        super(Employee.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(Employee entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, Employee entity) {
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
    public Employee find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("fullName/{fullName}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> employeesByName(@PathParam("fullName") String fullName) {
        List<Employee> employeesByName = null;
        try {
            LOGGER.info("Filtrado por nombre");
            employeesByName = em.createNamedQuery("employeesByName")
                    .setParameter("fullName", fullName).setParameter("privilege", UserPrivilege.EMPLOYEE).getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar empleados por nombre{0}", ex.getLocalizedMessage());
            throw new InternalServerErrorException(ex);
        }
        return employeesByName;
    }
    @GET
    @Path("salario/{salary}")
    @Produces({MediaType.APPLICATION_XML})
    public List<Employee> orderEmployeeBySalary(@PathParam("salary") Float salary) {
        List<Employee> employeesByName = null;
        try {
            LOGGER.info("Orden por salario");
            employeesByName = em.createNamedQuery("orderEmployeeBySalary")
                    .getResultList();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error al ordenar empleados por salario{0}", ex.getLocalizedMessage());
            throw new InternalServerErrorException(ex);
        }
        return employeesByName;
    }
}
