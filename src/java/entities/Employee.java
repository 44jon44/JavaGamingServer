/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jon
 */
@Entity
@DiscriminatorValue("EMPLOYEE")
@XmlRootElement
public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha en la que el empleado fue contratado
     */
    @Temporal(TemporalType.DATE)
    private Date hiringDate;

    /**
     * Salario que recibe el empleado
     */
    private Float salary;
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "g5reto2", name = "game_employee")
    private Set<Game> games;

    /**
     * Método que devuelve la fecha en la que fue contratado el empleado
     *
     * @return hiringDate
     */
    public Date getHiringDate() {
        return hiringDate;
    }

    /**
     * Metodo que establece la fecha de de cntratacion del empleado
     *
     * @param hiringDate
     */
    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    /**
     * Método que devuelve el salario que recibe el empleado
     *
     * @return hiringDate
     */
    public Float getSalary() {
        return salary;
    }

    /**
     * Metodo que establece el salario que recibe el empleado
     *
     * @param salary
     */
    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Employee other = (Employee) obj;
        return Objects.equals(this.getIdUser(), other.getIdUser());
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + "hiringDate=" + hiringDate + ", salary=" + salary + '}';
    }
}
