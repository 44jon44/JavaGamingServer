/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ibai Arriola, Alex Hurtado
 */
@Entity
@Table(name = "purchase", schema = "g5reto2")
@XmlRootElement
@NamedQueries({
    //RUD
    @NamedQuery(name = "findPurchaseById", query = "SELECT p FROM Purchase p WHERE p.idPurchase.idClient = :idClient AND p.idPurchase.idGame = :idGame"
    ),
    @NamedQuery(name = "deletePurchase", query = "DELETE FROM Purchase p WHERE p.idPurchase.idClient = :idClient AND p.idPurchase.idGame = :idGame"
    ),
    @NamedQuery(name = "findPurchasesByClientId", query = "SELECT p FROM Purchase p WHERE p.idPurchase.idClient =:idClient"
    ),
    @NamedQuery(name = "findPurchasesByPurchaseDate", query = "SELECT p FROM Purchase p WHERE p.purchaseDate = :purchaseDate"
    ),
    @NamedQuery(name = "findPurchasesByPrice", query = "SELECT p FROM Purchase p WHERE p.game.price = :price"
     )
})
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private IdPurchase idPurchase;
/**
 * Fecha en la que se produjo la compra
 */
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;
/**
 * id del cliente que hizo la compra
 */
    @MapsId("idClient")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Client client;
/**
 * id del juego que se compro
 */
    @MapsId("idGame")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Game game;
   
   //Metodos getters y setters 
    public IdPurchase getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(IdPurchase idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPurchase != null ? idPurchase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.idPurchase == null && other.idPurchase != null) || (this.idPurchase != null && !this.idPurchase.equals(other.idPurchase))) {
            return false;
        }
        return true;
    }
/**
 * 
 * @return devuelve los datos 
 */
    @Override
    public String toString() {
        return "Purchase{" + "idPurchase=" + idPurchase + ", purchaseDate=" + purchaseDate + ", client=" + client + ", game=" + game + '}';
    }

}
