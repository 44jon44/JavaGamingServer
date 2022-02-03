/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javax.xml.bind.DatatypeConverter;
import security.AESDecipher;
import security.Hashing;

/**
 *
 * @author Alex Hurtado
 */
public class TestMail {
    private static final ResourceBundle RB = ResourceBundle.getBundle("mail.mail");
    private static final String MAIL_USER= "./src/java/mail/mailUser.dat";
    private static final String MAIL_PASS= "./src/java/mail/mailPasswd.dat";
    public static void main(String[] args){
        //MailSender.sendEmail("lexurt@gmail.com", MailType.PASS_RESET);
//        System.out.println(DatatypeConverter.printHexBinary(fileReader(MAIL_PASS)));
//       System.out.println(DatatypeConverter.printHexBinary(fileReader(MAIL_USER)));
//        
       System.out.println(AESDecipher.decipherText(RB.getString("MAIL_USER")));
 	    System.out.println(AESDecipher.decipherText(RB.getString("MAIL_PASS")));
            
        System.out.println("71abdbde15449d127bcae56b906ecb5c0b97e24775748d5bed00eb6e08a46997"
                .equals(Hashing.getSHA256SecurePassword("0R*SVJ5q", Hashing.SALT)));
        System.out.println(Hashing.getSHA256SecurePassword("0R*SVJ5q", Hashing.SALT));
    }
    
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return sb.toString();
    }
    
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
}
