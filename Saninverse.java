/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saninverse;

import java.util.Scanner;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author zealous
 */
public class Saninverse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Matrix a;
    double [][] values = {{1,2},{3,4}};
        double [][] rhs = {{1, 0},{0, 1}};

        // Solving AB = I for given A
        RealMatrix A = new Array2DRowRealMatrix(values);
        System.out.println("Input A: " + A);
        DecompositionSolver solver = new LUDecomposition(A).getSolver();

        RealMatrix I = new Array2DRowRealMatrix(rhs);
        RealMatrix B = solver.solve(I);
        System.out.println("Inverse B: " + B);
    }	
 
}
