package security;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
* Esta clase permite descifrar un texto, que se ha cifrado previamente con una <b>clave secreta</b>, que está
 * guardado en un fichero. (Cifrado simétrico)
 * 
 *  En este caso vamos a utilizar:
 * <ul>
 * <li>El algoritmo AES</li>
 * <li>El modo CBC: Existen dos, el ECB que es sencillo, y el CBC que necesita
 * un vector de inicialización(IV)</li>
 * <li>El padding PKCS5Padding (128): Si el mensaje no es múltiplo de la
 * longitud del algoritmo se necesita un relleno.</li>
 * </ul>
 * @author Alex Hurtado
 *
 */
public class AESDecipher {
    private static final ResourceBundle RB = ResourceBundle.getBundle("security.aes");
    private static final byte[] SALT = RB.getString("SALT").getBytes();
    private static final String PASS = RB.getString("PASS");

    /**
     * Descifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y
     * lo retorna
     *
     * @param clave La clave del usuario
     */
    public static String decipherText(String filePath) {
    	
        String decodedText = null;
        // Leemos el contenido del fichero
        byte[] fileContent = fileReader(filePath); 
        KeySpec keySpec = null;
        SecretKeyFactory secretKeyFactory = null;
        try
        {
            // Obtenemos el keySpec
            keySpec = new PBEKeySpec(PASS.toCharArray(), SALT, 65536, 128); // AES-128

            // Obtenemos una instancide de SecretKeyFactory con el algoritmo "PBKDF2WithHmacSHA1"
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            
            // Generamos la clave
            byte[] key = secretKeyFactory.generateSecret(keySpec).getEncoded();

            // Creamos un SecretKey usando la clave + salt
            SecretKey privateKey = new SecretKeySpec(key, 0, key.length, "AES");
            
            // Obtenemos una instancide de Cipher con el algoritmos que vamos a usar "AES/CBC/PKCS5Padding"
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // Leemos el fichero codificado 
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));

            // Iniciamos el Cipher en DECRYPT_MODE y le pasamos la clave privada y el ivParam
            cipher.init(Cipher.DECRYPT_MODE, privateKey, ivParam);
            // Le decimos que descifre
            byte[] decodedBytes = cipher.doFinal(Arrays.copyOfRange(fileContent, 16, fileContent.length));

            // Texto descifrado
            decodedText = new String(decodedBytes);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return decodedText;
    }

    /**
     * Retorna el contenido de un fichero
     *
     * @param path Path del fichero
     * @return El texto del fichero
     */
    private static byte[] fileReader(String path) {
        byte ret[] = null;
        File file = new File(path);
        try
        {
            ret = Files.readAllBytes(file.toPath());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public static void main(String[] args) {
       
        String filePath = "./src/mail/mailUser.dat";
        //System.out.println("Cifrado! -> " + mensajeCifrado);
        System.out.println("-----------");
        System.out.println("Descifrado! -> " + AESDecipher.decipherText(filePath));
        System.out.println("-----------");
        filePath = "./src/mail/mailPasswd.dat";
        System.out.println("Descifrado! -> " + AESDecipher.decipherText(filePath));
    }
}
