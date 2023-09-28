package org.example;

import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;

public class Main {
    public static void main(String[] args) {
        // Given data points
        double stage = 0.01;
        double x0 = 0.752;
        int n = 8;
        double[] xList = new double[n]; // GIVE LENGTH VALUE AND STAGE NUMBER
        double[] yList = new double[n];

        for (int i = 0; i < n; i++) {
            xList[i] = x0 + stage * i;
            yList[i] = Math.cos(xList[i]);
        }
        CosTablePrint(xList, yList);
        PolynomialFunction[] polynomialFirstFormList = new PolynomialFunction[n - 1];
        for (int i = 0; i < polynomialFirstFormList.length; i++)
            polynomialFirstFormList[i] = createLagrangePolynomialFromTwoPoints(xList[i], yList[i], xList[i + 1], yList[i + 1]);
        PolynomialFunction[] polynomialSecondFormList = new PolynomialFunction[n - 2];
        for (int i = 0; i < polynomialSecondFormList.length; i++) {
            PolynomialFunction additionalPolynom1 = new PolynomialFunction(new double[]{-xList[i], 1});
            PolynomialFunction additionalPolynom2 = new PolynomialFunction(new double[]{-xList[i+2], 1});
            System.out.println("AdditionalPolynom1 polynomial: " + additionalPolynom1.toString());
            System.out.println("AdditionalPolynom2 polynomial: " + additionalPolynom2.toString());


            PolynomialFunction tempPositive = polynomialFirstFormList[i].multiply(additionalPolynom2);
            PolynomialFunction tempNegative = polynomialFirstFormList[i+1].multiply(additionalPolynom1);
            polynomialSecondFormList[i] = tempPositive.subtract(tempNegative).multiply(new PolynomialFunction(new double[] {xList[i+2]-xList[i]} ) );
            System.out.println("PolynomialSecondFormList polynomial: " + polynomialSecondFormList[i].toString());
            System.out.println("x | f(x): " + xList[i] + " -- " + polynomialSecondFormList[i].value(xList[i]));
            System.out.println("x-next | f(x-next): " + xList[i+1] + " -- " + polynomialSecondFormList[i].value(xList[i+1]));

        }

//        PolynomialFunction[] polynomialThirdFormList = new PolynomialFunction[n-3];
//        for (int i = 0; i < polynomialThirdFormList.length; i++)
//            polynomialThirdFormList[i] = polynomialSecondFormList[i].multiply(polynomialSecondFormList[i+1]);

//        for (int i = 0; i < polynomialSecondFormList.length; i++) {
//            System.out.println("Lagrange polynomial 3th: "+polynomialSecondFormList[i].toString());
//            System.out.println("Value in " + xList[i] + " -- " + polynomialSecondFormList[i].value(xList[i]));
//        }
    }

    private static PolynomialFunction createLagrangePolynomialFromTwoPoints(double x0, double y0, double x1, double y1) {
        double b = (y0*x1 - y1*x0)/(x1-x0);
        double k = (y1-y0)/(x1-x0);

        double[] coefficients = {b,k};
        return new PolynomialFunction(coefficients);
    }
    public static void CosTablePrint(double[] xList, double[] yList) {
        // Print the table header
        System.out.printf("%-10s%-15s%n", "x", "cos(x)");
        System.out.println("----------------------------");

        // Print the values in a table format
        for (int i = 0; i < xList.length; i++) {
            System.out.printf("%-10.3f | \t%-15.6f%n", xList[i], yList[i]);
        }
    }
}
