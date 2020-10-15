/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mono;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Mono {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //it will shuffle the alphabates which will be our new random key
        String sKey = scramble(sAlpha);

        Scanner scn = new Scanner(System.in);

        System.out.println("Enter Plain Text:");
        String sPlainText = scn.nextLine();

        String sCipherText = "";
        sCipherText = monoEncrypt(sPlainText, sKey);

        String sDecryptedText = "";
        sDecryptedText = monoDecrypt(sCipherText.toLowerCase(), sKey);

        System.out.println("Alpha:\t\t\t" + "abcdefghijklmnopqrstuvwxyz");
        System.out.println("Map:\t\t\t" + "||||||||||||||||||||||||||");
        System.out.println("Key:\t\t\t" + sKey);
        System.out.println("Plain Text:\t\t\t" + sPlainText);
        System.out.println("Cipher Text:\t\t\t" + sCipherText);
        System.out.println("Decrypted Text:\t\t\t" + sDecryptedText);

    }

    public static String monoEncrypt(String sPText, String sKey) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //String for storing ciphertext
        String sCText = "";

        for (int iVar1 = 0; iVar1 < sPText.length(); iVar1++) {

            //finding index of char in alpha
            int iIndex = sAlpha.indexOf(sPText.charAt(iVar1));

            //appending char by char in ciphertext string
            //in ceaser i have used char array
            sCText += sKey.charAt(iIndex);
        }
        return sCText.toUpperCase();
    }

    public static String monoDecrypt(String sCText, String sKey) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //for storing DecryptedText/PlainText
        String sPText = "";

        for (int iVar1 = 0; iVar1 < sCText.length(); iVar1++) {
            //finding index of ciphertext char in key
            int iIndex = sKey.indexOf(sCText.charAt(iVar1));

            //appending char by char in ciphertext string
            //in ceaser i have used char array
            sPText += sAlpha.charAt(iIndex);
        }
        return sPText;
    }

    public static String scramble(String sAlpha) {
        // Convert default alphabates to character array
        char sKey[] = sAlpha.toCharArray();
        Random rRandom = new Random();

        // Scramble using Fisher-Yates shuffle, 
        for (int i = 0; i < sKey.length; i++) {
            int j = rRandom.nextInt(sKey.length);
            //swapping
            char cTemp = sKey[i];
            sKey[i] = sKey[j];
            sKey[j] = cTemp;
        }
        return new String(sKey);
    }
}
