/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

/**
 *
 * @author Alex Hurtado
 */
public class SecurityTest {
    public static void main(String[] args){
        String pass = "abcd*1234";
        String hashPass = Hashing.getSHA256SecurePassword(pass, Hashing.SALT);
        System.out.println(hashPass);
    }
}
