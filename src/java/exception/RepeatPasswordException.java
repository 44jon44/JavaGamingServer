/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 * Excepcion que se señala que las contraseñas no coinciden
 *
 * @author Ibai,jon,markel,alex
 */
public class RepeatPasswordException extends Exception {

    /**
     * Crea una instancia de <code>RepeatPasswordException</code> sin mensaje de
     * detalle.
     */
    public RepeatPasswordException() {
        super("Las contraseñas no coinciden");
    }
}
