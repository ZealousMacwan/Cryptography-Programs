/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singlecolumn;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Singlecolumn {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // TODO code application logic here
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //it will shuffle the alphabates which will be our new random key
        //String sKey = scramble(sAlpha);
        Scanner scn = new Scanner(System.in);

        System.out.print("Enter Key\t\t:");
        String sKey = "";
        sKey = scn.nextLine();
        //sKey = newKey(sKey);
        int iKey[] = convertKey(sKey);
        int iCopyKey[] = iKey.clone();
        int iIndexKey[] = sortKey(iCopyKey);
        //System.out.println("index array" + Arrays.toString(iIndexKey));

        System.out.print("Enter PlainText\t\t:");
        String sPlainText = scn.nextLine().replaceAll(" ", "");

        String sCipherText = colEncrypt(sPlainText, iKey, iIndexKey);
        String sDecipherText = colDecrypt(sCipherText, iKey, iIndexKey);
        System.out.println("PlainText\t\t: " + sPlainText);
        System.out.println("CipherText\t\t: " + sCipherText.toUpperCase());
        System.out.println("DecipherText\t\t: " + sDecipherText);
    }

    public static String colDecrypt(String sCipherText, int[] iKey, int[] iIndexKey) {
        String sPlainText = "";
        int countCol = iKey.length;
        int countRow = (int) Math.ceil((double) (sCipherText.length()) / iKey.length);

        char table[][] = new char[countRow][countCol];
        int iTrace = 0;

        for (int iItr1 = 0; iItr1 < countCol; iItr1++) {
            for (int iItr2 = 0; iItr2 < countRow; iItr2++) {
                if (iTrace < sCipherText.length()) {
                    table[iItr2][iIndexKey[iItr1]] = sCipherText.charAt(iTrace);
                    iTrace++;
                } else {
                    break;
                }
            }
        }
//       for (int iItr1 = 0; iItr1 < countRow; iItr1++) {
//            for (int iItr2 = 0; iItr2 < countCol; iItr2++) {
//                System.out.print(table[iItr1][iItr2]+"\t");
//            }
//            System.out.println("");
//       }
//        

        //now read row by row
        for (int iItr1 = 0; iItr1 < countRow; iItr1++) {
            for (int iItr2 = 0; iItr2 < countCol; iItr2++) {
                sPlainText = sPlainText + table[iItr1][iItr2];
            }
        }
        return sPlainText.trim();

    }

    public static String colEncrypt(String sPlainText, int[] iKey, int[] iIndexKey) {
        String sCipherText = "";
        int countCol = iKey.length;
        int countRow = (sPlainText.length() + countCol) / countCol;

        char table[][] = new char[countRow][countCol];
        int iTrace = 0;

        for (int iItr1 = 0; iItr1 < countRow; iItr1++) {
            for (int iItr2 = 0; iItr2 < countCol; iItr2++) {
                if (iTrace < sPlainText.length()) {
                    table[iItr1][iItr2] = sPlainText.charAt(iTrace);
                    iTrace++;
                } else {
                    break;
                }
            }
        }
        
        System.out.println("");
        for (int iItr1 = 0; iItr1 < iKey.length; iItr1++) {
            System.out.print(iKey[iItr1]+"\t");
        }
        System.out.println("");
        for (int iItr1 = 0; iItr1 < countRow; iItr1++) {
            for (int iItr2 = 0; iItr2 < countCol; iItr2++) {
                System.out.print(table[iItr1][iItr2] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        

        //now read col by col       
        for (int iItr1 = 0; iItr1 < iIndexKey.length; iItr1++) {
            for (int iItr2 = 0; iItr2 < countRow; iItr2++) {
                sCipherText = sCipherText + table[iItr2][iIndexKey[iItr1]];
            }
        }
        return sCipherText;

    }

    public static int[] sortKey(int iKey[]) {
        int iBackupKey[] = iKey.clone();

        System.out.println("Key\t\t\t: " + Arrays.toString(iBackupKey));
        int iIndexKey[] = new int[iKey.length];
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        for (int iItr1 = 0; iItr1 < iKey.length; iItr1++) {
            for (int iItr2 = 0; iItr2 < iKey.length - 1; iItr2++) {
                if (iKey[iItr2] > iKey[iItr2 + 1]) {
                    int iTemp = iKey[iItr2];
                    iKey[iItr2] = iKey[iItr2 + 1];
                    iKey[iItr2 + 1] = iTemp;
                }
            }
        }
        //System.out.println(Arrays.toString(iBackupKey));
        //System.out.println((Arrays.toString(iKey)));

        for (int iItr1 = 0; iItr1 < iKey.length; iItr1++) {
            for (int iItr2 = 0; iItr2 < iBackupKey.length; iItr2++) {
                if (iKey[iItr1] == iBackupKey[iItr2]) {
                    iIndexKey[iItr1] = iItr2;
                }
            }

        }
        //System.out.println("index array" + Arrays.toString(iIndexKey));
        return iIndexKey;

    }

    public static int[] convertKey(String sKey) {
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        int iKey[] = new int[sKey.length()];
        for (int iItr1 = 0; iItr1 < sKey.length(); iItr1++) {
            iKey[iItr1] = sAlpha.indexOf(sKey.charAt(iItr1));
        }
        return iKey;
    }

    
}
