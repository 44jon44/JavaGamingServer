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
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * entidad cliente  que es extiende de la entidad user
 * @author Alex Hurtado
 */
@Entity
@DiscriminatorValue("CLIENT")
@XmlRootElement
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Fecha en la que se ha dado de alta el cliente.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date signUpDate;

    /**
     * lista de compras reliazada por el cliente
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "g5reto2", name = "client_purchase")
    private Set<Purchase> purchases;

    
    //getters y setters de la entidad cliente//
    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
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
        final Client other = (Client) obj;
        return Objects.equals(this.getIdUser(), other.getIdUser());
    }
/**
 *  
 * @return retorna los datos de un cliente
 */
    @Override
    public String toString() {
        return "Client{" + super.toString() + "signUpDate=" + signUpDate + '}';
    }
}
