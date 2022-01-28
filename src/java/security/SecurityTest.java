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
        String oldPass = "TA;zg0LG";
        String newPass = "abcd*1234";
        String oldPassHex = DatatypeConverter.printHexBinary(oldPass.getBytes());
        String newPassHex = DatatypeConverter.printHexBinary(newPass.getBytes());
        String hashedPass = "3b494128775ed980ab14b1364af78f7e1ed5ade61afba17cfcbf94b0b5cfe4f5";
        System.out.println(Hashing.getSHA256SecurePassword(oldPass, Hashing.SALT).equals(hashedPass));
        System.out.println(oldPassHex);
        System.out.println(newPassHex);
        
        String text = "Hola mundo";
        byte[] encryptedText = RSACipher.encrypt(text.getBytes());
        //System.out.println(new String(encryptedText));
        byte[] textDecrypted = RSACipher.decrypt(encryptedText);
        System.out.println(new String(textDecrypted));
    }
}
