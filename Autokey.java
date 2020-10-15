/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autokey;

import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Autokey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // TODO code application logic here
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter Key:");
        String sKey = scn.nextLine();

        System.out.println("Enter Plain Text:");
        String sPlainText = scn.nextLine();

        //new key will be , old key + plaintext appended
        sKey = newKey(sKey, sPlainText);

        String sCipherText = "";
        sCipherText = autoKeyEncrypt(sPlainText, sKey);

        String sDecryptedText = "";
        sDecryptedText = autoKeyDecrypt(sCipherText.toLowerCase(), sKey);

        System.out.println("Alpha:\t\t\t"+"abcdefghijklmnopqrstuvwxyz");
        System.out.println("PlainText:\t\t"+sPlainText);
        System.out.print("Map:\t\t\t");
        for(int iVar1=0;iVar1<sPlainText.length();iVar1++)
            System.out.print("+");
        System.out.println("\nKey:\t\t\t" + sKey);
        
        System.out.println("Cipher Text:\t\t" + sCipherText);
        System.out.println("Decrypted Text:\t\t" + sDecryptedText);
    }

    public static String autoKeyEncrypt(String sPText, String sKey) {
        //it will be useful for finding char index in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        String sCText = "";

        for (int iVar1 = 0; iVar1 < sPText.length(); iVar1++) {
            //finding index of plaintext char in alpha
            int iIndex = sAlpha.indexOf(sPText.charAt(iVar1));

            //finding index of key char in alpha
            int iKeyIndex = sAlpha.indexOf(sKey.charAt(iVar1));

            //adding plain and key char index
            iIndex = ((iIndex + iKeyIndex) % 26);

            //append char by char in ciphertext
            sCText += sAlpha.charAt(iIndex);
        }
        return sCText.toUpperCase();
    }

    public static String autoKeyDecrypt(String sCText, String sKey) {
        //it will be useful for finding char index in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        String sPText = "";

        for (int iVar1 = 0; iVar1 < sCText.length(); iVar1++) {
            //finding index of ciphertext char in alpha
            int iIndex = sAlpha.indexOf(sCText.charAt(iVar1));

            //finding index of key char in alpha
            int iKeyIndex = sAlpha.indexOf(sKey.charAt(iVar1));

            //minus of plain and key char index
            iIndex = ((iIndex - iKeyIndex) % 26);

            //java gives negative in modulo
            //so adding +26 for positive and in range of 26
            if (iIndex < 0) {
                iIndex = iIndex + 26;
            }
            //appending char by char in PlainText
            sPText += sAlpha.charAt(iIndex);
        }
        return sPText.toLowerCase();
    }

    public static String newKey(String sOKey, String sPText) {
        int iPLength = sPText.length();
        String sNewKey = "";
        int iOKeyLength = sOKey.length(); //OldKey Length

        for (int iVar1 = 0, iVar2 = 0; iVar1 < iPLength; iVar1++) {
            //first append old key in new key
            if (iVar1 < (iOKeyLength)) {
                sNewKey += sOKey.charAt(iVar1);
            } else {
                //append plaintext in new key
                sNewKey += sPText.charAt(iVar2);
                iVar2++;
            }
        }
        return sNewKey;
    }

}
