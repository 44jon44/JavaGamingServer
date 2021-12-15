/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Markel Lopez de Uralde
 */
@Entity
@Table(name = "platform", schema = "g5reto2")
@XmlRootElement
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Clave unica autogenerada para la plataforma
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPlatform;
    /**
     * Nombre de la plataforma
     */
    private String name;
    /**
     * Fecha de salida de la plataforma
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date realizeDate;
    /**
     * Relacion N:M con la clase Game
     */
    @ManyToMany(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinTable(schema = "g5reto2", name = "game_platform")
    private Set<Game> games;

    public Integer getIdPlatform() {
        return idPlatform;
    }

    public void setIdPlatform(Integer idPlatform) {
        this.idPlatform = idPlatform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getRealizeDate() {
        return realizeDate;
    }

    public void setRealizeDate(Date realizeDate) {
        this.realizeDate = realizeDate;
    }

    public Set<Game> getGames() {
        return games;
    }

    /**
     * Getters y setters
     */
    public void setGames(Set<Game> games) {   
        this.games = games;
    }

    /**
     * Hascode con el atributo id de la plataforma
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlatform != null ? idPlatform.hashCode() : 0);
        return hash;
    }

    /**
     * Equals para comparar si dos objetos platform son iguales
     *
     * @return un true en el caso de que los objetos sean iguales
     * @param object el objeto a comparar
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Platform)) {
            return false;
        }
        Platform other = (Platform) object;
        if ((this.idPlatform == null && other.idPlatform != null) || (this.idPlatform != null && !this.idPlatform.equals(other.idPlatform))) {
            return false;
        }
        return true;
    }

    /**
     * Este método devuelve una representación de string para una entidad
     * Platform
     *
     * @return un string que representación para el objeto Platform
     */
    @Override
    public String toString() {
        return "Platform{" + "idPlatform=" + idPlatform + ", name=" + name + ", realizeDate=" + realizeDate + '}';
    }
}
