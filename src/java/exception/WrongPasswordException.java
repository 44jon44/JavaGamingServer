/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 * Excepción que señala que un usuario ha introducido mal su contraseña a la hora de cambiarla.
 * @author Alex Hurtado
 */
public class WrongPasswordException extends Exception{
    /**
     * Crea una instancia de <code>WrongPasswordException</code> sin mensaje de
     * detalle.
     */
    public WrongPasswordException() {
        super("La contraseña introducida no es correcta");
    }
}
