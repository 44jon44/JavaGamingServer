/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se utiliza para hashear las contraseñas de los usuarios
 * antes de almacenarla en la base de datos.
 * @author Alex Hurtado, Ibai Arriola
 */
public class Hashing {
    //Logger de clase Hashing
    private static final Logger LOG = Logger.getLogger(Hashing.class.getName());

    /**
     * Este método se encarga de hashear la contraseña. Se utiliza también un salt para añadir más seguridad.
     * @param passwordToHash Password que queremos hashear.
     * @param salt  String hexadecimal aleatoria para añadir más seguridad al hashing.
     * @return Password hasheada.
     */
    public static String getSHA256SecurePassword(String passwordToHash,
            String salt) {
        String generatedPassword = null;
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            generatedPassword = bytesToHexString(bytes);
        } catch (NoSuchAlgorithmException ex)
        {
            LOG.log(Level.SEVERE, null, ex.getMessage());
        }
        return generatedPassword;
    }

    /**
     * Este método genera un salt para añadir más seguridad al algoritmo de
     * resumen.
     *
     * @return El salt generado
     * @throws NoSuchAlgorithmException
     */
    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    
    /**
     * Este método convierte el array de bytes generado por la instancia de la clase MessageDigest
     * en una cadena hexadecimal.
     * @param bytes Array de bytes generado por la instancia de MessageDigest.
     * @return String hexadecimal.
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }
}
