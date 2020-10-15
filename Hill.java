/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hill;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class Hill {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("Sample\t\t\t\t:2 hill");
        System.out.println("Sample\t\t\t\t:3 cryptowar");
        // TODO code application logic here
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //it will shuffle the alphabates which will be our new random key
        //String sKey = scramble(sAlpha);
        System.out.print("Enter Block Size\t\t:");
        int iBlockSize = 0;

        Boolean bBlockSize = false;
        String sKey = "";

        do {
            try {
                //System.out.println("Blocksize 2 to 6 for now.");

                //using old scn gives infinite looping problem
                Scanner scn1 = new Scanner(System.in);
                iBlockSize = scn1.nextInt();
                if (iBlockSize >= 2 && iBlockSize <= 6) {
                    bBlockSize = true;
                } else {
                    bBlockSize = false;
                }
            } catch (InputMismatchException e) {
                // accept integer only.
                System.out.println("Invalid ! Integer Only");
                bBlockSize = false;
            }
        } while (!bBlockSize);
        int iMatrixSize = iBlockSize * iBlockSize;
        System.out.println("Enter " + iMatrixSize + " size char key:");
        Scanner scn = new Scanner(System.in);
        bBlockSize = false;
        while (!bBlockSize) {

            System.out.print(iMatrixSize + " char only !\t\t\t:");
            sKey = scn.nextLine();
            sKey = sKey.trim();
            sKey = sKey.replace(" ", "");
            if ((iMatrixSize) == sKey.length()) {
                bBlockSize = true;
            } else {
                bBlockSize = false;
            }
        }
//        sKey = newKey(sKey);
//        
//        System.out.println("\nNew Key\t\t\t\t:" + sKey);
//        sAlpha = scramble(sAlpha);
//        System.out.println("Random Alpha\t\t\t:" + sAlpha);

        float caMatrix[][] = new float[iBlockSize][iBlockSize];
        createMatrix(caMatrix, sKey, sAlpha, iBlockSize);

        float dDet = determinant(caMatrix, iBlockSize);
        if (0 == dDet) {
            System.out.println("Inverse does not exist!");
            System.exit(0);
        }

        System.out.println("\n");
        printMatrix(caMatrix, iBlockSize);
        System.out.println("\n");

        System.out.println("Enter Plain Text:");
        String sPlainText = "";
        Boolean bLetters = false;
        while (!bLetters) {
            System.out.print("Only Letters are allowed\t:");
            sPlainText = scn.nextLine();
            sPlainText = sPlainText.trim();
            sPlainText = sPlainText.replace(" ", "");
            bLetters = sPlainText.chars().allMatch(Character::isLetter);
        }

        sPlainText = newPlainText(sPlainText, iBlockSize);

        //System.out.println("" + sPlainText);
        String sCipherText = "";
        sCipherText = playfairEncrypt(sPlainText.toUpperCase(), caMatrix, iBlockSize);

//
        caMatrix = invert(caMatrix, dDet);

//        double[][] key_rev = Inverse(key,iBlockSize,detMatrix);
        //       key_rev = RepairMatrix(key_rev,keySize);
//        System.out.println("The inverse is: ");
//        for (int i = 0; i < iBlockSize; ++i) {
//            for (int j = 0; j < iBlockSize; ++j) {
//                System.out.print((caMatrix[i][j]) + "  ");
//            }
//            System.out.println();
//        }
        String sDecryptedText = "";
        sDecryptedText = playfairEncrypt(sCipherText, caMatrix, iBlockSize).toLowerCase();
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

    public static float determinant(float A[][], int N) {
        float det = 0;
        if (N == 1) {
            det = A[0][0];
        } else if (N == 2) {
            det = A[0][0] * A[1][1] - A[1][0] * A[0][1];
        } else {
            det = 0;
            for (int j1 = 0; j1 < N; j1++) {
                float[][] m = new float[N - 1][];
                for (int k = 0; k < (N - 1); k++) {
                    m[k] = new float[N - 1];
                }
                for (int i = 1; i < N; i++) {
                    int j2 = 0;
                    for (int j = 0; j < N; j++) {
                        if (j == j1) {
                            continue;
                        }
                        m[i - 1][j2] = A[i][j];
                        j2++;
                    }
                }
                det += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1] * determinant(m, N - 1);
            }
        }
        return det;
    }

