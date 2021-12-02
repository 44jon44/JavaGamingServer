/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;
/**
 *Excepcion que se señala que el usuario no esta registrado
* @author Ibai,jon,markel,alex
 */
public class LoginNotFoundException extends Exception {

    /**
     * Crea una instancia de <code>LoginNotFoundException</code> sin mensaje de
     * detalle.
     */
    public LoginNotFoundException() {
        super("El usuario introducido no existe");
    }
}
