/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 * Excepción que señala que el e-mail introducido no existe.
 * @author Alex Hurtado
 */
public class EmailNotFoundException extends Exception{
    /**
     * Crea una instancia de <code>EmailNotFoundException</code> sin mensaje de
     * detalle.
     */
    public EmailNotFoundException() {
        super("El email introducido no existe");
    }
}