//    private static float[][] Inverse(float[][] key, int keySize, float detMatrix) {
//        float[][] transposeMat = new float[keySize][keySize];
//        for (int pLoop = 0; pLoop < keySize; pLoop++) {
//            for (int sLoop = 0; sLoop < keySize; sLoop++) {
//                transposeMat[pLoop][sLoop] = key[sLoop][pLoop];
//            }
//        }
//        float[][] adjMatrix = AdjMatrix(transposeMat, keySize);
//        if (detMatrix < 0) {
//            while (detMatrix < 0) {
//                detMatrix += 26;
//            }
//        } else {
//            detMatrix = detMatrix % 26;
//        }
//        float invDetMatrix = FindCoPrime(detMatrix);
//        if (invDetMatrix == 0) {
//            System.exit(0);
//        }
//        for (int pLoop = 0; pLoop < keySize; pLoop++) {
//            for (int sLoop = 0; sLoop < keySize; sLoop++) {
//                adjMatrix[pLoop][sLoop] = (adjMatrix[pLoop][sLoop] * invDetMatrix) % 26;
//            }
//        }
//        return adjMatrix;
//    }

//    private static float DetReducedMatrix(float[][] transposeMat, int pLoop, int sLoop, int keySize) {
//        HillCipher hillCipher = new HillCipher();
//        float[][] returnData = new float[keySize - 1][keySize - 1];
//        for (int h = 0, j = 0; h < keySize; h++) {
//            if (h == pLoop) {
//                continue;
//            }
//            for (int i = 0, k = 0; i < keySize; i++) {
//                if (i == sLoop) {
//                    continue;
//                }
//                returnData[j][k] = transposeMat[h][i];
//                k++;
//            }
//            j++;
//        }
//        return determinant(returnData, keySize - 1);
//    }

//    private static float[][] AdjMatrix(float[][] transposeMat, int keySize) {
//        int counter = 0;
//        float[][] adjMatrix = new float[keySize][keySize];
//        for (int pLoop = 0; pLoop < keySize; pLoop++) {
//            for (int sLoop = 0; sLoop < keySize; sLoop++) {
//                adjMatrix[pLoop][sLoop] = (float) (DetReducedMatrix(transposeMat, pLoop, sLoop, keySize) * Math.pow(-1, counter));
//                counter++;
//            }
//        }
//        return adjMatrix;
//    }
//
//    private static float FindCoPrime(float detMatrix) {
//        int pLoop;
//        for (pLoop = 1; pLoop <= 26; pLoop++) {
//            if ((detMatrix * pLoop) % 26 == 1) {
//                return pLoop;
//            }
//        }
//        return 0;
//    }

//    private static double[][] RepairMatrix(double[][] key_rev, int keySize) {
//        for (int pLoop = 0; pLoop < keySize; pLoop++) {
//            for (int sLoop = 0; sLoop < keySize; sLoop++) {
//                if (key_rev[pLoop][sLoop] < 0) {
//                    while (key_rev[pLoop][sLoop] < 0) {
//                        key_rev[pLoop][sLoop] += 26;
//                    }
//                }
//            }
//        }
//        return key_rev;
//    }

    public static float findInverse(float dDet) {
        dDet = dDet % 26;
        if (dDet < 0) {
            dDet += 26;
        }
       // System.out.println("dDet" + dDet);
        for (int iItr1 = 0; iItr1 < 26; iItr1++) {
            float fInverse = ((dDet * iItr1) % 26);
            if (1 == fInverse) {
                return iItr1;
            }
        }
        return 0;
    }

    public static int modInverse(int iA, int iB) {
        iA = iA % iB;
        for (int iItr1 = 1; iItr1 < iB; iItr1++) {
            if ((iA * iItr1) % iB == 1) {
                return iItr1;
            }
        }
        return 1;
    }

