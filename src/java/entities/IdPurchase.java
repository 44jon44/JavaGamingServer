/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author ibai Arriola
 */
@Embeddable
@XmlRootElement
public class IdPurchase implements Serializable {

    private static final long serialVersionUID = 1L;
    // la id de de un juego
    private Integer idGame;
    // la id de de un  cliente
    private Integer idClient;
    //getter y setter
    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGame != null ? idGame.hashCode() : 0);
        hash += (idClient != null ? idClient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IdPurchase)) {
            return false;
        }
        IdPurchase other = (IdPurchase) object;
        if ((this.idGame == null && other.idGame != null) || (this.idGame != null && !this.idGame.equals(other.idGame))) {
            return false;
        }
        if ((this.idClient == null && other.idClient != null) || (this.idClient != null && !this.idClient.equals(other.idClient))) {
            return false;
        }
        return true;
    }
// muestra la salida  de  idpurchase
    @Override
    public String toString() {
        return "IdPurchase{" + "idGame=" + idGame + ", idClient=" + idClient + '}';
    }

    

}
