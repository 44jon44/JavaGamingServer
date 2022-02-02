/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Clase de la que heredan Client y Employee.
 *
 * @author Alex Hurtado
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "user", schema = "g5reto2")
@DiscriminatorValue("ADMIN")
@DiscriminatorColumn(name="privilege")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findUserByLogin",
            query = "SELECT u FROM User u WHERE u.login = :login"),
    @NamedQuery(name = "findUserByEmail",
            query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "checkLogin", query = "SELECT u FROM User u WHERE u.login =:login AND u.password =:password")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * ID del usuario.
     */
    private Integer idUser;

    /**
     * Login del usuario.
     */
    private String login;

    /**
     * Email del usuario.
     */
    private String email;

    /**
     * Nombre completo del usuario.
     */
    private String fullName;

    /**
     * Status del usuario.
     */
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    /**
     * Privilegio del usuario.
     */
    @NotNull
    @Column(insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UserPrivilege privilege;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Timestamp del último cambio de contraseña del usuario.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChange;

    /**
     *
     * Metodos getter y setter
     */
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }
    @XmlTransient
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }
    @XmlTransient
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * Representación entera de una instancia de User.
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.idUser);
        return hash;
    }

    /**
     * Compara dos objetos User para ver si son iguales.
     *
     * @param obj El otro objeto User con el que comparar.
     * @return
     */
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
        final User other = (User) obj;
        return Objects.equals(this.idUser, other.idUser);
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", login=" + login + ", email=" + email + ", fullName=" + fullName + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + '}';
    }
}