//    public static float findInverse(float dDet) {
//        dDet = dDet % 26;
//        if (dDet < 0) {
//            dDet += 26;
//        }
//        System.out.println("dDet" + dDet);
//        for (int iItr1 = 0; true; iItr1++) {
//            float fInverse = ((dDet * iItr1) % 26);
//            if (1 == fInverse) {
//                return iItr1;
//            }
//        }
//        // return 0;
//    }
    public static float[][] invert(float a[][], float dDet) {
        int n = a.length;
        float x[][] = new float[n][n];
        float b[][] = new float[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i][i] = 1;
        }

        //System.out.println("det" + dDet);
        float fInverse = findInverse(dDet);
        if (0 == fInverse) {
            System.out.println("Inverse not exist in mod 26");
            System.exit(0);
        }
        //System.out.println("finverse" + fInverse);

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // x[i][j] = Math.round(x[i][j]);
                x[i][j] = (Math.round(x[i][j] * (dDet)));
                if (x[i][j] < 0) {
                    x[i][j] += 26;
                }
                x[i][j] = ((x[i][j] * fInverse) % 26);
                if (x[i][j] < 0) {
                    x[i][j] += 26;
                }

            }
        }
        return x;
    }

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.
    public static void gaussian(float a[][], int index[]) {
        int n = index.length;
        float c[] = new float[n];

        // Initialize the index
        for (int i = 0; i < n; ++i) {
            index[i] = i;
        }

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            float c1 = 0;
            for (int j = 0; j < n; ++j) {
                float c0 = Math.abs(a[i][j]);
                if (c0 > c1) {
                    c1 = c0;
                }
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            float pi1 = 0;
            for (int i = j; i < n; ++i) {
                float pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                float pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    public static String newKey(String sKey) {
        String sNewKey = "";
        char cTrace;
        for (int iItr1 = 0; iItr1 < sKey.length(); iItr1++) {
            cTrace = sKey.charAt(iItr1);
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

    public static String newPlainText(String sPlainText, int iBlockSize) {
        int iKeyLength = sPlainText.length();
        int iPadding = iKeyLength % iBlockSize;
        if (0 != iPadding) {
            for (int iItr1 = 0; iItr1 < (iBlockSize - iPadding); iItr1++) {
                sPlainText += 'x';
            }
        }

        return sPlainText;

    }

    public static String playfairEncrypt(String sPText, float[][] iaMatrix, int iBlockSize) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        sAlpha = sAlpha.toUpperCase();
        float iaMatrix2[][] = new float[iBlockSize][1];

        //String for storing ciphertext
        String sCText = "";
        int iIndexrow1 = 0, iIndexcol1 = 0, iIndexrow2 = 0, iIndexcol2 = 0, iTracePText = 0;
        for (int iItr4 = 0; iItr4 < (sPText.length());) {

            for (int iItr1 = 0; iItr1 < iBlockSize; iItr1++) {
                iaMatrix2[iItr1][0] = 0;
                for (int iItr3 = 0; iItr3 < iBlockSize; iItr3++) {
                    float fEquat = (iaMatrix2[iItr1][0] + iaMatrix[iItr1][iItr3] * sAlpha.indexOf(sPText.charAt(iItr4))) % 26;
                    if (fEquat < 0) {
                        fEquat += 26;
                    }
                    iaMatrix2[iItr1][0] = fEquat;

                    iItr4++;
                }
                iItr4 -= iBlockSize;
                //System.out.printf("|%02d|", (int) iaMatrix2[iItr1][0]);
                sCText += sAlpha.charAt((int) iaMatrix2[iItr1][0]);
                //System.out.println("");
            }
            iItr4 += iBlockSize;
           // System.out.println("new set");
        }

        return sCText;
    }

    public static String playfairEncrypt2(String sPText, float[][] iaMatrix, int iBlockSize) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";
        //sAlpha = sAlpha.toUpperCase();
        float iaMatrix2[][] = new float[iBlockSize][1];

        //String for storing ciphertext
        String sCText = "";
        int iIndexrow1 = 0, iIndexcol1 = 0, iIndexrow2 = 0, iIndexcol2 = 0, iTracePText = 0;
        for (int iItr4 = 0; iItr4 < (sPText.length());) {

            for (int iItr1 = 0; iItr1 < iBlockSize; iItr1++) {
                iaMatrix2[iItr1][0] = 0;
                for (int iItr3 = 0; iItr3 < iBlockSize; iItr3++) {
                    float fEquat = (iaMatrix2[iItr1][0] + iaMatrix[iItr1][iItr3] * sAlpha.indexOf(sPText.charAt(iItr4)));
                    //if(fEquat < 0){
                    //   fEquat += 26;
                    //}
                    iaMatrix2[iItr1][0] = fEquat;

                    iItr4++;
                }
                iItr4 -= 3;
                System.out.printf("|%02f|", iaMatrix2[iItr1][0] % 26);
                sCText += sAlpha.charAt((int) iaMatrix2[iItr1][0] % 26);
                System.out.println("");
            }
            iItr4 += 3;
            System.out.println("new set");
        }

        return sCText;
    }

    public static String playfairDecrypt(String sCText, float[][] caMatrix, int iBlockSize) {
        //This will be useful for getting position of char in a-z
        String sAlpha = "abcdefghijklmnopqrstuvwxyz";

        //String for storing ciphertext
        ///String sCText = "";/
        String sPText = "";

        caMatrix = invert(caMatrix, 121);
        System.out.println("The inverse is: ");
        for (int i = 0; i < iBlockSize; ++i) {
            for (int j = 0; j < iBlockSize; ++j) {
                System.out.print((caMatrix[i][j]) + "  ");
            }
            System.out.println();
        }
        //printMatrix(caMatrix, iBlockSize);
        sPText = playfairEncrypt(sCText, caMatrix, iBlockSize).toLowerCase();
        return sPText;
//        int iIndexrow1 = 0, iIndexcol1 = 0, iIndexrow2 = 0, iIndexcol2 = 0, iTracePText = 0;
//
//        for (int iItr3 = 0; iItr3 < sCText.length(); iItr3++) {
//            int iChar1set = 0, iChar2set = 0;
//            for (int iItr1 = 0; iItr1 < 5; iItr1++) {
//                for (int iItr2 = 0; iItr2 < 5; iItr2++) {
//                    if (iChar1set == 1 && iChar2set == 1) {
//                        break;
//                    }
//                    if (iChar1set == 0 && caMatrix[iItr1][iItr2] == sCText.charAt(iItr3)) {
//                        iIndexrow1 = iItr1;
//                        iIndexcol1 = iItr2;
//                        iChar1set = 1;
//                    }
//                    if (iChar2set == 0 && caMatrix[iItr1][iItr2] == sCText.charAt(iItr3 + 1)) {
//                        iIndexrow2 = iItr1;
//                        iIndexcol2 = iItr2;
//                        iChar2set = 1;
//                    }
//                }
//            }
//            if (iIndexrow1 != iIndexrow2 && iIndexcol1 != iIndexcol2) {
//                sPText += caMatrix[iIndexrow1][iIndexcol2];
//                sPText += caMatrix[iIndexrow2][iIndexcol1];
//            } else if (iIndexrow1 == iIndexrow2) {
//                int newIndexcol1 = (iIndexcol1 - 1) % 5;
//                int newIndexcol2 = (iIndexcol2 - 1) % 5;
//                if (newIndexcol1 < 0) {
//                    newIndexcol1 += 5;
//                }
//                if (newIndexcol2 < 0) {
//                    newIndexcol2 += 5;
//                }
//                sPText += caMatrix[iIndexrow1][newIndexcol1];
//                sPText += caMatrix[iIndexrow2][newIndexcol2];
//            } else {
//                int newIndexrow1 = (iIndexrow1 - 1) % 5;
//                int newIndexrow2 = (iIndexrow2 - 1) % 5;
//                if (newIndexrow1 < 0) {
//                    newIndexrow1 += 5;
//                }
//                if (newIndexrow2 < 0) {
//                    newIndexrow2 += 5;
//                }
//                sPText += caMatrix[newIndexrow1][iIndexcol1];
//                sPText += caMatrix[newIndexrow2][iIndexcol2];
//            }
//            iItr3++; //we have traced 2 char in each iteration
//        }
//        return sPText.toLowerCase();
    }

    private static void createMatrix(float[][] iaMatrix, String sKey, String sAlpha, int iBlockSize) {

        int iKeyLength = sKey.length();
        int iTraceKey = 0;
        int iTraceAlpha = 0;
        sKey = sKey.toUpperCase();
        sAlpha = sAlpha.toUpperCase();

        for (int iItr1 = 0; iItr1 < iBlockSize; iItr1++) {
            for (int iItr2 = 0; iItr2 < iBlockSize; iItr2++) {

                iaMatrix[iItr1][iItr2] = sAlpha.indexOf(sKey.charAt(iTraceKey));
                iTraceKey++;

            }
        }

    }

    public static void printMatrix(float[][] iaMatrix, int iBlockSize) {
        //System.out.println("_______________________");
        System.out.println("----------------------");
        for (int iItr1 = 0; iItr1 < iBlockSize; iItr1++) {
            //System.out.println("-----------------------");
            System.out.print("|");
            for (int iItr2 = 0; iItr2 < iBlockSize; iItr2++) {
                System.out.printf("  %02d  |", (int) iaMatrix[iItr1][iItr2]);
            }
            System.out.println("");
        }
        System.out.println("----------------------");
    }

}
