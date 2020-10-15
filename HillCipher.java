/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hill;

import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class HillCipher {
    public static final String reference= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Text:");
        String inputText = input.nextLine().replaceAll(" ", "");
        inputText=inputText.toUpperCase();
        System.out.println("Enter key size:");
        int keySize = input.nextInt();
        double[][] key = new double[keySize][keySize];
        double[][] key_rev = new double[keySize][keySize];
        HillCipher hillCipher = new HillCipher();
        System.out.println("Enter the elements :");
        for(int p_Loop=0; p_Loop<keySize;p_Loop++){
            for(int s_Loop=0; s_Loop<keySize;s_Loop++){
                key[p_Loop][s_Loop] = input.nextDouble()%26;
            }
        }
        double detMatrix = hillCipher.determinant(key,keySize);
        if(detMatrix==0){
            System.exit(0);
        }
        else{
            
            System.out.println("Enter your choice.\n 1. Encrypt \n2. Decrypt");
            int choice = input.nextInt();
            switch(choice){
                case 1: Encrypt(inputText,key,keySize);
                   break;
                case 2:Decrypt(inputText,key,keySize,detMatrix);
                   break;
                default:System.exit(0);
       }  
        }
    }    
    

    private static void Encrypt(String inputText, double[][] key, int keySize) {
        if(inputText.length()%keySize!=0){
            while(inputText.length()%keySize!=0){
                inputText+='X';
            }
        }
        int iTr = inputText.length()/keySize;
        String cipherText = "";
        int index=0;
        for(int pLoop=0;pLoop<iTr; pLoop++){
            String temp = inputText.substring(index,index+keySize);
            int mIndex=0;
            double[][] plainPartition = new double[keySize][1];
            for(int iLoop=0;iLoop<keySize;iLoop++){
                plainPartition[iLoop][0] = reference.indexOf(temp.charAt(mIndex));
                mIndex++;
            }
            double[][] cipherPartition = new double[keySize][1];
            cipherPartition=multiplyMatrix(key,plainPartition,keySize);
            for(int iLoop=0;iLoop<keySize;iLoop++){
                cipherText+= reference.charAt((int)cipherPartition[iLoop][0]);
            }
            index+=keySize;
        }
        System.out.println("CipherText of given plaintext is "+cipherText);
    }

    private static void Decrypt(String inputText, double[][] key, int keySize, double detMatrix) {
        if(inputText.length()%keySize!=0){
            while(inputText.length()%keySize!=0){
                inputText+='X';
            }
        }
        double[][] key_rev = Inverse(key,keySize,detMatrix);
        key_rev = RepairMatrix(key_rev,keySize);
        int iTr = inputText.length()/keySize;
        String plainText = "";
        int index=0;
        for(int pLoop=0;pLoop<iTr; pLoop++){
            String temp = inputText.substring(index,index+keySize);
            int mIndex=0;
            double[][] cipherPartition = new double[keySize][1];
            for(int iLoop=0;iLoop<keySize;iLoop++){
                cipherPartition[iLoop][0] = reference.indexOf(temp.charAt(mIndex));
                mIndex++;
            }
            double[][] plainPartition = new double[keySize][1];
            plainPartition=multiplyMatrix(key_rev,cipherPartition,keySize);
            for(int iLoop=0;iLoop<keySize;iLoop++){
                plainText+= reference.charAt((int)plainPartition[iLoop][0]);
            }
            index+=keySize;
        }
        System.out.println("PlainText of given plaintext is  "+plainText.toLowerCase());
    }

    private static double[][] multiplyMatrix(double[][] key, double[][] plainPartition, int keySize) {
        double[][] mulResult = new double[keySize][1];
        for(int pLoop=0;pLoop<keySize;pLoop++){
            for(int sLoop=0 ; sLoop<keySize;sLoop++){
                mulResult[pLoop][0] += (key[pLoop][sLoop]*plainPartition[sLoop][0]);
            }
            mulResult[pLoop][0] =mulResult[pLoop][0] %26;
        }
        return mulResult;
    }

    private static double[][] Inverse(double[][] key, int keySize, double detMatrix) {
        double [][] transposeMat = new double[keySize][keySize];
        for(int pLoop=0;pLoop<keySize;pLoop++){
            for(int sLoop=0;sLoop<keySize;sLoop++){
                transposeMat[pLoop][sLoop] = key[sLoop][pLoop];
            }
        }
        double[][] adjMatrix = AdjMatrix(transposeMat,keySize);
        if(detMatrix<0){
            while(detMatrix<0){
            detMatrix+=26;
            }
        }
        else{
            detMatrix = detMatrix%26;
        }
        double invDetMatrix = FindCoPrime(detMatrix);
        if(invDetMatrix==0){
            System.exit(0);
        }
        for(int pLoop=0;pLoop<keySize;pLoop++){
            for(int sLoop=0;sLoop<keySize;sLoop++){
                adjMatrix[pLoop][sLoop] = (adjMatrix[pLoop][sLoop]*invDetMatrix)%26;
            }
        }
        return adjMatrix;
    }

    private static double[][] AdjMatrix(double[][] transposeMat, int keySize) {
        int counter =0;
        double[][] adjMatrix = new double[keySize][keySize];
        for(int pLoop=0;pLoop<keySize;pLoop++){
            for(int sLoop=0 ; sLoop<keySize ; sLoop++){
                adjMatrix[pLoop][sLoop] = DetReducedMatrix(transposeMat,pLoop,sLoop,keySize)*Math.pow(-1, counter);
                counter++;
            }
        }
        return adjMatrix;
    }

    private static double DetReducedMatrix(double[][] transposeMat, int pLoop, int sLoop, int keySize) {
        HillCipher hillCipher = new HillCipher();
        double[][] returnData = new double[keySize-1][keySize-1];
        for (int h = 0, j = 0; h < keySize; h++) { 
            if (h == pLoop) 
                continue; 
            for (int i = 0, k = 0; i < keySize; i++) { 
                if (i == sLoop) 
                    continue; 
                returnData[j][k] = transposeMat[h][i]; 
                k++; 
            } 
            j++; 
        } 
    return hillCipher.determinant(returnData,keySize-1);
    }

    private static double FindCoPrime(double detMatrix) {
        int pLoop;
        for(pLoop=1;pLoop<=26;pLoop++){
            if((detMatrix*pLoop)%26==1){
                return pLoop;
            }
        }
        return 0;
    }

    private static double[][] RepairMatrix(double[][] key_rev, int keySize) {
        for(int pLoop=0;pLoop<keySize;pLoop++){
            for(int sLoop=0;sLoop<keySize;sLoop++){
                if(key_rev[pLoop][sLoop]<0){
                    while(key_rev[pLoop][sLoop]<0){
                        key_rev[pLoop][sLoop]+=26;
                    }
                }
            }
        }
        return key_rev;
    }

    public double determinant(double[][] key, int keySize) {
        double det=0;
        if(keySize == 1)
        {
            det = key[0][0];
        }
        else if (keySize == 2)
        {
            det = key[0][0]*key[1][1] - key[1][0]*key[0][1];
        }
        else
        {
            det=0;
            for(int j1=0;j1<keySize;j1++)
            {
                double[][] m = new double[keySize-1][];
                for(int k=0;k<(keySize-1);k++)
                {
                    m[k] = new double[keySize-1];
                }
                for(int i=1;i<keySize;i++)
                {
                    int j2=0;
                    for(int j=0;j<keySize;j++)
                    {
                        if(j == j1)
                            continue;
                        m[i-1][j2] = key[i][j];
                        j2++;
                    }
                }
                det+= Math.pow(-1.0,1.0+j1+1.0)* key[0][j1]*determinant(m, keySize-1);
            }
        }
        return det;
    } 
}