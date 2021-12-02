/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ibai Arriola
 */
@Entity
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPurchase;
    private Game game;
    private Date purchaseDate;
   //private Client client;

    public Long getIdPurchase() {
        return idPurchase;
    }
/*
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
*/
    public void setIdPurchase(Long idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
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

}
