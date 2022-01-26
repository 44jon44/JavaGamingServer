/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Esta clase se ocupa de generar una contraseña segura de 8 caracteres.
 * @author Alex Hurtado
 */
public class PasswordGenerator {

    private static final Logger LOG = Logger.getLogger(PasswordGenerator.class.getName());

    //private final static char[] SYMBOLS = "^$*.[]{}()?-\"!@#%&/\\,><':;|_~`".toCharArray();
    private final static char[] SYMBOLS = "$*@+-:;".toCharArray();
    private final static char[] LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private final static char[] UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private final static char[] NUMBERS = "0123456789".toCharArray();
    //private final static char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789^$*.[]{}()?-\"!@#%&/\\,><':;|_~`".toCharArray();
    private final static char[] ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$*@+-:;".toCharArray();
    private final static Random RAND = new SecureRandom();

    /**
     * Este método devuelve una contraseña segura de la longitud que se pasa como parámetro.
     * @param length longitud de la contraseña generada
     * @return contraseña segura de la longitud pasada como parámetro.
     */
    public static String getPassword(int length) {
        LOG.info("Generando password...");
        assert length >= 4;
        char[] password = new char[length];

        //establecemos los requisítos mínimos de loS carácteres que debe contener la contraseña
        password[0] = LOWERCASE[RAND.nextInt(LOWERCASE.length)];
        password[1] = UPPERCASE[RAND.nextInt(UPPERCASE.length)];
        password[2] = NUMBERS[RAND.nextInt(NUMBERS.length)];
        password[3] = SYMBOLS[RAND.nextInt(SYMBOLS.length)];

        //completamos el resto de la contraseña con carácteres aleatorios
        for (int i = 4; i < length; i++) {
            password[i] = ALL_CHARS[RAND.nextInt(ALL_CHARS.length)];
        }

        //los mezclamos
        for (int i = 0; i < password.length; i++) {
            int randomPosition = RAND.nextInt(password.length);
            char temp = password[i];
            password[i] = password[randomPosition];
            password[randomPosition] = temp;
        }
        //devolvemos una cadena con la contraseña generada
        return new String(password);
    }
}
