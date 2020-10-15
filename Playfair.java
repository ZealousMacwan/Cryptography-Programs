/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Playfair {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Sample\t\t\t\t:zealous");
        // TODO code application logic here
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

       
        Scanner scn = new Scanner(System.in);

        System.out.println("Enter Key\t\t\t:");

        Boolean bLetters = false;
        String sKey = "";
        while (!bLetters) {
            System.out.print("Only Letters are allowed\t:");
            sKey = scn.nextLine();
            bLetters = sKey.chars().allMatch(Character::isLetter);           
        }
        sKey = sKey.trim();
        sKey = sKey.replace(" ", "");
        sKey = newKey(sKey);
        System.out.println("\nNew Key\t\t\t\t:" + sKey);
  //    sAlpha = scramble(sAlpha);
  //    no scramble in playfair !!!
  //    alphabets comes in order in matrix to be filled 
        System.out.println("Random Alpha\t\t\t:" + sAlpha);

        char caMatrix[][] = new char[5][5];
        createMatrix(caMatrix, sKey, sAlpha);
        System.out.println("\n");
        printMatrix(caMatrix);
        System.out.println("\n");

        System.out.println("Enter Plain Text\t\t:");
        String sPlainText = "";
        bLetters = false;        
        while (!bLetters) {
            System.out.print("Only Letters are allowed\t:");
            sPlainText= scn.nextLine();
            sPlainText = sPlainText.trim();
            sPlainText = sPlainText.replace(" ", "");
            bLetters = sPlainText.chars().allMatch(Character::isLetter);
        }    
        sPlainText = newPlainText(sPlainText);
        //System.out.println("" + sPlainText);
        String sCipherText = "";
        sCipherText = playfairEncrypt(sPlainText.toUpperCase(), caMatrix);
//
        String sDecryptedText = "";
        sDecryptedText = playfairDecrypt(sCipherText, caMatrix);
//
//        System.out.println("Alpha:\t\t\t" + "abcdefghijklmnopqrstuvwxyz");
//        System.out.println("Map:\t\t\t" + "||||||||||||||||||||||||||");
//        System.out.println("Key:\t\t\t" + sKey);
        System.out.println("\nPlain Text\t\t\t:" + sPlainText);
        System.out.println("Cipher Text\t\t\t:" + sCipherText);
        System.out.println("Decrypted Text\t\t\t:" + sDecryptedText);
        System.out.println("\nPlain Text\t\t\t:" + sDecryptedText.replace("x", ""));
        System.out.println("\n\n");
    }

    public static String newKey(String sKey) {
        String sNewKey = "";
        char cTrace;
        for (int iItr1 = 0; iItr1 < sKey.length(); iItr1++) {
            cTrace = sKey.charAt(iItr1);
            //first condition will return negative if char not found
            //second condition check to ignore 'j' 
            if (0 > sNewKey.indexOf(cTrace) && 'j' != sKey.charAt(iItr1)) {
                sNewKey += cTrace;
            }
        }
        return sNewKey;
    }

    public static String scramble(String sAlpha) {
        // Convert default alphabates to character array
        char sKey[] = sAlpha.toCharArray();
        Random rRandom = new Random();

        // Scramble using Fisher-Yates shuffle, 
        for (int i = 0; i < sKey.length; i++) {
            int j = rRandom.nextInt(sKey.length);
            // swapping
            char cTemp = sKey[i];
            sKey[i] = sKey[j];
            sKey[j] = cTemp;
        }

        return new String(sKey);
    }

    public static String newPlainText(String sPlainText) {
        int iKeyLength = sPlainText.length();
        String sNewKey = "";
//        if (0 != iKeyLength % 2) {
//            iKeyLength += 1;
//            sPlainText += '\0';
//        }        
        sPlainText = sPlainText.replace('j', 'i');
        for (int iItr1 = 0; iItr1 < (iKeyLength); iItr1++) {
            if ((iItr1 + 1) <= (iKeyLength - 1)) {
                if (sPlainText.charAt(iItr1) != sPlainText.charAt(iItr1 + 1)) {
                    sNewKey += sPlainText.charAt(iItr1);
                    sNewKey += sPlainText.charAt(iItr1 + 1);
                    iItr1++;
                } else if (sPlainText.charAt(iItr1) == sPlainText.charAt(iItr1 + 1)) {
                    sNewKey += sPlainText.charAt(iItr1);
                    sNewKey += "x";
                }
            } else {
                sNewKey += sPlainText.charAt(iItr1);
                sNewKey += "x";
            }

        }
        return sNewKey;

    }

    public static String playfairEncrypt(String sPText, char[][] caMatrix) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //String for storing ciphertext
        String sCText = "";
        int iIndexrow1 = 0, iIndexcol1 = 0, iIndexrow2 = 0, iIndexcol2 = 0, iTracePText = 0;

        for (int iItr3 = 0; iItr3 < sPText.length(); iItr3++) {
            int iChar1set = 0, iChar2set = 0;
            for (int iItr1 = 0; iItr1 < 5; iItr1++) {
                for (int iItr2 = 0; iItr2 < 5; iItr2++) {
                    if (iChar1set == 1 && iChar2set == 1) {
                        break;
                    }
                    if (iChar1set == 0 && caMatrix[iItr1][iItr2] == sPText.charAt(iItr3)) {
                        iIndexrow1 = iItr1;
                        iIndexcol1 = iItr2;
                        iChar1set = 1;
                    }
                    if (iChar2set == 0 && caMatrix[iItr1][iItr2] == sPText.charAt(iItr3 + 1)) {
                        iIndexrow2 = iItr1;
                        iIndexcol2 = iItr2;
                        iChar2set = 1;
                    }
                }
            }
            if (iIndexrow1 != iIndexrow2 && iIndexcol1 != iIndexcol2) {
                sCText += caMatrix[iIndexrow1][iIndexcol2];
                sCText += caMatrix[iIndexrow2][iIndexcol1];
            } else if (iIndexrow1 == iIndexrow2) {
                sCText += caMatrix[iIndexrow1][(iIndexcol1 + 1) % 5];
                sCText += caMatrix[iIndexrow2][(iIndexcol2 + 1) % 5];
            } else {
                sCText += caMatrix[(iIndexrow1 + 1) % 5][iIndexcol1];
                sCText += caMatrix[(iIndexrow2 + 1) % 5][iIndexcol2];
            }
            iItr3++; //we have traced 2 char in each iteration
        }
        return sCText;
    }

    public static String playfairDecrypt(String sCText, char[][] caMatrix) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //String for storing ciphertext
        ///String sCText = "";/
        String sPText = "";
        int iIndexrow1 = 0, iIndexcol1 = 0, iIndexrow2 = 0, iIndexcol2 = 0, iTracePText = 0;

        for (int iItr3 = 0; iItr3 < sCText.length(); iItr3++) {
            int iChar1set = 0, iChar2set = 0;
            for (int iItr1 = 0; iItr1 < 5; iItr1++) {
                for (int iItr2 = 0; iItr2 < 5; iItr2++) {
                    if (iChar1set == 1 && iChar2set == 1) {
                        break;
                    }
                    if (iChar1set == 0 && caMatrix[iItr1][iItr2] == sCText.charAt(iItr3)) {
                        iIndexrow1 = iItr1;
                        iIndexcol1 = iItr2;
                        iChar1set = 1;
                    }
                    if (iChar2set == 0 && caMatrix[iItr1][iItr2] == sCText.charAt(iItr3 + 1)) {
                        iIndexrow2 = iItr1;
                        iIndexcol2 = iItr2;
                        iChar2set = 1;
                    }
                }
            }
            if (iIndexrow1 != iIndexrow2 && iIndexcol1 != iIndexcol2) {
                sPText += caMatrix[iIndexrow1][iIndexcol2];
                sPText += caMatrix[iIndexrow2][iIndexcol1];
            } else if (iIndexrow1 == iIndexrow2) {
                int newIndexcol1 = (iIndexcol1 - 1) % 5;
                int newIndexcol2 = (iIndexcol2 - 1) % 5;
                if (newIndexcol1 < 0) {
                    newIndexcol1 += 5;
                }
                if (newIndexcol2 < 0) {
                    newIndexcol2 += 5;
                }
                sPText += caMatrix[iIndexrow1][newIndexcol1];
                sPText += caMatrix[iIndexrow2][newIndexcol2];
            } else {
                int newIndexrow1 = (iIndexrow1 - 1) % 5;
                int newIndexrow2 = (iIndexrow2 - 1) % 5;
                if (newIndexrow1 < 0) {
                    newIndexrow1 += 5;
                }
                if (newIndexrow2 < 0) {
                    newIndexrow2 += 5;
                }
                sPText += caMatrix[newIndexrow1][iIndexcol1];
                sPText += caMatrix[newIndexrow2][iIndexcol2];
            }
            iItr3++; //we have traced 2 char in each iteration
        }
        return sPText.toLowerCase();
    }

    private static void createMatrix(char[][] iaMatrix, String sKey, String sAlpha) {

        int iKeyLength = sKey.length();
        int iTraceKey = 0;
        int iTraceAlpha = 0;
        sKey = sKey.toUpperCase();
        sAlpha = sAlpha.toUpperCase();

        for (int iItr1 = 0; iItr1 < 5; iItr1++) {
            for (int iItr2 = 0; iItr2 < 5; iItr2++) {
                if (iTraceKey < iKeyLength) {
                    iaMatrix[iItr1][iItr2] = sKey.charAt(iTraceKey);
                    iTraceKey++;
                } else {
                    //char cC = sAlpha.charAt(iTraceAlpha);
                    while ((sAlpha.charAt(iTraceAlpha) == 'J') || 0 <= sKey.indexOf(sAlpha.charAt(iTraceAlpha))) {
                        iTraceAlpha++;
                    }
                    iaMatrix[iItr1][iItr2] = sAlpha.charAt(iTraceAlpha);
                    iTraceAlpha++;
                }
            }
        }

    }

    public static void printMatrix(char[][] iaMatrix) {
        //System.out.println("_______________________");
        System.out.println("-------------------------------");
        for (int iItr1 = 0; iItr1 < 5; iItr1++) {
            //System.out.println("-----------------------");
            System.out.print("|");
            for (int iItr2 = 0; iItr2 < 5; iItr2++) {
                System.out.print("  " + iaMatrix[iItr1][iItr2] + "  |");
            }
            System.out.println("");
        }
        System.out.println("-------------------------------");
    }

}
