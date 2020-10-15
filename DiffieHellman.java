/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffiehellman;

import java.util.Scanner;

/**
 *
 * @author zealous
 */
public class DiffieHellman {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scn = new Scanner(System.in);
        
        int iPrime;
        int iRoot;
        int iAlicep;
        int iBobp;
        System.out.println("Sample : (p=23,r=5,ap=4,bp=3,x=4,y=10,k=18)");
        System.out.println("Sample : (p=23,r=9,ap=4,bp=3,x=6,y=16,k=9)");
        System.out.println("Sample : (p=11,r=2,ap=9,bp=4,x=6,y=5,k=9)");
        System.out.println("");
        System.out.print("Enter Prime Number(p)\t\t:");
        iPrime = scn.nextInt();
        System.out.print("Enter primitive root(r)\t\t:");
        iRoot = scn.nextInt();
        
        System.out.print("Enter private key of Alice\t:");
        while(true){
            iAlicep = scn.nextInt();
            if(iPrime <= iAlicep){
                System.out.print("Private Key should < p "+iPrime+"\t:");
                continue;
            }
            else
                break;
        }
        
        System.out.print("Enter private key of Bob\t:");
        iBobp = scn.nextInt();
        
        System.out.println("\nAlice generating her key X\t\t\tBob generating his key Y\n");
        
        Double iAliceX=Math.pow(iRoot, iAlicep)% iPrime;
        Double iBobY=Math.pow(iRoot,iBobp) % iPrime;
        
        System.out.println("X="+iAliceX+"\t\t\t\t\t\t"+"Y="+iBobY);
        
        System.out.println("\nAlice calculates shared key K\t\t\tBob calculates shared key K\n");
        
        Double iAliceK=Math.pow(iBobY, iAlicep)% iPrime;
        Double iBobK=Math.pow(iAliceX,iBobp) % iPrime;
        
        System.out.println("K="+iAliceK+"\t\t\t\t\t\t"+"K="+iBobK);
        System.out.println("\n");
        
         
    }
    
}

