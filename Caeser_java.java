/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caeser_java;

import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Caeser_java {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        Scanner scn = new Scanner(System.in);

        System.out.println("Enter Plain Text:");

        //char array for plaintext
        char[] caPlainText = scn.nextLine().toCharArray();

        //int key between 1 to 25 because 0 means no shift
        System.out.println("Enter Key (2-25):");
        int iKey = scn.nextInt();

        //keep trying if invalid
        while (iKey > 25 || iKey <= 1) {
            System.out.println("Enter Key (2-25):");
            iKey = scn.nextInt();
        }

        //char array to store CipherText
        char[] caCipherText = new char[caPlainText.length];
        caCipherText = caeserEncrypt(caPlainText, iKey);

        //char array to store DecryptedText
        char[] caDecryptedText = new char[caCipherText.length];
        caDecryptedText = caeserDecrypt(caCipherText, iKey);

        System.out.println("Alpha:\t\t\t" + "abcdefghijklmnopqrstuvwxyz");
        System.out.println("Map:\t\t\t" + "||||||||||||||||||||||||||");
        printMapping(iKey);
        System.out.println("Plain Text:\t\t" + new String(caPlainText));
        //System.out.println("Key:" + iKey);
        System.out.println("Cipher Text:\t\t" + new String(caCipherText));
        System.out.println("Decrypted Text:\t\t" + new String(caDecryptedText));

    }

    public static void printMapping(int iKey) {
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        String sAlphaKey = "";
        int iIndex = 0;
        for (int iVar1 = 0; iVar1 < 26; iVar1++) {
            //iIndex = sAlpha.indexOf(caPText[iVar1]);
            //shifting the index
            iIndex = (iVar1 + iKey) % 26;
            sAlphaKey += sAlpha.charAt(iIndex);
        }
        System.out.println("Key:\t\t\t" + sAlphaKey);
    }

    public static char[] caeserEncrypt(char[] caPText, int iKey) {
        //char array for CipherText of size of plaintext 
        char[] caCipherText = new char[caPText.length];

        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        for (int iVar1 = 0; iVar1 < caPText.length; iVar1++) {
            //finding index of char in alpha
            int iIndex = sAlpha.indexOf(caPText[iVar1]);

            //shifting the index
            iIndex = (iIndex + iKey) % 26;

            //now assining shifted index char from alpha to ciphertext         
            caCipherText[iVar1] = Character.toUpperCase(sAlpha.charAt(iIndex));
        }

        return caCipherText;
    }

    public static char[] caeserDecrypt(char[] caCText, int iKey) {
        char[] caPText = new char[caCText.length];
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        for (int iVar1 = 0; iVar1 < caCText.length; iVar1++) {
            int iIndex = sAlpha.indexOf(Character.toLowerCase(caCText[iVar1]));
            iIndex = ((iIndex - iKey) % 26);
            if (iIndex < 0) {
                iIndex += 26;
            }

            caPText[iVar1] = (sAlpha.charAt(iIndex));
        }
        return caPText;
    }

}
