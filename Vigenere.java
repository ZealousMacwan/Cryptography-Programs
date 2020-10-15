/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vigenere;

import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Vigenere {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter Key:");
        String sKey = scn.nextLine();
        
        for(int iVar1=0;iVar1<sKey.length();iVar1++){
            
        }

        System.out.println("Enter Plain Text:");
        String sPlainText = scn.nextLine();

        //it will make key of length of plaintext by repeating current key
        sKey = newKey(sKey, sPlainText.length());

        String sCipherText = "";
        sCipherText = vigenereEncrypt(sPlainText, sKey);

        String sDecryptedText = "";
        sDecryptedText = vigenereDecrypt(sCipherText.toLowerCase(), sKey);
        
        System.out.println("Key:\t\t\t" + sKey);
        System.out.println("Plain Text:\t\t" + sPlainText);
        System.out.println("Cipher Text:\t\t" + sCipherText);
        System.out.println("Decrypted Text:\t\t" + sDecryptedText);
    }
    
    public static String vigenereEncrypt(String sPText, String sKey){
        //it will be useful for finding char position in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        
        String sCText = "";
        
        for(int iVar1=0;iVar1<sPText.length();iVar1++){
            //finding index of plaintext char in alpha
            int iIndex = sAlpha.indexOf(sPText.charAt(iVar1));
            
            //finding index of key char in alpha
            int iKeyIndex = sAlpha.indexOf(sKey.charAt(iVar1));
            
            //adding plaintext char index and key char index 
            iIndex = ((iIndex+iKeyIndex)%26);
            
            //appending char by char 
            sCText+=sAlpha.charAt(iIndex);
        }
        return sCText.toUpperCase();
    }
    public static String vigenereDecrypt(String sCText, String sKey){
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        String sPText = "";
        for(int iVar1=0;iVar1<sCText.length();iVar1++){
            int iIndex = sAlpha.indexOf(sCText.charAt(iVar1));
            int iKeyIndex = sAlpha.indexOf(sKey.charAt(iVar1));
            iIndex = ((iIndex-iKeyIndex)%26);
            
            //in java modulo give negative value 
            //so adding +26 will make it postive and in range of 26
            if(iIndex<0)
                iIndex=iIndex+26;
            
            //appending char by char 
            sPText+=sAlpha.charAt(iIndex);
        }
        return sPText.toLowerCase();
    }

    public static String newKey(String sOKey, int iPLength) {
        String sNewKey = "";
        int iOKeyLength=sOKey.length(); //OldKey Length
        
        for(int iVar1=0,iVar2=0;iVar1<iPLength;iVar1++,iVar2++){
            
            //if old key length is reached make it 0 to start from 0 again
            if(iVar2==(iOKeyLength))
                iVar2=0;
            
            //till key length traversed keep adding to new key
            sNewKey+=sOKey.charAt(iVar2);
        }
        return sNewKey;
    }

}
