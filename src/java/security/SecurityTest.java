/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import javax.xml.bind.DatatypeConverter;
import javax.xml.datatype.DatatypeConstants;

/**
 *
 * @author Alex Hurtado
 */
public class SecurityTest {
    public static void main(String[] args){

        String oldPass = "abcd*1234";
        String newPass = "Abcd*1234";
        System.out.println(Hashing.getSHA256SecurePassword(oldPass, Hashing.SALT));
        
        String oldPassHex = DatatypeConverter.printHexBinary(oldPass.getBytes());
        String newPassHex = DatatypeConverter.printHexBinary(newPass.getBytes());
        String hashedPass = "56127fecb4c2c943ead237281290f7634513551a30a6c07af0e9c03668e7fb93";
        System.out.println(Hashing.getSHA256SecurePassword(oldPass, Hashing.SALT).equals(hashedPass));
        System.out.println(oldPassHex);
        System.out.println(newPassHex);
        
        String oldPassRSA = "abcd*1234";
        String newPassRSA = "Abcd*1234";
        byte[] encryptedOldPass = RSACipher.encrypt(oldPassRSA.getBytes());
        System.out.println(encryptedOldPass.length);
        System.out.println(DatatypeConverter.printHexBinary(encryptedOldPass));
        byte[] encryptedNewPass = RSACipher.encrypt(newPassRSA.getBytes());
        System.out.println(DatatypeConverter.printHexBinary(encryptedNewPass));
        //System.out.println(new String(encryptedText));
        byte[] textDecrypted = RSACipher.decrypt(encryptedOldPass);
        System.out.println(new String(textDecrypted));
        textDecrypted = RSACipher.decrypt(encryptedNewPass);
        System.out.println(new String(textDecrypted));
      
        String pass = "abcd*1234";
        String hashPass = Hashing.getSHA256SecurePassword(pass, Hashing.SALT);
        System.out.println(hashPass);
    }
}
