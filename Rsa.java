/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsa;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Rsa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Sample\t\t\t\t:p=17.q=11,e=7,d=23");
        System.out.println("Sample\t\t\t\t:p=11.q=13,e=7,d=103");

        System.out.println("");

        Scanner scn = new Scanner(System.in);
        int iMsg = 20;
        System.out.print("Enter prime p\t\t\t:");
        int iPrimeP = scn.nextInt();

        System.out.print("Enter prime q\t\t\t:");
        int iPrimeQ = scn.nextInt();

        int iN = (iPrimeP) * (iPrimeQ);
        System.out.print("Calculate n = (p)*(q)\t\t:" + iN);
        System.out.println("");

        int iPhi = (iPrimeP - 1) * (iPrimeQ - 1);
        System.out.print("Calculate phi = (p-1)*(q-1)\t:" + iPhi);
        System.out.println("");

        int iE = 0;
        //manual e
        System.out.print("Enter e(manual mode)\t\t:");
        while (true) {
            iE = scn.nextInt();
            if ((1 < iE && iE < iPhi) && 1 == gcd(iE, iPhi)) {
                break;
            } else {
                System.out.print("Choose different e\t\t:");
            }
        }

        //auto choose e
//        for (iE = 2; iE < iPhi; iE++) {
//            if (gcd(iE, iPhi) == 1) {
//                break;
//            }
//        }
        int iD = (int) findInverse(iE, iPhi);
        System.out.println("Calculated d\t\t\t:" + iD);

        System.out.println("");
        System.out.println("Enter msg to be encrypted\t:");
        System.out.print("msg be int and < n\t\t:");
//        String sMsg = scn.next();
//        String sCipherText ="";
//        for (int iItr1 = 0; iItr1 < sMsg.length(); iItr1++) {
//            sCipherText = sCipherText + Encrypt(sMsg.charAt(iItr1),iE,iN);
//        }
// here we are not dealing with 26 char, brute force can easily break the code
// here We may have msg upto range n, so taking only int to encrypt and decrypt
        int iM = scn.nextInt();
        
        int iCipher = Encrypt(iM, iE, iN);                
        BigInteger biSign = CalcExpression(BigInteger.valueOf(iM), iD, BigInteger.valueOf(iN));
                
        System.out.println("Cipher Msg\t\t\t:" + iCipher);

        BigInteger biN = BigInteger.valueOf(iN);
        BigInteger biCipher = BigInteger.valueOf(iCipher);

        BigInteger biDecrypted = CalcExpression(biCipher, iD, biN);
        BigInteger biSignV = CalcExpression(biSign, iE, BigInteger.valueOf(iN));
              
        System.out.println("Decrypted Msg\t\t\t:" + biDecrypted);
        if (biDecrypted.equals(biSignV)) {
            System.out.println("Signature Msg\t\t\t:" + "Valid");
        } else {
            System.out.println("Signature Verify\t\t\t:" + "InValid");
        }

    }

//    public static int Decrypt(int iCipher, int iD, int iN) {
//        return ((int) (Math.pow(iCipher, iE) % iN));
//    }
    

    public static int Encrypt(int iM, int iE, int iN) {
        return (int) ((Math.pow(iM, iE) % iN));
    }

    public static BigInteger CalcExpression(BigInteger biCipher, int iD, BigInteger biN) {
        return (biCipher.pow(iD)).mod(biN);
    }

    public static int gcd(int ie, int io) {
        if (ie == 0) {
            return io;
        } else {
            return gcd(io % ie, ie);
        }
    }

    public static int findInverse(int dDet, int iPhi) {
        dDet = dDet % iPhi;
        if (dDet < 0) {
            dDet += iPhi;
        }
        //System.out.println("dDet" + dDet);
        for (int iItr1 = 0; iItr1 < iPhi; iItr1++) {
            float fInverse = ((dDet * iItr1) % iPhi);
            if (1 == fInverse) {
                return iItr1;
            }
        }
        return 0;
    }
}
