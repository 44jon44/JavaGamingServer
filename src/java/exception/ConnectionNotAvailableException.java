/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;
/**
 *Excepcion que se señala que no hay conexiones disponibles
 * @author Ibai,jon,markel,alex
 */
public class ConnectionNotAvailableException extends Exception {

    /**
     * Crea una instancia de <code>ConnectionNotAvailableException</code> sin mensaje de
     * detalle.
     */
    public ConnectionNotAvailableException() {
        super("No se ha podido establecer conexión");
    }
}
