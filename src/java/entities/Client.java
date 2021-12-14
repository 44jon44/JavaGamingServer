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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase Client
 *
 * @author Alex Hurtado
 */
@Entity
@Table(name = "client", schema = "g5reto2")
@DiscriminatorValue("client")
@XmlRootElement
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Fecha en la que se ha dado de alta el cliente.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date signUpDate;

    @OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "g5reto2", name = "client_purchase")
    private Set<Purchase> purchases;

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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Client other = (Client) obj;
        return Objects.equals(this.getIdUser(), other.getIdUser());
    }

    @Override
    public String toString() {
        return "Client{" + super.toString() + "signUpDate=" + signUpDate + '}';
    }
}
